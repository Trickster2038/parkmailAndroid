package com.park.soulmates;

public class Like {
    String userSender, userGetter;

    Like(String sender, String getter) {
        this.userSender = sender;
        this.userGetter = getter;
    }

    public String getUserGetter() {
        return userGetter;
    }

    public String getUserSender() {
        return userSender;
    }

    public void setUserGetter(String userGetter) {
        this.userGetter = userGetter;
    }

    public void setUserSender(String userSender) {
        this.userSender = userSender;
    }
}