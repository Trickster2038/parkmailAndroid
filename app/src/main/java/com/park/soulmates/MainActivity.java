package com.park.soulmates;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
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
                                                             switch (menuItem.getItemId()) {
                                                                 case (R.id.tabFeed):
                                                                     s = "tab1";
                                                                     break;
                                                                 case (R.id.tabMatches):
                                                                     s = "tab2";
                                                                     break;
                                                                 case (R.id.tabProfile):
                                                                     s = "tab3";
                                                                     break;
                                                             }
                                                             Toast msg = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
                                                             msg.show();
                                                             return true;
                                                         }
                                                     }
        );
    }
}