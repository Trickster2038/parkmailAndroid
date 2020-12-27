package com.park.soulmates.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
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
import com.park.soulmates.models.MessageDao;
import com.park.soulmates.models.MessageModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseUtils {
    private static boolean isMatch;
    private static FirebaseAuth auth;
    private static String uid;

    public static void init() {
        auth = FirebaseAuth.getInstance();
        uid = auth.getUid();
    }

    public static void sendMessage(String targetUser, String message) {

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

    public static DatabaseReference getChatReference(String targetUser) {
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

        DatabaseReference ref = db.getReference("users/" + uid);
        AdvancedUserModel user = new AdvancedUserModel(uid, name, surname, bio,
                birthdate, contacts, romanticSearch, gender, interests);

        ref.child("uid").setValue(user.getUid());
        ref.child("name").setValue(user.getName());
        ref.child("surname").setValue(user.getSurname());
        ref.child("bio").setValue(user.getBio());
        ref.child("birthdate").setValue(user.getBirthdate());
        ref.child("contacts").setValue(user.getContacts());
        ref.child("romanticSearch").setValue(user.getRomanticSearch());
        ref.child("gender").setValue(user.getGender());
        ref.child("interests").setValue(user.getInterests());
        ref.child("latitude").setValue(String.valueOf(CustomLocationListener.getCurrentLocation().getLatitude()));
        ref.child("longitude").setValue(String.valueOf(CustomLocationListener.getCurrentLocation().getLongitude()));

        //ref.setValue(user);
        Log.d("dev_utils", "user pushed");
    }

    public static void pushLike(String userGetterUid) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users/" + uid + "/likes/" + userGetterUid);
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
                    Log.d("dev_MatchPusher", value.toString());
                } else {
                    Log.d("dev_MatchPusher", "back like is null");
                    isMatch = false;
                }
                Log.d("dev_MatchPusher", userAuth.getUid());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("dev_MatchPusher", "onCancelled: Something went wrong! Error:" + databaseError.getMessage());

            }
        });

        if (isMatch) {
            Log.d("dev_MatchPusher", "its a match");
        } else {
            Log.d("dev_MatchPusher", "its NOT a match");
        }

        return isMatch;
    }

    public static void getMatchesAcc(ArrayList<AdvancedUserModel> userList, String targetUid, Activity act) {
        DatabaseReference ref = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users")
                .child(targetUid);
        Log.d("dev_mathes_down", ref.toString());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AdvancedUserModel user = snapshot.getValue(AdvancedUserModel.class);
                userList.add(user);
                Log.d("dev_mathes_down", user.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Log.d("dev_mathes_down", userList.toString());
    }

    public static void cacheChat(String targetUid, ArrayList<MessageModel> msgList){
        DatabaseReference databaseReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("chats")
                .child(targetUid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                if(td != null) {
                    for (Object i : td.values()) {
                        Log.d("dev_chat", i.toString());
                        //{messageText=2, messageTime=1606556691489, messageUser=VSLz6I1awOcWH8i9vWKg9uygvvJ2}
                        String[] parsed = i.toString().replaceAll("[{}]", "").split("[=,]");
                        //Log.d("dev_chat", parsed.toString());
                        for(String s: parsed){
                            Log.d("dev_chat", s);
                        }

                        // messageText | 2 | messageTime |  1606556691489 | messageUser | VSLz6I1awOcWH8i9vWKg9uygvvJ2
                        MessageModel msg = new MessageModel();
                        msg.setMessageText(parsed[1]);
                        msg.setMessageTime(Long.parseLong(parsed[3]));
                        msg.setMessageUser(parsed[5]);
                        msg.setSecondUser(targetUid);

                        msgList.add(msg);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
