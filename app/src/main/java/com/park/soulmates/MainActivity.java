package com.park.soulmates;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.tabFeed) {
                getSupportFragmentManager().beginTransaction().replace(R.id.placeholder, new FeedFragment()).commit();
                Log.d("Tab", "Tab 1");
                return true;
            } else if (itemId == R.id.tabMatches) {
                getSupportFragmentManager().beginTransaction().replace(R.id.placeholder, new MatchesFragment()).commit();
                Log.d("Tab", "Tab 2");
                return true;
            } else if (itemId == R.id.tabProfile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.placeholder, new ProfileFragment()).commit();
                Log.d("Tab", "Tab 3");

                // test of user push
                // UserPusher.push("testName","testSurname","testBio", "02.02.2001", true, new Boolean[]{true,false,true,false,false});

                return true;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.tabFeed);
    }
}