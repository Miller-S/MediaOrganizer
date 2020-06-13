package org.octavia.octaviaGui.media.side;

import org.octavia.octaviaDatabase.connections.TagDAO;
import org.octavia.octaviaDatabase.connections.TagMediaLinkDAO;
import org.octavia.octaviaDatabase.dataTypes.Media;
import org.octavia.octaviaDatabase.OctyDB;
import org.octavia.octaviaDatabase.dataTypes.Tag;
import org.octavia.octaviaDatabase.dataTypes.TagMediaLink;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Model for the side section of the media viewing page.
 */
public class MediaSideModel {

    //Everything the media is tagged with
    ArrayList<Tag> tags = new ArrayList<>();
    //the media displayed
    Media media;

    /**
     * Constructor
     * Set the current media and then get the tags for the media
     * @param media
     */
    public MediaSideModel(Media media) {
        this.media = media;
        generateTags();
    }

    /**
     * Populate the tags list with what the media has been tagged with
     */
    private void generateTags(){
        //access database
        TagMediaLinkDAO linkDAO = new TagMediaLinkDAO();
        try {
            //get the tags
            tags = linkDAO.getTagsFromMediaID(media.getMediaID());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public int getMediaID() {
        return getMediaID();
    }

    /**
     * Set the media and get the tags for the new media
     * @param mediaID
     */
    public void setMedia(Media mediaID){
        this.media = mediaID;
        generateTags();
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

}
