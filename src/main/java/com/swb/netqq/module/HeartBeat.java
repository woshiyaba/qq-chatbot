package com.swb.netqq.module;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/16 
 **/
public class HeartBeat extends BaseMessage {

    @JsonProperty("meta_event_type")
    private String meta_event_type;

    @JsonProperty("status")
    private HeartBeatStatus status;

    @JsonProperty("interval")
    private int interval;


    public String getMeta_event_type() {
        return meta_event_type;
    }

    public void setMeta_event_type(String meta_event_type) {
        this.meta_event_type = meta_event_type;
    }

    public HeartBeatStatus getStatus() {
        return status;
    }

    public void setStatus(HeartBeatStatus status) {
        this.status = status;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
