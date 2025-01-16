package com.swb.netqq.module;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/16 
 **/
public class Sender {
    private long user_id;
    private String nickname;
    private String card;
    private String role;

    // Getters and Setters
    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
