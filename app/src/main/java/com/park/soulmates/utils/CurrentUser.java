package com.park.soulmates.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.park.soulmates.models.AdvancedUserModel;

public class CurrentUser {
    private static volatile AdvancedUserModel sData;
    private static volatile Boolean sIsRomantic;
    private static volatile Boolean sGender;

    public static Boolean getGender() {
        return sGender;
    }

    public static void setGender(Boolean gender) {
        CurrentUser.sGender = gender;
    }

    public static Boolean getIsRomantic() {
        return sIsRomantic;
    }

    public static void setIsRomantic(Boolean isRomantic) {
        CurrentUser.sIsRomantic = isRomantic;
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

        sData = new AdvancedUserModel();
        DatabaseReference base = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getUid());
        //Log.d("dev_current", base.toString());

        // HOTFIXes for new users
        base.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sData = snapshot.getValue(AdvancedUserModel.class);
                if (sData != null) {
                    sIsRomantic = sData.getRomanticSearch();
                    sGender = sData.getGender();

                    SharedPreferences prefs = act.getSharedPreferences("profilePrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = prefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(sData);
                    prefsEditor.putString("UserSettings", json);
                    prefsEditor.apply();

                } else {
                    sIsRomantic = false;
                    sGender = false;
                }
                //Log.d("dev_current_snap", Boolean.toString(Data.getRomanticSearch()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                sIsRomantic = false;
                sGender = false;
            }
        });
    }
}
