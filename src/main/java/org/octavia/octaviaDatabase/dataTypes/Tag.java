package org.octavia.octaviaDatabase.dataTypes;

/**
 * Tag data type to represent the sql table as a class
 */
public class Tag {
    //Name of the tag
    protected String tagName;
    //The id of the tag
    private int tagID;
    //The description of the tag
    private String description;


    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getTagID() {
        return tagID;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tag(String tagName, int tagID, String description) {
        this.tagName = tagName;
        this.tagID = tagID;
        this.description = description;
    }

    public Tag(Tag tag) {
        this.tagName = tag.tagName;
        this.tagID = tag.tagID;
        this.description = tag.description;
    }

    public Tag(String tagName, int tagID) {
        this(tagName,tagID,null);
    }

    public Tag() {
        this(null, 0);
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Tag(int tagID) {
        this.tagID = tagID;
    }
    @Override
    public String toString() {
        return tagName;
    }
}
