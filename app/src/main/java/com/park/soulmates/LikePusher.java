package com.park.soulmates;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LikePusher {

    public LikePusher() {
    }

    public static void push(String userGetterUID) {
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users/".concat(userAuth.getUid()).concat("/likes/").concat(userGetterUID));
        LikeModel like = new LikeModel(userGetterUID);
        ref.setValue(like);
        Log.d("dev_DB_status", "like pushed");
        if (MatchPusher.check(userAuth, userGetterUID, like)) {
            MatchPusher.push(userAuth, userGetterUID);
        }
    }
}

