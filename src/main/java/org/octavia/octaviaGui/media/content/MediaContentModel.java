package org.octavia.octaviaGui.media.content;

import org.octavia.octaviaDatabase.dataTypes.Media;

/**
 * Model for the main content section of the media viewing page.
 */
public class MediaContentModel {
    // media that is being displayed on the page
    private Media media;

    /**
     * Constructor
     * @param media the media that is displayed on the page
     */
    public MediaContentModel(Media media) {
        this.media = media;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
