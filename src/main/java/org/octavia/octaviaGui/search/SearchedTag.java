package org.octavia.octaviaGui.search;

import org.octavia.octaviaDatabase.dataTypes.Tag;

public class SearchedTag extends Tag {

    public enum SearchType {
        OR, AND, NOT
    }

    private SearchType type;


    public SearchedTag(Tag tag, SearchType type) {
        super(tag);
        this.type = type;
    }
    public SearchedTag(String tagName, int id, SearchType type){
        super(tagName, id);
        this.type = type;
    }

    public SearchedTag(String tagName, int id, String description, SearchType type){
        super(tagName, id, description);
        this.type = type;
    }

    public SearchedTag(String tagName, int id, String description){
        super(tagName, id, description);
        this.type = SearchType.AND;
    }

    public SearchType getType() {
        return type;
    }

    public void setType(SearchType type) {
        this.type = type;
    }
}
