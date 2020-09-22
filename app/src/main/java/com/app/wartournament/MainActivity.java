package com.app.wartournament;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
       FrameLayout newNotiIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newNotiIcon = findViewById(R.id.newNotiIcon);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(MainActivity.this);
        loadFragment(new GameFragment());
        navigation.getMenu().getItem(1).setChecked(true);
        newNotiIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Notificationlist.class);
                startActivity(i);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainContainer, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
        switch (item.getItemId()) {
            case R.id.nav_game: {
                fragment=new GameFragment();
                break;
            }
            case  R.id.nav_earn:{
                fragment = new EarnFragment();
                break;
            }
            case R.id.nav_account: {
                fragment = new MyAccountFragment();
                break;
            }
        }
        return loadFragment(fragment);
    }
}
