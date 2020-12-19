package com.park.soulmates.models;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface UserDao {
    @Insert
    void Insert(AdvancedUserModel user);
}
