package com.park.soulmates.utils;

import android.app.Application;

import androidx.room.Room;

public class AppSingletone extends Application {
    public static AppSingletone instance;
    private UserDB database;
    private MessageDB databaseMsg;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, UserDB.class, "database")
                .build();
        databaseMsg = Room.databaseBuilder(this, MessageDB.class, "databaseMsg")
                .build();
    }

    public static AppSingletone getInstance() {
        return instance;
    }

    public UserDB getDatabase() {
        return database;
    }
    public MessageDB getDatabaseMsg() {
        return databaseMsg;
    }
}
