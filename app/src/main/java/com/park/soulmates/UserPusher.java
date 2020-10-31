package com.park.soulmates;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserPusher {

    public static void push(String name, String surname, String bio, String birthdate,
                            String contacts, Boolean romanticsearch, Boolean gender, Boolean[] interests) {
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users/".concat(userAuth.getUid())); // Key
        AdvancedUserModel user = new AdvancedUserModel(userAuth.getUid(), name, surname, bio,
                birthdate, contacts, romanticsearch, gender, interests);
        ref.setValue(user);
        Log.d("UserPusher", "user pushed");
    }

}
