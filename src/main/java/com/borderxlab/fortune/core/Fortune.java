package com.borderxlab.fortune.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Fortune Data Model
 */
public class Fortune {
    private int id;
    private String content;

    public Fortune() {
        // Jackson deserialization
    }

    public Fortune(int id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("content")
    public String getContent() {
        return content;
    }
}


