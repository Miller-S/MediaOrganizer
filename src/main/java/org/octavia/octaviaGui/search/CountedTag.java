package org.octavia.octaviaGui.search;

import org.octavia.octaviaDatabase.dataTypes.Tag;

public class CountedTag extends Tag {

    //number of uses of the tag
    private int count;

    public CountedTag(String tagName, int tagID, String description, int count) {
        super(tagName, tagID, description);
        this.count = count;
    }

    public CountedTag(String tagName, int tagID, int count) {
        super(tagName, tagID);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return tagName + "   " + count;
    }
}
