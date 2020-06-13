package org.octavia.octaviaGui.search.list;

import org.octavia.octaviaDatabase.dataTypes.Media;
import org.octavia.octaviaGui.search.SearchedTag;

import javax.swing.*;
import java.util.ArrayList;

public class SearchListController {
    SearchListView view;
    SearchListModel model;

    public SearchListController() {
        this.view = new SearchListView();
        this.model = new SearchListModel();
    }

    public SearchListController(ArrayList<SearchedTag> searchedTags){
        this();
        setSearch(searchedTags, model.getMedia());
    }

    public void setSearch(ArrayList<SearchedTag> searchedTags, ArrayList<Media> media){
        model.setSearch(searchedTags, media);
        view.clearElements();
        for (Media m: model.getMedia()) {
            view.createMedia(m);
        }
        System.out.println("searchedTags = " + searchedTags + ", media = " + media);
    }

    public JPanel getView(){
        return view.getPanel();
    }
}
