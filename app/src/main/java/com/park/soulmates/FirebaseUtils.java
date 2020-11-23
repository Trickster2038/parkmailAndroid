package com.park.soulmates;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

}
