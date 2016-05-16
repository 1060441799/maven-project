package com.newframe.web.websocket;

import java.util.Date;

/**
 * Created by xm on 2016/4/25.
 */
public class WSMessageVO {
    private Integer topic;
    private String messageId;
    private Date messageDate;
    private String sendToId;
    private UserAware whoSend;
    private Object messageData;

    public Integer getTopic() {
        return topic;
    }

    public void setTopic(Integer topic) {
        this.topic = topic;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public String getSendToId() {
        return sendToId;
    }

    public void setSendToId(String sendToId) {
        this.sendToId = sendToId;
    }

    public UserAware getWhoSend() {
        return whoSend;
    }

    public void setWhoSend(UserAware whoSend) {
        this.whoSend = whoSend;
    }

    public Object getMessageData() {
        return messageData;
    }

    public void setMessageData(Object messageData) {
        this.messageData = messageData;
    }
}
