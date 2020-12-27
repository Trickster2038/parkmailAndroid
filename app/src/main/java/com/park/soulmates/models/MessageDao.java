package com.park.soulmates.models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {
    @Insert
    void Insert(MessageModel msg);

    @Query("DELETE FROM MessageModel")
    void deleteAll();

    @Query("SELECT * FROM MessageModel")
    List<MessageModel> getAll();

    @Query("SELECT * FROM MessageModel WHERE secondUser == :sideUser")
    List<MessageModel> getDialog(String sideUser);


}