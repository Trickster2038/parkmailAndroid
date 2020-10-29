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
        //DatabaseReference ref = db.getReference("matches/".concat(userAuth.getUid()).concat(userGetterUID));
        Match matchObj = new Match();
        ref1.setValue(matchObj);
        ref2.setValue(matchObj);
        Log.d("log DB_status","match pushed");
    }
    public static boolean check(final FirebaseAuth userAuth, String userGetterUID, Like like){
        Log.d("log DB_status","match checking");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef = myRef.child("users").child(userGetterUID).child("likes").child(userAuth.getUid()).child("exist");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value =  dataSnapshot.getValue(Integer.class);
                if(value != null){
                    // equals() goes wrong, maybe hashCode unindentity
                    match = (value == 1); // almost always true
                    Log.d("log DB obj search", value.toString());
                } else {
                    Log.d("log DB", "back like is null");
                    match = false;
                }
                Log.d("log DB current user", userAuth.getUid());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("log DB", "onCancelled: Something went wrong! Error:" + databaseError.getMessage() );

            }
        });

        if(match){
            Log.d("log Match", "its a match");
        } else {
            Log.d("log Match", "its NOT a match");
        }

        return match;
    }
}