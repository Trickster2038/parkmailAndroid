package com.park.soulmates;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LikePush {

    public LikePush() {
    }

    public static void push(String userGetterUID) {
        FirebaseAuth userAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users/".concat(Objects.requireNonNull(userAuth.getUid())).concat("/likes/").concat(userGetterUID)); // Key
        Like like = new Like(userGetterUID);
        ref.setValue(like);
        if (MatchPush.check(userAuth, userGetterUID, like)) {
            MatchPush.push(userAuth, userGetterUID);
        }
        Log.d("Like", "Like pushed");
    }
}

