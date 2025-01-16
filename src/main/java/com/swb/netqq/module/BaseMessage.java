package com.swb.netqq.module;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/16 
 **/
public class BaseMessage {
    @JsonProperty("time")
    private long time;

    @JsonProperty("self_id")
    private long self_id;

    @JsonProperty("post_type")
    private String post_type;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getSelf_id() {
        return self_id;
    }

    public void setSelf_id(long self_id) {
        this.self_id = self_id;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }
}
