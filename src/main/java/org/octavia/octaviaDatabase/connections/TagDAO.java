package org.octavia.octaviaDatabase.connections;

import org.octavia.octaviaDatabase.OctyDB;
import org.octavia.octaviaDatabase.OctyPreparedStatements;
import org.octavia.octaviaDatabase.dataTypes.Tag;
import org.octavia.octaviaGui.search.SearchedTag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TagDAO extends OctyDAO{

    public TagDAO() {
        super("Tag");
    }

    private Tag getHelper(String queryEnder) throws SQLException {
        open();
        Statement state = db.createStatement();
        StringBuilder query = createStarSelectQuery();
        query.append(" " + queryEnder);
        ResultSet rs = state.executeQuery(query.toString());
        Tag tag = null;
        if(rs.next()){
            tag = new Tag(rs.getString("TagName"),
                    rs.getInt("TagID"),
                    rs.getString("Description"));
        }
        rs.close();
        close();
        return tag;

    }

    public Tag getTagFromName(String name) throws SQLException {
        String query = "WHERE TagName = '" + name + "'";
        return getHelper(query);
    }

    public Tag getTagFromID(int id) throws SQLException {
        String query = "WHERE TagID = " + id;
        return getHelper(query);
    }

    public boolean updateTag(Tag update) throws SQLException {
        open();
        Statement state = db.createStatement();
        StringBuilder query = updateStarter();
        query.append("TagName = " + update.getTagName());
        query.append(" Description = " + update.getDescription());
        query.append(" WHERE TagId = " + update.getTagID());
        int count = state.executeUpdate(query.toString());
        state.close();
        close();
        return count > 0;
    }

    public ArrayList<Tag> getTagsFromName(ArrayList<String> names){
        open();
        String sql = createSQLTagQuery(names);
        ArrayList<Tag> tags = new ArrayList<>();
        try {
            Statement tagQuery = db.createStatement();
            ResultSet tagResults = tagQuery.executeQuery(sql);
            while(tagResults.next()){
                tags.add(new SearchedTag(
                        tagResults.getString("TagName"),
                        tagResults.getInt("TagID"),
                        tagResults.getString("Description")));
            }

            tagQuery.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        close();
        return tags;
    }

    private String createSQLTagQuery(ArrayList<String> tags) {
        StringBuilder sql = new StringBuilder("SELECT * FROM Tag WHERE ");
        for (int i = 0; i < tags.size(); i++) {
            sql.append("tagName = '" + tags.get(i) + "'");
            if(i < tags.size() - 1)
                sql.append(" OR ");
        }
        return sql.toString();
    }

    public boolean insertTag(Tag tag) throws SQLException {
        OctyPreparedStatements prepped = OctyPreparedStatements.getInstance();
        return prepped.addTag(tag.getTagName(), tag.getDescription());
    }

    public boolean removeTagByID(int id) throws SQLException {
        OctyPreparedStatements prepped = OctyPreparedStatements.getInstance();
        return prepped.removeTagByID(id);
    }
}
