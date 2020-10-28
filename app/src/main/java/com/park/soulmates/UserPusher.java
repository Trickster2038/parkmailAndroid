package com.park.soulmates;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserPusher {
    public UserPusher(){

    }

    public static void push(String iName, String iSurname, String iBio, Boolean iRomanticSearch, Boolean[] iInterests){
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users/".concat(userAuth.getUid())); // Key
        AdvancedUserModel user = new AdvancedUserModel(iName, iSurname, iBio, iRomanticSearch, iInterests);
        ref.setValue(user);
        Log.d("log DB_status", "user pushed");
    }

}
