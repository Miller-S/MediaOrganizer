package org.octavia.octaviaGui.media.side;

import org.octavia.octaviaDatabase.dataTypes.Media;
import org.octavia.octaviaDatabase.dataTypes.Tag;
import org.octavia.octaviaGui.OctyBooruFrame;
import org.octavia.octaviaGui.search.SearchPageController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller for the side bar section of the media viewing page.
 * Contains tags as well as options for the current piece of media such as adding and removing tags
 */
public class MediaSideController {
    //model for the side bar
    MediaSideModel model;
    //view for the side bar
    MediaSideView view;


    /**
     * add the tags from the model to the view
     */
    private void addTags(){
        for (Tag t:
        model.getTags()) {
            view.addElementToTags(t);
        }
    }

    /**
     * Get the Jpanel that is the view.
     * @return
     */
    public JPanel getMediaSidePanel(){
        return view.getPanel();
    }

    /**
     *
     * @param media
     */
    public MediaSideController(Media media) {
        model = new MediaSideModel(media);
        view = new MediaSideView();
        addTags();
        view.addSearchListener(new SearchButtonListener());
    }

    private class SearchButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String search = view.getSearchInput();
            SearchPageController tab = new SearchPageController();
            tab.setSearch(search);
            OctyBooruFrame frame = OctyBooruFrame.getInstance();
            String title = search;
            if(title.length() > 10)
                title = title.substring(0,7) + "...";
            else if(title.equals("") || title == null)
                title = "*";
            frame.addTab(title, tab.getView());
        }
    }


}
