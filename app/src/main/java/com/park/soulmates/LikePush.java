package com.park.soulmates;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LikePush {
    public LikePush(){}

    public static void push(FirebaseAuth userAuth, String userGetterUID){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("likes/".concat(userAuth.getUid()).concat(userGetterUID)); // Key
        ref.setValue("This is a test bio-message"); // Value
        Like like = new Like(userAuth.getUid(), userGetterUID);
        ref.setValue(like);
        Log.d("DB_status","like pushed");
    }
}

