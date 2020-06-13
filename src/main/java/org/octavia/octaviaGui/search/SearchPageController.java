package org.octavia.octaviaGui.search;

import org.octavia.octaviaGui.search.list.SearchListController;
import org.octavia.octaviaGui.search.side.SearchSideController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchPageController {
    private SearchPageModel model;
    private SearchPageView view;

    private SearchSideController sideController;
    private SearchListController listController;

    public SearchPageController(){
        this(null);
    }

    public SearchPageController(String search){
        sideController = new SearchSideController();
        listController = new SearchListController();
        model = new SearchPageModel();
        view = new SearchPageView(sideController.getView(), listController.getView());
        sideController.addSearchButtonListener(new SearchButtonListener());
        setSearch(search);
    }

    public JPanel getView() {
        return view.getView();
    }

    //TODO: Searching multiple times crashes program as well as empty sets effecting it
    public void setSearch(String search){
        model.setSearch(search);
        sideController.setSearch(model.getSearchedTags(),model.getMedia());
        //TODO: it's this
        listController.setSearch(model.getSearchedTags(), model.getMedia());
        //view.getView().revalidate();
        view.getView().repaint();
    }

    private class SearchButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String search = sideController.getSearch();
            setSearch(search);
        }
    }
}
