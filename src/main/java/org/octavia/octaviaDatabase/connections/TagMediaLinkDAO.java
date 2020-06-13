package org.octavia.octaviaDatabase.connections;

import org.octavia.octaviaDatabase.OctyDB;
import org.octavia.octaviaDatabase.OctyPreparedStatements;
import org.octavia.octaviaDatabase.dataTypes.Media;
import org.octavia.octaviaDatabase.dataTypes.TagMediaLink;
import org.octavia.octaviaGui.search.CountedTag;
import org.octavia.octaviaGui.search.SearchedTag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TagMediaLinkDAO extends OctyDAO{

    public TagMediaLinkDAO() {
        super("TagMediaLink");
    }

    private TagMediaLink getHelper(String queryEnder) throws SQLException {
        open();
        Statement state = db.createStatement();
        StringBuilder query = createStarSelectQuery();
        query.append(" " + queryEnder);
        ResultSet rs = state.executeQuery(query.toString());
        TagMediaLink link = null;
        if(rs.next()){
            link = new TagMediaLink(
                    rs.getInt("TagID"),
                    rs.getInt("MediaID"));
        }
        rs.close();
        close();
        return link;

    }

    public TagMediaLink getLinkFromTag(int id) throws SQLException {
        String query = "WHERE TagID = " + id;
        return getHelper(query);
    }

    public TagMediaLink getLinkFromMedia(int id) throws SQLException {
        String query = "WHERE MediaID = " + id;
        return getHelper(query);
    }

    public boolean insertLink(TagMediaLink link) throws SQLException {
        open();
        Statement state = db.createStatement();
        StringBuilder query = insertStarter();
        query.append("TagID, MediaID) ");
        query.append("VALUES ( " + link.getTagID() + ", " + link.getMediaID() + ")");
        int count = state.executeUpdate(query.toString());
        state.close();
        close();
        return count > 0;

    }

    public ArrayList getTagsFromMediaID(int id) throws SQLException {
        OctyPreparedStatements prepped = OctyPreparedStatements.getInstance();
        return prepped.getTagsFromMediaID(id);
    }

    public CountedTag[] getRelatedTags(ArrayList<Media> media, int amount) throws SQLException {
        open();
        String sql = createTopTagQuery(media, amount);
        CountedTag[] relatedTag = new CountedTag[amount];
        int i = 0;
        Statement tagQuery = db.createStatement();
        ResultSet tagResults = tagQuery.executeQuery(sql);
        while(tagResults.next()){
            relatedTag[i++] = new CountedTag(
                    tagResults.getString("TagName"),
                    tagResults.getInt("TagID"),
                    tagResults.getInt("count"));

        }

        tagQuery.close();
        close();
        return relatedTag;
    }

    private String createTopTagQuery(ArrayList<Media> media, int amount) {
        StringBuilder sql = new StringBuilder("SELECT TOP "+ amount + " Tag.[TagName], Tag.[TagID],COUNT("+ table +".TagID) AS 'count'" +
                " FROM [dbo].["+ table +"] " +
                "INNER JOIN Tag ON Tag.TagID = "+ table +".TagID ");
        if(media.isEmpty()) {
            sql.append("GROUP BY Tag.TagID, Tag.TagName ORDER BY count desc");
            return sql.toString();
        }
        sql.append("WHERE ");
        for (int i = 0; i < media.size(); i++) {
            sql.append("MediaID = " + media.get(i).getMediaID() + " ");
            if(i < media.size() - 1)
                sql.append("OR ");
        }
        sql.append("GROUP BY Tag.TagID, Tag.TagName ORDER BY count desc");
        return sql.toString();
    }

    public ArrayList<Media> getMediaFromTags(ArrayList<SearchedTag> searchedTags) throws SQLException {
        open();
        String sql = createSQLMediaQuery(searchedTags);
        ArrayList<Media> media = new ArrayList<>();
        Statement mediaQuery = db.createStatement();
        ResultSet mediaResults = mediaQuery.executeQuery(sql);
        while(mediaResults.next()) {
            media.add(new Media(
                    mediaResults.getInt("MediaID"),
                    mediaResults.getString("Path")));
        }
        close();
        return media;
    }

    //TODO: Need to finalize search to run off of different types of searches instead of just AND
    private String createSQLMediaQuery(ArrayList<SearchedTag> searchedTags) {
        StringBuilder sql = new StringBuilder("SELECT Media.MediaID, Media.Path FROM Tag " +
                "INNER JOIN "+ table +" ON Tag.TagID = "+ table +".TagID " +
                "INNER JOIN Media ON (Media.MediaID = "+ table +".MediaID) ");
        if(searchedTags.isEmpty()) {
            sql.append("GROUP BY Media.MediaID, Media.Path");
            return sql.toString();
        }
        sql.append("WHERE ");
        SearchedTag tag;
        for (int i = 0; i < searchedTags.size() - 1; i++) {
            tag = searchedTags.get(i);
            switch (tag.getType()){
                default:
                    sql.append(" Tag.TagID = " + tag.getTagID() + " AND " );
            }
        }
        sql.append(" Tag.TagID = " + searchedTags.get(searchedTags.size() - 1).getTagID());
        sql.append(" GROUP BY Media.MediaID, Media.Path");
        return sql.toString();
    }

}
