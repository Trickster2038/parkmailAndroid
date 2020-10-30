package com.park.soulmates;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LikePush {

    public LikePush() {
    }

    public static void push(String userGetterUID) {
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users/".concat(userAuth.getUid()).concat("/likes/").concat(userGetterUID));
        Like like = new Like(userGetterUID);
        ref.setValue(like);
        Log.d("dev_DB_status", "like pushed");
        if (MatchPush.check(userAuth, userGetterUID, like)) {
            MatchPush.push(userAuth, userGetterUID);
        }
    }
}

