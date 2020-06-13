package org.octavia.octaviaGui.search.list;

import org.octavia.octaviaDatabase.OctyDB;
import org.octavia.octaviaDatabase.dataTypes.Media;
import org.octavia.octaviaGui.search.SearchedTag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchListModel {
    //list of all the media to be displayed
    ArrayList<Media> media = new ArrayList<>();
    ArrayList<SearchedTag> searchedTags = new ArrayList<>();

    public SearchListModel(){}

    public ArrayList<Media> getMedia() {
        return media;
    }

    public void setSearch(ArrayList<SearchedTag> tags, ArrayList<Media> media){
        searchedTags = tags;
        this.media = media;
    }

}
