package com.park.soulmates.utils;

import android.app.Application;

import androidx.room.Room;

public class AppSingletone extends Application {
    public static AppSingletone instance;
    private UserDB database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, UserDB.class, "database")
                .build();
    }

    public static AppSingletone getInstance() {
        return instance;
    }

    public UserDB getDatabase() {
        return database;
    }
}
