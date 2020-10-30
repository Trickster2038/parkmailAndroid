package com.park.soulmates;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MatchPush {
    static boolean MATCH;

    public MatchPush(){}

    public static void push(FirebaseAuth userAuth, String userGetterUID){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref1 = db.getReference("users/".concat(Objects.requireNonNull(userAuth.getUid())).concat("/matches/").concat(userGetterUID));
        DatabaseReference ref2 = db.getReference("users/".concat(userGetterUID).concat("/matches/").concat(userAuth.getUid()));
        Match matchObj1 = new Match(userGetterUID);
        Match matchObj2 = new Match(userAuth.getUid());
        ref1.setValue(matchObj1);
        ref2.setValue(matchObj2);
        Log.d("Match", "Match pushed");
    }
    public static boolean check(final FirebaseAuth userAuth, String userGetterUID, Like like){
        Log.d("Match", "Match checking");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef = myRef.child("users").child(userGetterUID).child("likes").child(Objects.requireNonNull(userAuth.getUid())).child("exist");

        // FIXME: extremely unstable because works asynchroniously
        //  (changes "match" after a while in backround, where takes data from DB),
        //  check match some other way
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value =  dataSnapshot.getValue(Integer.class);
                if(value != null){
                    // equals() goes wrong, maybe hashCode unindentity
                    MATCH = (value == 1); // almost always true
                    Log.d("log DB obj search", value.toString());
                } else {
                    Log.d("log DB", "back like is null");
                    MATCH = false;
                }
                Log.d("log DB current user", userAuth.getUid());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("log DB", "onCancelled: Something went wrong! Error:" + databaseError.getMessage() );

            }
        });

        if (MATCH) {
            Log.d("log Match", "its a match");
        } else {
            Log.d("log Match", "its NOT a match");
        }

        return MATCH;
    }
}