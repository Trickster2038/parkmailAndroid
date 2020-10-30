package com.park.soulmates;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MatchPush {
    static boolean match;

    public MatchPush(){}

    public static void push(FirebaseAuth userAuth, String userGetterUID){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref1 = db.getReference("users/".concat(userAuth.getUid()).concat("/matches/").concat(userGetterUID));
        DatabaseReference ref2 = db.getReference("users/".concat(userGetterUID).concat("/matches/").concat(userAuth.getUid()));
        Match matchObj1 = new Match(userGetterUID);
        Match matchObj2 = new Match(userAuth.getUid());
        ref1.setValue(matchObj1);
        ref2.setValue(matchObj2);
        Log.d("dev_DB_status","match pushed");
    }
    public static boolean check(final FirebaseAuth userAuth, String userGetterUID, Like like){
        Log.d("dev_DB_status","match checking");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef = myRef.child("users").child(userGetterUID).child("likes").child(userAuth.getUid()).child("exist");

        // FIXME: extremely unstable because works asynchroniously
        //  (changes "match" after a while in backround, where takes data from DB),
        //  check match some other way
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value =  dataSnapshot.getValue(Integer.class);
                if(value != null){
                    // equals() goes wrong, maybe hashCode unindentity
                    match = (value == 1); // almost always true
                    Log.d("dev_DB_obj_search", value.toString());
                } else {
                    Log.d("dev_DB_match_check", "back like is null");
                    match = false;
                }
                Log.d("dev_DB_current_user", userAuth.getUid());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("dev_DB", "onCancelled: Something went wrong! Error:" + databaseError.getMessage() );

            }
        });

        if(match){
            Log.d("dev_Match", "its a match");
        } else {
            Log.d("dev_Match", "its NOT a match");
        }

        return match;
    }
}