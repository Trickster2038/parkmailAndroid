package com.park.soulmates;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UserPusher {
    public UserPusher() {

    }

    public static void push(String name, String surname, String bio, String birth,
                            String contacts, Boolean romanticSearch, Boolean gender, Boolean[] interests) {
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users/".concat(Objects.requireNonNull(userAuth.getUid()))); // Key
        UserModel user = new UserModel(userAuth.getUid(), name, surname, bio,
                birth, contacts, romanticSearch, gender, interests);
        ref.setValue(user);
        Log.d("log DB_status", "user pushed");
    }

}
