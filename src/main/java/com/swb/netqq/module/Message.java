package com.swb.netqq.module;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/16 
 **/
public class Message {
    private String type;
    private JsonNode data;

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }
}
