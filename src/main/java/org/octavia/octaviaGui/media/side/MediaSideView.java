package org.octavia.octaviaGui.media.side;

import org.octavia.octaviaDatabase.dataTypes.Tag;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;

/**
 * View for the side bar section of the media viewing page.
 */
public class MediaSideView {
    //Search bar for performing a new query
    private JTextField searchBar;
    //Button to perform search
    private JButton searchButton;
    //List of tags for the media
    private JList tags;
    private DefaultListModel<Tag> tagsModel;
    //List of statistic options for current media
    private JList statistics;
    private DefaultListModel<String> statisticsModel;
    //List of options for current media
    private JList options;
    private DefaultListModel<String> optionsModel;
    //The view for the side panel
    private JPanel viewMediaSidePanel;
    //History information on the current media
    private JList history;
    private DefaultListModel<String> historyModel;

    /**
     * Constuctor
     * Create all of the Jlists
     */
    public MediaSideView() {
        this.tagsModel = new DefaultListModel<>();
        this.statisticsModel = new DefaultListModel<>();
        this.optionsModel = new DefaultListModel<>();
        this.historyModel = new DefaultListModel<>();
        tags.setModel(tagsModel);
        statistics.setModel(statisticsModel);
        options.setModel(optionsModel);
        history.setModel(historyModel);
    }

    /**
     * Get the panel that is the view
     * @return
     */
    public JPanel getPanel(){
        return viewMediaSidePanel;
    }

    public void addSearchListener(ActionListener listenForSearchButton){
        searchButton.addActionListener(listenForSearchButton);
    }

    public void addStatisticsListSelectionListener(ListSelectionListener listen){
        statistics.addListSelectionListener(listen);
    }

    public void addOptionsListSelectionListener(ListSelectionListener listen){
        options.addListSelectionListener(listen);
    }

    public void addHistoryListSelectionListener(ListSelectionListener listen){
        history.addListSelectionListener(listen);
    }

    public void addTagListSelectionListener(ListSelectionListener listen){
        tags.addListSelectionListener(listen);
    }

    /**
     * get the input in the search bar
     * @return
     */
    public String getSearchInput(){
        return searchBar.getText();
    }

    /**
     * Add a tag to the tag list
     * @param tag
     */
    public void addElementToTags(Tag tag){
        tagsModel.addElement(tag);
    }

    public void addElementToStatistics(String statistic){
        statisticsModel.addElement(statistic);
    }

    public void addElementToOptions(String option){
        optionsModel.addElement(option);
    }

    public void addElementToHistory(String history){
        historyModel.addElement(history);
    }
}
