package com.park.soulmates.utils;

import android.app.Application;

import androidx.room.Room;

public class AppSingletone extends Application {
    public static AppSingletone sInstance;
    private UserDB mDatabase;
    private MessageDB mDatabaseMsg;

    public static AppSingletone getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mDatabase = Room.databaseBuilder(this, UserDB.class, "database")
                .build();
        mDatabaseMsg = Room.databaseBuilder(this, MessageDB.class, "databaseMsg")
                .build();
    }

    public UserDB getDatabase() {
        return mDatabase;
    }

    public MessageDB getDatabaseMsg() {
        return mDatabaseMsg;
    }
}
