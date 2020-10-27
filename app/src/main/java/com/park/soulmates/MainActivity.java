package com.park.soulmates;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users/".concat(mAuth.getUid().concat("/Bio"))); // Key
        ref.setValue("This is a test bio-message"); // Value
        Log.d("log DB_status", "db_main - OK");
        // end of tests, +- normal code start
        BottomNavigationView bottomMenu = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomMenu.setOnNavigationItemSelectedListener(menuItem -> {
                    String s = "default";
                    FragmentManager fm;
                    FragmentTransaction ft;

                    // TODO: make code DRY (use function for transaction)
                    switch (menuItem.getItemId()) {
                        case (R.id.tabFeed):
                            s = "tab1";
                            FeedFragment feedFragment = new FeedFragment(mAuth);
                            fm = getFragmentManager();
                            ft = fm.beginTransaction();

                            ft.replace(R.id.placeholder, feedFragment);
                            // ft.addToBackStack(null); - will be able in history by backButton
                            ft.commit();
                            break;
                        case (R.id.tabMatches):
                            s = "tab2";
                            MatchesFragment matchesFragment = new MatchesFragment();
                            fm = getFragmentManager();
                            ft = fm.beginTransaction();

                            ft.replace(R.id.placeholder, matchesFragment);
                            ft.commit();
                            break;
                        case (R.id.tabProfile):
                            s = "tab3";
                            ProfileFragment profileFragment = new ProfileFragment();
                            fm = getFragmentManager();
                            ft = fm.beginTransaction();

                            ft.replace(R.id.placeholder, profileFragment);
                            ft.commit();
                            break;
                    }
                    Toast msg = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
                    // msg.show(); - debug toast for tabs
                    return true;
                }
        );
    }
}