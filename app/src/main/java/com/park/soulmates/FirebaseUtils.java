package com.park.soulmates;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.park.soulmates.models.AdvancedUserModel;
import com.park.soulmates.models.LikeModel;
import com.park.soulmates.models.MessageModel;

public class FirebaseUtils {
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
        if (MatchPusher.check(auth, userGetterUid, like)) {
            MatchPusher.push(auth, userGetterUid);
        }
    }

}
