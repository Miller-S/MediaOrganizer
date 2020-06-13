package org.octavia;
import org.octavia.octaviaDatabase.OctyDB;
import org.octavia.octaviaDatabase.OctyPreparedStatements;
import org.octavia.octaviaDatabase.PoolTester;
import org.octavia.octaviaDatabase.dataTypes.Media;
import org.octavia.octaviaGui.OctyBooruFrame;
import org.octavia.octaviaGui.media.MediaPageController;
import org.octavia.octaviaGui.search.SearchPageController;
import org.octavia.octaviaGui.search.list.SearchListController;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{

    //@TODO: for testing purposes all the media in the database
    private static ArrayList<Media> media  = new ArrayList<>();

    /**
     * @TODO: for testing purposes initialize {@link #media}
     */
    private static void getImages() {
        OctyPreparedStatements prepped = OctyPreparedStatements.getInstance();
        try {
            //acquire all media in the database
            media.addAll(prepped.getAllMedia());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static void main( String[] args )
    {

        //get all the media in the database
        getImages();
        JPanel mainPanel;
        //@TODO: testing media page, get a random piece of media and display it
        OctyBooruFrame f = OctyBooruFrame.getInstance();
        int rand = (int)(media.size() * Math.random());
        MediaPageController mediaPageController = new MediaPageController(media.get(rand));
        mainPanel = mediaPageController.getMediaPagePanel();
        f.addTab("Media",mainPanel);
        //SearchListController controller = new SearchListController("yuri");
        //mainPanel = controller.getView();
        SearchPageController controller = new SearchPageController();
        mainPanel = controller.getView();
        f.addTab("Search", mainPanel);

        try {
            Connection con = PoolTester.getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("Select * From Media");
            while (rs.next()){
                System.out.println(rs.getString("path"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
