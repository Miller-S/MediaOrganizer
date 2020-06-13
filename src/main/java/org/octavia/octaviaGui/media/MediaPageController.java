package org.octavia.octaviaGui.media;

import org.octavia.octaviaDatabase.dataTypes.Media;
import org.octavia.octaviaGui.media.content.MediaContentController;
import org.octavia.octaviaGui.media.side.MediaSideController;

import javax.swing.*;

/**
 * Controller for the media page that will display a set media as well as provide options for
 * editing the media.
 */
public class MediaPageController {

    //Model for the page
    private MediaPageModel model;
    //View for the page
    private MediaPageView view;


    /**
     * Constructor
     * set the media for the page
     * @param media
     */
    public MediaPageController(Media media) {
        setMedia(media);
    }

    /**
     * Change the media that is displayed on the page
     * @param media
     */
    public void setMedia(Media media){
        model = new MediaPageModel(media, new MediaSideController(media), new MediaContentController(media));
        view = new MediaPageView(model.getMediaSideController().getMediaSidePanel(), model.getMediaContentController().getMediaContentPanel());
    }

    /**
     * Get the panel that is the view
     * @return
     */
    public JPanel getMediaPagePanel(){
        return view.getPanel();
    }
}
