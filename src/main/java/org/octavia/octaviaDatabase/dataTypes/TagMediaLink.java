package org.octavia.octaviaDatabase.dataTypes;

public class TagMediaLink {
    private int tagID;
    private int mediaID;

    public TagMediaLink(int tagID, int mediaID) {
        this.tagID = tagID;
        this.mediaID = mediaID;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public int getMediaID() {
        return mediaID;
    }

    public void setMediaID(int mediaID) {
        this.mediaID = mediaID;
    }
}
