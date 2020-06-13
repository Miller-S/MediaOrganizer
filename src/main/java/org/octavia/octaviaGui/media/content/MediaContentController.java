package org.octavia.octaviaGui.media.content;

import org.octavia.octaviaDatabase.dataTypes.Media;

import javax.swing.*;

/**
 * Controller for the main content section of the media viewing page.
 * Contains comments as well as the media
 */
public class MediaContentController {
    private MediaContentModel model;
    private MediaContentView view;

    /**
     * Constructor
     * Create the model and the view for the section and set the media that will be displayed.
     * @param media media that will be displayed on the page
     */
    public MediaContentController(Media media) {
        model = new MediaContentModel(media);
        view = new MediaContentView();
        view.setImage(media.getPath());
    }

    /**
     * Get the panel that is the view
     * @return
     */
    public JPanel getMediaContentPanel() {
        return view.getPanel();
    }

    /**
     * Update the model and view with new media.
     * @param media the media to set on the page
     */
    public void setMedia(Media media){
        model.setMedia(media);
        view.setImage(media.getPath());
    }


}
