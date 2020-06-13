package org.octavia.octaviaGui.search.list;

import org.octavia.octaviaDatabase.dataTypes.Media;
import org.octavia.octaviaGui.OctyBooruFrame;
import org.octavia.octaviaGui.media.MediaPageController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * view to display results of a search in a visual list of the results
 */
public class SearchListView {

    private JPanel view;
    private JLabel empty;

    private final int imageHeight = 200;
    private final int imageWidth = 200;

    public void createMedia(Media media){
        JButton button = new JButton();
        try {
            BufferedImage image = ImageIO.read(new File(media.getPath()));
            Image scaled = image.getScaledInstance(imageWidth,imageHeight,Image.SCALE_AREA_AVERAGING);
            //TODO This causes massive errors
            button.setIcon(new ImageIcon(scaled));
            button.setMinimumSize(new Dimension(imageWidth,imageHeight));
            button.setSize(new Dimension(imageWidth,imageHeight));
            button.setMaximumSize(new Dimension(imageWidth,imageHeight));

            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);

            button.addActionListener(new SearchButtonListener(media));

            addElement(button);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: elements not wrapping properly
    public void addElement(Component c){
        empty.setEnabled(false);
        view.add(c);
        view.repaint();
    }

    private class SearchButtonListener implements ActionListener {

        private Media media;

        public  SearchButtonListener(Media media){
            this.media = media;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MediaPageController page = new MediaPageController(media);
            OctyBooruFrame frame = OctyBooruFrame.getInstance();
            frame.addTab(String.valueOf(media.getMediaID()), page.getMediaPagePanel());
        }


    }

    public void clearElements(){
        view.removeAll();
        //view.revalidate();
        empty.setEnabled(true);
        view.repaint();

    }

    public void removeElement(Component c){
        view.remove(c);
    }

    public JPanel getPanel() {
        return  view;
    }

}
