package org.octavia.octaviaGui.search;

import org.octavia.octaviaDatabase.MediaSearcher;
import org.octavia.octaviaDatabase.OctyDB;
import org.octavia.octaviaDatabase.connections.TagDAO;
import org.octavia.octaviaDatabase.connections.TagMediaLinkDAO;
import org.octavia.octaviaDatabase.dataTypes.Media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchPageModel {

    //list of all the media to be displayed
    ArrayList<Media> media = new ArrayList<>();
    ArrayList<SearchedTag> searchedTags = new ArrayList<>();
    MediaSearcher searcher;

    public ArrayList<SearchedTag> getSearchedTags() {
        return searchedTags;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }

    public void setSearch(String tagSearch){
        searcher = new MediaSearcher(tagSearch);
        searchedTags = searcher.getTags();
        media = searcher.getMedia();
    }

}
