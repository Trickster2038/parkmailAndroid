package com.park.soulmates.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.park.soulmates.models.AdvancedUserModel;

public class CurrentUser {
    private static volatile AdvancedUserModel Data;
    private static volatile Boolean isRomantic;
    private static volatile Boolean gender;

    public static Boolean getGender() {
        return gender;
    }

    public static void setGender(Boolean gender) {
        CurrentUser.gender = gender;
    }

    public static Boolean getIsRomantic() {
        return isRomantic;
    }

    public static void setIsRomantic(Boolean isRomantic) {
        CurrentUser.isRomantic = isRomantic;
    }

//    public static AdvancedUserModel getData() {
//        return Data;
//    }
//
//    public static void setData(AdvancedUserModel data) {
//        Data = data;
//    }

    public static void init(Activity act) {
//        UserDB db = Room.databaseBuilder(ctx,
//            UserDB.class, "populus-database").build();




        Data = new AdvancedUserModel();
        DatabaseReference base = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getUid().toString());
        //Log.d("dev_current", base.toString());

        // HOTFIXes for new users
        base.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Data = snapshot.getValue(AdvancedUserModel.class);
                if(Data != null) {
                    isRomantic = Data.getRomanticSearch();
                    gender = Data.getGender();

                    SharedPreferences prefs = act.getSharedPreferences("profilePrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = prefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(Data);
                    prefsEditor.putString("UserSettings", json);
                    prefsEditor.apply();

                } else {
                    isRomantic = false;
                    gender = false;
                }
                //Log.d("dev_current_snap", Boolean.toString(Data.getRomanticSearch()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                isRomantic = false;
                gender = false;
            }
        });
    }
}
