package com.park.soulmates;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    String TABFEED = "TABFEDD";
    String TABMATCHES = "TABMATCHES";
    String TABPROFILE = "TABPROFILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.tabFeed) {
                if (getSupportFragmentManager().findFragmentByTag(TABFEED) == null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder, new FeedFragment(), TABFEED)
                            .addToBackStack(TABFEED)
                            .commit();
                    Log.d("Tab", "Tab 1");
                    Log.d("Tab", String.valueOf(getSupportFragmentManager().getFragments()));
                } else {
                    getSupportFragmentManager().popBackStack(TABFEED, 0);
                    Log.d("Tab", "Tab 1 from backstack");
                }
                return true;

            } else if (itemId == R.id.tabMatches) {
                if (getSupportFragmentManager().findFragmentByTag(TABMATCHES) == null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder, new MatchesFragment(), TABMATCHES)
                            .addToBackStack(TABMATCHES)
                            .commit();
                    Log.d("Tab", "Tab 2");
                    Log.d("Tab", String.valueOf(getSupportFragmentManager().getFragments()));
                } else {
                    getSupportFragmentManager().popBackStack(TABMATCHES, 0);
                    Log.d("Tab", "Tab 2 from backstack");
                }
                return true;

            } else if (itemId == R.id.tabProfile) {
                if (getSupportFragmentManager().findFragmentByTag(TABPROFILE) == null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder, new ProfileFragment(), TABPROFILE)
                            .addToBackStack(TABPROFILE)
                            .commit();
                    Log.d("Tab", "Tab 3");
                    Log.d("Tab", String.valueOf(getSupportFragmentManager().getFragments()));
                } else {
                    getSupportFragmentManager().popBackStack(TABPROFILE, 0);
                    Log.d("Tab", "Tab 3 from backstack");
                }
                return true;
            }

            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.tabFeed);
    }
}