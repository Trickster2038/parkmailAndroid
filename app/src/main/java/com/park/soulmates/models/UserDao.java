package com.park.soulmates.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Insert
    void Insert(AdvancedUserModel user);

    @Query("DELETE FROM AdvancedUserModel")
    void deleteAll();
}
