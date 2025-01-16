package com.swb.netqq.module;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/16 
 **/
public class HeartBeatStatus {
    private boolean online;
    private boolean good;

    // Getters and Setters
    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }
}
