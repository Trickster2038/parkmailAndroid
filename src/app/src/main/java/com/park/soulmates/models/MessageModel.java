package com.park.soulmates.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class MessageModel {
    //@PrimaryKey @NonNull private String msgUid;
    private String mSecondUser;
    private String mMessageText;
    private String mMessageUser;
    @PrimaryKey
    private long messageTime;

    @Ignore
    public MessageModel(String messageText, String messageUser) {
        this.mMessageText = messageText;
        this.mMessageUser = messageUser;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public MessageModel() {

    }

    public String getMessageText() {
        return mMessageText;
    }

    public void setMessageText(String messageText) {
        this.mMessageText = messageText;
    }

    public String getMessageUser() {
        return mMessageUser;
    }

    public void setMessageUser(String messageUser) {
        this.mMessageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getSecondUser() {
        return mSecondUser;
    }

    public void setSecondUser(String secondUser) {
        this.mSecondUser = secondUser;
    }
}