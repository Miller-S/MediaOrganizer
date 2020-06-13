package org.octavia.octaviaDatabase.connections;

import org.junit.Test;
import org.octavia.octaviaDatabase.dataTypes.Media;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class MediaDAOTest {


    @Test
    public void TestMedia() {
        Media media = new Media(1, "This is a real path");
        MediaDAO mdao = new MediaDAO();
        try {
            //test insert and get
            mdao.insertMedia(media);
            Media retrieved = mdao.getMediaFromPath(media.getPath());
            assertTrue(media.getPath().equalsIgnoreCase(retrieved.getPath()));

            //test update
            media.setMediaID(retrieved.getMediaID());
            media.setPath("This is also a real path");
            assertTrue(mdao.updateMedia(media));
            retrieved = mdao.getMediaFromID(media.getMediaID());
            assertTrue(retrieved.equals(media));

            //test delete
            assertTrue(mdao.deleteMedia(media.getMediaID()));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}