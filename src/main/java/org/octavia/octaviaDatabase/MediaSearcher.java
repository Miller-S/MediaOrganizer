package org.octavia.octaviaDatabase;

import org.octavia.octaviaDatabase.connections.TagDAO;
import org.octavia.octaviaDatabase.connections.TagMediaLinkDAO;
import org.octavia.octaviaDatabase.dataTypes.Media;
import org.octavia.octaviaGui.search.SearchedTag;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class MediaSearcher {
    private String search;
    private ArrayList<SearchedTag> tags;
    private ArrayList<Media> media;


    public MediaSearcher(ArrayList<SearchedTag> tags) {
        setTags(tags);
    }

    public MediaSearcher(String search) {
        setSearch(search);
    }

    public void setSearch(String search){
        this.search = search;
        this.tags = parseTags(search);
        this.media = runSearch(this.tags);
    }

    public void setTags(ArrayList<SearchedTag> tags){
        this.search = "";
        this.tags = tags;
        this.media = runSearch(tags);

    }

    public String getSearch() {
        return search;
    }

    public ArrayList<SearchedTag> getTags() {
        return tags;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }

    private  ArrayList<SearchedTag> parseTags(String search) {
        tags = new ArrayList<>();

        if(search != null && !search.equals("")) {
            ArrayList<String> splitTags = new ArrayList<String>(Arrays.asList(search.split(",? ")));
            populateTags(splitTags);
        }
        return tags;
    }

    private void populateTags(ArrayList<String> splitTags){
            for(int i = 0; i < splitTags.size(); i++){
                try {
                    tags.add(createTag(splitTags.get(i)));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
    }

    //TODO: Cases for no tag found
    private SearchedTag createTag(String tag) throws SQLException {
        TagDAO dao = new TagDAO();
        switch (tag.charAt(0)) {
            case '~':
                return new SearchedTag(dao.getTagFromName(tag.substring(1,tag.length())), SearchedTag.SearchType.OR);
            case '-':
                return new SearchedTag(dao.getTagFromName(tag.substring(1,tag.length())), SearchedTag.SearchType.NOT);
            default:
                return new SearchedTag(dao.getTagFromName(tag), SearchedTag.SearchType.AND);
        }
    }

    private ArrayList<Media> runSearch(ArrayList<SearchedTag> tags){
        ArrayList<Media> media = new ArrayList<>();
        TagMediaLinkDAO link = new TagMediaLinkDAO();
        try {
            media.addAll(link.getMediaFromTags(tags));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return media;
    }

    //TODO add in sorting methods
}
