package org.octavia.octaviaGui.media;

import org.octavia.octaviaDatabase.dataTypes.Media;
import org.octavia.octaviaGui.media.content.MediaContentController;
import org.octavia.octaviaGui.media.side.MediaSideController;

/**
 * Model for the the media viewing page.
 */
public class MediaPageModel {
    //Controller for the main content
    private MediaContentController mediaContentController;
    //Controller for the side bar
    private MediaSideController mediaSideController;
    //Media displayed on the current page
    Media media;

    /**
     * Constructor
     * @param media media that will be displayed ont he page
     * @param side the controller for the side bar
     * @param panel the controller for the main content
     */
    public MediaPageModel(Media media, MediaSideController side, MediaContentController panel) {
        this.media = media;
        mediaContentController = panel;
        mediaSideController = side;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public MediaContentController getMediaContentController() {
        return mediaContentController;
    }

    public void setMediaContentController(MediaContentController mediaContentController) {
        this.mediaContentController = mediaContentController;
    }

    public MediaSideController getMediaSideController() {
        return mediaSideController;
    }

    public void setMediaSideController(MediaSideController mediaSideController) {
        this.mediaSideController = mediaSideController;
    }
}
