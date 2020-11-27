package com.park.soulmates.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.park.soulmates.models.AdvancedUserModel;
import com.park.soulmates.models.LikeModel;
import com.park.soulmates.models.MatchModel;
import com.park.soulmates.models.MessageModel;

public class FirebaseUtils {
    private static boolean isMatch;
    private static FirebaseAuth auth;
    private static String uid;

    public static void init(){
        auth = FirebaseAuth.getInstance();
        uid = auth.getUid();
    }

    public static void sendMessage(String targetUser, String message){

        FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(uid)
                .child("chats")
                .child(targetUser)
                .push()
                .setValue(new MessageModel(message,
                        FirebaseAuth.getInstance()
                                .getUid())
                );
        FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(targetUser)
                .child("chats")
                .child(uid)
                .push()
                .setValue(new MessageModel(message,
                        FirebaseAuth.getInstance()
                                .getUid())
                );
    }

    public static DatabaseReference getChatReference(String targetUser){
        DatabaseReference base = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users")
                .child(uid)
                .child("chats")
                .child(targetUser);
        return base;
    }

    public static void pushUser(String name, String surname, String bio, String birthdate,
                            String contacts, Boolean romanticSearch, Boolean gender, Boolean[] interests) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users/"+ uid); // Key
        AdvancedUserModel user = new AdvancedUserModel(uid, name, surname, bio,
                birthdate, contacts, romanticSearch, gender, interests);
        ref.setValue(user);
        Log.d("dev_utils", "user pushed");
    }

    public static void pushLike(String userGetterUid) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users/"+ uid + "/likes/" + userGetterUid);
        LikeModel like = new LikeModel(userGetterUid);
        ref.setValue(like);
        Log.d("dev_utils", "Like pushed");
        if (checkMatch(auth, userGetterUid, like)) {
            pushMatch(auth, userGetterUid);
        }
    }

    public static void pushMatch(FirebaseAuth userAuth, String userGetterUid) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref1 = db.getReference("users/".concat(userAuth.getUid()).concat("/matches/").concat(userGetterUid));
        DatabaseReference ref2 = db.getReference("users/".concat(userGetterUid).concat("/matches/").concat(userAuth.getUid()));
        MatchModel matchObj1 = new MatchModel(userGetterUid);
        MatchModel matchObj2 = new MatchModel(userAuth.getUid());
        ref1.setValue(matchObj1);
        ref2.setValue(matchObj2);
        Log.d("MatchPusher", "match pushed");
    }

    public static boolean checkMatch(final FirebaseAuth userAuth, String userGetterUID, LikeModel like) {

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
                    isMatch = (value == 1); // almost always true
                    Log.d("MatchPusher", value.toString());
                } else {
                    Log.d("MatchPusher", "back like is null");
                    isMatch = false;
                }
                Log.d("MatchPusher", userAuth.getUid());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MatchPusher", "onCancelled: Something went wrong! Error:" + databaseError.getMessage());

            }
        });

        if (isMatch) {
            Log.d("MatchPusher", "its a match");
        } else {
            Log.d("MatchPusher", "its NOT a match");
        }

        return isMatch;
    }

}