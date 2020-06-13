package org.octavia.octaviaGui.search.side;

import org.octavia.octaviaDatabase.dataTypes.Tag;
import org.octavia.octaviaGui.search.CountedTag;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SearchSideView {
    private DefaultListModel<CountedTag> relatedTagsModel;
    private JTextField Search;
    private JList relatedTags;
    private JButton searchButton;
    private JPanel searchPanel;

    public SearchSideView() {
        this.relatedTagsModel = new DefaultListModel<>();
        relatedTags.setModel(relatedTagsModel);
    }

    public void addRelatedTag(CountedTag tag){
        relatedTagsModel.addElement(tag);
    }

    public void addSearchButtonListener(ActionListener listenForSearchButton){
        searchButton.addActionListener(listenForSearchButton);
    }

    public String getSearch() {
        return Search.getText();
    }

    public JPanel getPanel(){
        return searchPanel;
    }

    public void clearRelatedTags() {
        relatedTagsModel.clear();
    }
}
