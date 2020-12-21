package com.park.soulmates.utils;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.park.soulmates.models.AdvancedUserModel;
import com.park.soulmates.models.MessageDao;
import com.park.soulmates.models.MessageModel;
import com.park.soulmates.models.UserDao;

@Database(entities = {MessageModel.class}, version = 1)
public abstract class MessageDB extends RoomDatabase {
    public abstract MessageDao messageDao();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
