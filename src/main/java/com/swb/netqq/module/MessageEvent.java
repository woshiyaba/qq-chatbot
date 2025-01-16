package com.swb.netqq.module;

import java.util.List;

/**
 * @desc:
 * @author: cyj
 * @date: 2025/1/16 
 **/
public class MessageEvent extends BaseMessage {
    private long user_id;
    private long message_id;
    private long message_seq;
    private long real_id;
    private String message_type;
    private Sender sender;
    private String raw_message;
    private int font;
    private String sub_type;
    private List<Message> message;
    private String message_format;
    private long group_id;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getMessage_id() {
        return message_id;
    }

    public void setMessage_id(long message_id) {
        this.message_id = message_id;
    }

    public long getMessage_seq() {
        return message_seq;
    }

    public void setMessage_seq(long message_seq) {
        this.message_seq = message_seq;
    }

    public long getReal_id() {
        return real_id;
    }

    public void setReal_id(long real_id) {
        this.real_id = real_id;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public String getRaw_message() {
        return raw_message;
    }

    public void setRaw_message(String raw_message) {
        this.raw_message = raw_message;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public String getMessage_format() {
        return message_format;
    }

    public void setMessage_format(String message_format) {
        this.message_format = message_format;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }
}
