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
        Like like = new Like(userAuth.getUid(), userGetterUID);
        ref.setValue(like);
        if(MatchPush.check(userAuth, userGetterUID, like)){
            MatchPush.push(userAuth, userGetterUID);
        }
        Log.d("DB_status","like pushed");
    }
}

