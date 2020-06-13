package org.octavia.octaviaGui.media.content;

import javax.swing.*;

/**
 * View for the main content section of the media viewing page.
 * Contains comments as well as the media
 */
public class MediaContentView {

    private JPanel mediaPanelView;
    //Media that is displayed
    private JLabel image;

    /**
     * Constructor
     */
    public MediaContentView() {
    }

    /**
     * Insert the media that is displayed
     * @param path the path to the media
     */
    public void setImage(String path){
        image.setIcon(new ImageIcon(path));
    }

    /**
     * get the JPannel that is the view
     * @return the view
     */
    public JPanel getPanel() {
        return mediaPanelView;
    }
}
