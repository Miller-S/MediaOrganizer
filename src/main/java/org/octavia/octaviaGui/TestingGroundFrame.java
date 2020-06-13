package org.octavia.octaviaGui;

import org.octavia.octaviaDatabase.OctyDB;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestingGroundFrame {

    private static JFrame frame;
    private static TestingGroundFrame oFrame;
    private static int height = 1080;
    private static int width = 1920;

    private JPanel mainPanel;
    private JTextField enterTagTextField;
    private JButton addTheTagButton;
    private JLabel statusDisplay;
    private JLabel imageLabel;
    private JButton getImageButton;
    private JTextArea tags;
    private JTextField enterPathTextField;
    private JButton fileSelectorButton;
    private JButton uploadImageButton;

    private void displayImage(String path){
        ImageIcon icon = new ImageIcon(path);
        imageLabel.setIcon(icon);
        OctyDB db = OctyDB.getInstance();
        PreparedStatement p = db.getPreparedStatement(OctyDB.queries.GET_MEDIA_ID_FROM_PATH);
        try {
            p.setString(1, path);
            ResultSet rs = p.executeQuery();
            rs.next();
            int id = rs.getInt("MediaID");
            p = db.getPreparedStatement(OctyDB.queries.GET_TAGS_FROM_MEDIA_ID);
            p.setInt(1, id);
            rs = p.executeQuery();
            String tags = "";
            while(rs.next()){
                tags += rs.getString("TagName") + "\n";
            }
            this.tags.setText(tags);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void displayMessage(Color c, String message){
        statusDisplay.setText(message);
        statusDisplay.setForeground(c);
        statusDisplay.setVisible(true);
    }

    public class tagListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String tag = enterTagTextField.getText();
            OctyDB db = OctyDB.getInstance();
            try {
                boolean added = db.addTag(tag);
                if(added) {
                    displayMessage(Color.GREEN, "Fetish: " + tag + " was added!");
                }
                else{
                    displayMessage(Color.red,"Are you too Horny? Fetish already exists");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }


        }
    }

    public class uploadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String tag = enterTagTextField.getText();
            OctyDB db = OctyDB.getInstance();
            String path = enterPathTextField.getText();
            try {
               PreparedStatement p = db.getPreparedStatement(OctyDB.queries.INSERT_MEDIA);
               Image image = ImageIO.read(new File(path));
               if(image == null) {
                   displayMessage(Color.RED, "Please Select an Image");
                   return;
               }

               p.setString(1 , enterPathTextField.getText());
               if (p.executeUpdate() > 0){
                   displayImage(path);
                }


            } catch (SQLException | IOException ex) {
                displayMessage(Color.RED, "Please Select an Image");
                ex.printStackTrace();
            }


        }
    }

    public class fileSelectorListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Create a file chooser
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(System.getProperty("user.home")));
            fc.setDialogTitle("Upload Image");
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnVal = fc.showOpenDialog(fileSelectorButton);
            if (returnVal == JFileChooser.APPROVE_OPTION){
                try {
                    Image selected = ImageIO.read(fc.getSelectedFile());
                    if (selected != null){
                        enterPathTextField.setText(fc.getSelectedFile().getPath());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }


        }
    }

    public class imageGrabberListener implements ActionListener {

        ArrayList<String> imagePaths = new ArrayList<>();
        public imageGrabberListener() {
            OctyDB db = OctyDB.getInstance();
            try {
                PreparedStatement p = db.getPreparedStatement(OctyDB.queries.GET_ALL_MEDIA);
                ResultSet rs = p.executeQuery();
                while(rs.next()){
                    imagePaths.add(rs.getString("Path"));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int rand = (int)(imagePaths.size() * Math.random());
            displayImage(imagePaths.get(rand));
        }
    }

    private TestingGroundFrame() {
        String[] title = {
                "( ͡° ͜ʖ ͡°)",
                "For all your degenerate needs",
                "You're invited to the night of debauchery",
                "Haaaaarder~ Mommy",
                "This is my fetish",
                "No browsing history here ;)"
        };
        frame = new JFrame("OctyBooru - " + title[(int)(Math.random() * title.length)]);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.setSize(width, height);
        mainPanel.setSize(width, height);
        statusDisplay.setVisible(false);
        frame.setVisible(true);
        addTheTagButton.addActionListener(new tagListener());
        getImageButton.addActionListener(new imageGrabberListener());
        fileSelectorButton.addActionListener(new fileSelectorListener());
        uploadImageButton.addActionListener(new uploadListener());
    }

    public static TestingGroundFrame getInstance(){
        if(oFrame == null)
            oFrame = new TestingGroundFrame();
        return oFrame;
    }


}
