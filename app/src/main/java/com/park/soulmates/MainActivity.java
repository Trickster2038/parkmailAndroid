package com.park.soulmates;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFY_ID = 103;
    // Идентификатор канала
    private static String CHANNEL_ID = "t channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("dev_", "main activity started");

        NotificationsMatches.startListening(MainActivity.this, MainActivity.class);

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.tabFeed) {
                getSupportFragmentManager().beginTransaction().replace(R.id.placeholder, new FeedFragment()).commit();
                Log.d("dev_Tab", "Tab 1");
                return true;
            } else if (itemId == R.id.tabMatches) {
                getSupportFragmentManager().beginTransaction().replace(R.id.placeholder, new MatchesFragment()).commit();
                Log.d("dev_Tab", "Tab 2");
                return true;
            } else if (itemId == R.id.tabProfile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.placeholder, new ProfileFragment()).commit();
                Log.d("dev_Tab", "Tab 3");

                // TODO: explain why we use this scheme
                return true;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.tabFeed);
    }
}