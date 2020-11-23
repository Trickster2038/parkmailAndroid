package com.park.soulmates;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.park.soulmates.models.MatchModel;

public class NotificationsMatches {
    private static int mCount=0;
    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;
    // Идентификатор канала
    private static String CHANNEL_ID = "match channel";
    private static String CHANNEL_NAME = "m channel";

    public NotificationsMatches(){}

    // MainActivity context and class
    public static void startListening(Context context, Class contextClass){
        MatchModel match;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //TODO: rewrite all paths in one style
        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid()).child("matches");
        Log.d("dev_notices_init_acc", mAuth.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // fixes that first "DataChange" is just reading from DB, so meanless notification appears
                if(mCount > 0) {
                    NotificationsMatches.notify(context, contextClass);
                }
                mCount++;

//                holder.title.setText(user.getName().concat(" ").concat(user.getSurname()));
//                holder.uid.setText(user.getUid());
//                holder.contacts.setText(user.getContacts());
//                NotificationManagerCompat notificationManager =
//                        NotificationManagerCompat.from(context);
//                NotificationCompat.Builder builder =
//                        new NotificationCompat.Builder(context, CHANNEL_ID)
//                                .setSmallIcon(R.drawable.ic_heart)
//                                .setContentTitle("Match")
//                                .setContentText("You got a new match")
//                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                                //.setContentIntent(contentIntent);
//                notificationManager.notify(NOTIFY_ID, builder.build());
//                Log.d("dev_notices","notices tick");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("The read failed: ", String.valueOf(databaseError.getCode()));
            }
        });
    }

    public static void notify(Context ctx, Class contextClass){
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        // create channel in new versions of android
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
        }


        // show notification
        Intent intent = new Intent(ctx, contextClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // 0 is request code
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(ctx, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_heart)
                        .setContentTitle("Match")
                        .setContentText("you got a new match")
                        .setAutoCancel(true)
                        //.setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        // 0 is id of notification
        notificationManager.notify(0, notificationBuilder.build());

    }
}

