package org.octavia.octaviaGui.search.side;

import org.octavia.octaviaDatabase.dataTypes.Media;
import org.octavia.octaviaGui.search.CountedTag;
import org.octavia.octaviaGui.search.SearchedTag;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchSideController {

    private SearchSideView view;
    private SearchSideModel model;

    public SearchSideController() {
        view = new SearchSideView();
        model = new SearchSideModel();
    }

    public void addSearchButtonListener(ActionListener a){
        view.addSearchButtonListener(a);
    }

    public void setSearch(ArrayList<SearchedTag> search, ArrayList<Media> media){
        model.setSearch(search,media);
        CountedTag[] related = model.getRelatedTag();
        view.clearRelatedTags();
        for (CountedTag t: related
             ) {
            view.addRelatedTag(t);
        }
    }

    public String getSearch(){
        String search = view.getSearch();
        return search;
    }


    public JPanel getView() {
        return view.getPanel();
    }
}
