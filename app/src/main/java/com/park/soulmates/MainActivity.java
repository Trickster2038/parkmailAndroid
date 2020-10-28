package com.park.soulmates;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tabFeed);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.tabFeed) {
                getFragmentManager().beginTransaction().replace(R.id.placeholder, new FeedFragment(mAuth)).commit();
                return true;
            } else if (itemId == R.id.tabMatches) {
                getFragmentManager().beginTransaction().replace(R.id.placeholder, new MatchesFragment()).commit();
                return true;
            } else if (itemId == R.id.tabProfile) {
                getFragmentManager().beginTransaction().replace(R.id.placeholder, new ProfileFragment()).commit();
                return true;
            }
            return false;
        });
    }
}