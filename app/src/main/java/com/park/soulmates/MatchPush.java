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
        DatabaseReference ref = db.getReference("matches/".concat(userAuth.getUid()).concat(userGetterUID));
        Match match = new Match(userAuth.getUid(), userGetterUID);
        ref.setValue(match);
        Log.d("DB_status","match pushed");
    }
    public static boolean check(final FirebaseAuth userAuth, String userGetterUID, Like like){
        Log.d("DB_status","match checking");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef = myRef.child("likes").child(userGetterUID.concat(userAuth.getUid())).child("userGetter");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value =  dataSnapshot.getValue(String.class);
                match = value.equals(userAuth.getUid());
                Log.d("DB obj search", value);
                Log.d("DB current user", userAuth.getUid());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DB", "onCancelled: Something went wrong! Error:" + databaseError.getMessage() );

            }
        });

        if(match){
            Log.d("Match", "its a match");
        } else {
            Log.d("Match", "its NOT a match");
        }

        return match;
    }
}