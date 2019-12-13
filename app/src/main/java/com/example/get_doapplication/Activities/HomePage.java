package com.example.get_doapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.get_doapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_get:
                        Toast.makeText(HomePage.this, "get fragment", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_do:
                        Toast.makeText(HomePage.this, "do fragment", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_home:
                        Toast.makeText(HomePage.this, "home fragment", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_notify:
                        Toast.makeText(HomePage.this, "notification fragment", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_setting:
                        Toast.makeText(HomePage.this, "setting fragment", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

    }
}
