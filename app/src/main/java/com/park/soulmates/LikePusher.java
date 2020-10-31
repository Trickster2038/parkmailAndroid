package com.park.soulmates;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LikePusher {

    public static void push(String userGetterUid) {
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users/".concat(userAuth.getUid()).concat("/likes/").concat(userGetterUid));
        LikeModel like = new LikeModel(userGetterUid);
        ref.setValue(like);
        Log.d("LikePusher", "Like pushed");
        if (MatchPusher.check(userAuth, userGetterUid, like)) {
            MatchPusher.push(userAuth, userGetterUid);
        }
    }
}

