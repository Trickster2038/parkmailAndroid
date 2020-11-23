package com.park.soulmates;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.park.soulmates.models.LikeModel;
import com.park.soulmates.models.MatchModel;

public class MatchPusher {
    private static volatile boolean sMatch;

    public static void push(FirebaseAuth userAuth, String userGetterUid) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref1 = db.getReference("users/".concat(userAuth.getUid()).concat("/matches/").concat(userGetterUid));
        DatabaseReference ref2 = db.getReference("users/".concat(userGetterUid).concat("/matches/").concat(userAuth.getUid()));
        MatchModel matchObj1 = new MatchModel(userGetterUid);
        MatchModel matchObj2 = new MatchModel(userAuth.getUid());
        ref1.setValue(matchObj1);
        ref2.setValue(matchObj2);
        Log.d("MatchPusher", "match pushed");
    }

    public static boolean check(final FirebaseAuth userAuth, String userGetterUID, LikeModel like) {
        Log.d("MatchPusher", "match checking");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        // TODO: check LikeModel.getExist() instead of this reference
        myRef = myRef.child("users").child(userGetterUID).child("likes").child(userAuth.getUid()).child("exist");

        // FIXME: extremely unstable because works asynchronously
        //  (changes "match" after a while in background, where takes data from DB),
        //  check match some other way
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                if (value != null) {
                    // equals() goes wrong, maybe hashCode unindentity
                    sMatch = (value == 1); // almost always true
                    Log.d("MatchPusher", value.toString());
                } else {
                    Log.d("MatchPusher", "back like is null");
                    sMatch = false;
                }
                Log.d("MatchPusher", userAuth.getUid());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MatchPusher", "onCancelled: Something went wrong! Error:" + databaseError.getMessage());

            }
        });

        if (sMatch) {
            Log.d("MatchPusher", "its a match");
        } else {
            Log.d("MatchPusher", "its NOT a match");
        }

        return sMatch;
    }
}