package com.borderxlab.fortune.core;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error Data Model
 */
public class Error {
    private int code;
    private String message;

    public Error() {
        // Jackson deserialization
    }

    public Error(String message) {
        this.message = message;
    }

    @JsonProperty("code")
    public int getCode() {
        return code;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }
}


