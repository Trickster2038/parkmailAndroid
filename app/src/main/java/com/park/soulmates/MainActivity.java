package com.park.soulmates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Application;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: this is simple test of firebase-auth, make individual activity later - Serge
        FirebaseApp.initializeApp(getApplicationContext());
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // Already signed in
            // Do nothing
            Log.d("Auth_status","already login");
            Toast msg = Toast.makeText(getApplicationContext(), "already signed", Toast.LENGTH_LONG);
            msg.show();
        } else {
            auth.signInWithEmailAndPassword("fzastahov@gmail.com", "testpass")
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // User signed in successfully
                                Log.d("Auth_status","new succces login");
                                Toast msg = Toast.makeText(getApplicationContext(), "sign as fz..@gmail.com", Toast.LENGTH_LONG);
                                msg.show();
                            }
                        }
                    });
        }
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users/".concat(auth.getUid().concat("/Bio"))); // Key
        ref.setValue("This is a test bio-message"); // Value
        Log.d("DB_status","db_main - OK");

        // end of tests, +- normal code start
        BottomNavigationView bottomMenu = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                         @Override
                                                         public boolean onNavigationItemSelected(final MenuItem menuItem) {
                                                             String s = "default";
                                                             FragmentManager fm;
                                                             FragmentTransaction ft;

                                                             // TODO: make code DRY (use function for transaction)
                                                             switch (menuItem.getItemId()) {
                                                                 case (R.id.tabFeed):
                                                                     s = "tab1";
                                                                     FeedFragment feedFragment = new FeedFragment(auth);
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
                                                     }
        );
    }
}