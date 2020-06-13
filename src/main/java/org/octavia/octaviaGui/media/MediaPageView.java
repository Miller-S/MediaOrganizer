package org.octavia.octaviaGui.media;

import org.octavia.octaviaGui.media.content.MediaContentView;
import org.octavia.octaviaGui.media.side.MediaSideView;

import javax.swing.*;

public class MediaPageView {
    private JPanel viewMedia;
    private JPanel side;
    private JPanel media;
    private MediaSideView sidePanel;
    private MediaContentView mediaPanel;


    public MediaPageView(JPanel side, JPanel media) {
        this.media = media;
        this.side = side;
    }

    public JPanel getPanel() {
        return viewMedia;
    }

    private void createUIComponents() {

    }
}
