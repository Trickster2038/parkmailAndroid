package com.park.soulmates.views.other;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.park.soulmates.utils.CustomLocationListener;
import com.park.soulmates.views.feed.FeedFragment;
import com.park.soulmates.views.matches.MatchesFragment;
import com.park.soulmates.utils.NotificationsMatches;
import com.park.soulmates.R;
import com.park.soulmates.utils.CurrentUser;
import com.park.soulmates.utils.FirebaseUtils;

import static com.park.soulmates.utils.CustomLocationListener.getActivity;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "Main activity started");

        //FirebaseAuth.getInstance().signOut();

        CurrentUser.init(getApplicationContext());
        FirebaseUtils.init();
        NotificationsMatches.startListening(MainActivity.this, MainActivity.class);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // ask permissions here using below code
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
        }

        CustomLocationListener.SetUpLocationListener(this, this);


        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.tabFeed) {
                getSupportFragmentManager().beginTransaction().replace(R.id.placeholder, new FeedFragment()).commit();
                Log.d("MainActivity", "Tab 1");
                return true;
            } else if (itemId == R.id.tabMatches) {
                getSupportFragmentManager().beginTransaction().replace(R.id.placeholder, new MatchesFragment()).commit();
                Log.d("MainActivity", "Tab 2");
                return true;
            } else if (itemId == R.id.tabProfile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.placeholder, new ProfileFragment()).commit();
                Log.d("MainActivity", "Tab 3");

                // TODO: explain why we use this scheme
                return true;
            } else if (itemId == R.id.tabFilter){
                getSupportFragmentManager().beginTransaction().replace(R.id.placeholder, new FiltersFragment()).commit();
                Log.d("MainActivity", "Tab 4");

                return true;
            }
            return false;
        });
        bottomNavigationView.setSelectedItemId(R.id.tabFeed);
    }
}