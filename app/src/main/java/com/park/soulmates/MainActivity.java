package com.park.soulmates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Application;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomMenu = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                         @Override
                                                         public boolean onNavigationItemSelected(final MenuItem menuItem) {
                                                             String s = "default";
                                                             FragmentManager fm;
                                                             FragmentTransaction ft;
                                                             switch (menuItem.getItemId()) {
                                                                 case (R.id.tabFeed):
                                                                     s = "tab1";
                                                                     FeedFragment feedFragment = new FeedFragment();
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
                                                                     // ft.addToBackStack(null);
                                                                     ft.commit();
                                                                     break;
                                                                 case (R.id.tabProfile):
                                                                     s = "tab3";
                                                                     ProfileFragment profileFragment = new ProfileFragment();
                                                                     fm = getFragmentManager();
                                                                     ft = fm.beginTransaction();

                                                                     ft.replace(R.id.placeholder, profileFragment);
                                                                     // ft.addToBackStack(null);
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