package org.octavia.octaviaGui.search.side;

import org.octavia.octaviaDatabase.OctyDB;
import org.octavia.octaviaDatabase.connections.TagMediaLinkDAO;
import org.octavia.octaviaDatabase.dataTypes.Media;
import org.octavia.octaviaDatabase.dataTypes.Tag;
import org.octavia.octaviaDatabase.dataTypes.TagMediaLink;
import org.octavia.octaviaGui.search.CountedTag;
import org.octavia.octaviaGui.search.SearchedTag;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SearchSideModel {
    private final int relatedTagSize = 15;
    private CountedTag[] relatedTag = new CountedTag[relatedTagSize];
    private ArrayList<SearchedTag> searchedTags;
    private ArrayList<Media> media;

    private void getRelatedTags(){
        TagMediaLinkDAO link = new TagMediaLinkDAO();
        try {
            relatedTag = link.getRelatedTags(media, relatedTagSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setSearch(ArrayList<SearchedTag> search, ArrayList<Media> media){
        searchedTags = search;
        this.media = media;
        getRelatedTags();
    }

    public CountedTag[] getRelatedTag() {
        return relatedTag;
    }
}
