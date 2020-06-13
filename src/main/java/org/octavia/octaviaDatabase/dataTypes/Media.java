package org.octavia.octaviaDatabase.dataTypes;

/**
 * Media data type to represent the sql table as a class
 */
public class Media {
    //the id of the media
    private int mediaID;
    //the path which the media is located
    private String path;

    /**
     * Constructor
     * @param mediaID
     * @param path
     */
    public Media(int mediaID, String path) {
        this.path = path;
        this.mediaID = mediaID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getMediaID() {
        return mediaID;
    }

    public void setMediaID(int mediaID) {
        this.mediaID = mediaID;
    }

    @Override
    public String toString() {
        return "Media{" +
                "mediaID=" + mediaID +
                ", path='" + path + '\'' +
                '}';
    }

    public boolean equals(Media obj) {
        return this.mediaID == obj.mediaID && this.path.equals(obj.path);
    }
}
