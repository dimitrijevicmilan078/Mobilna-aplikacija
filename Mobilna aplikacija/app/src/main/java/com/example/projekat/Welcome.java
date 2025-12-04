package com.example.projekat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class Welcome extends AppCompatActivity{

    DrawerLayout drawerLayout;
    ImageButton menu;
    NavigationView navigationView;
    Button euro, prem;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        drawerLayout = findViewById(R.id.drawer_layout);
        menu = findViewById(R.id.menu);
        navigationView = findViewById(R.id.navigationView);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_euro){
                    Intent intent = new Intent(Welcome.this, Euro.class);
                    startActivity(intent);
                    return true;
                }else if(id == R.id.nav_pl){
                    Intent intent = new Intent(Welcome.this, PL.class);
                    startActivity(intent);
                    return true;
                }else if(id == R.id.nav_logout){
                    finish();
                    return true;
                }
                drawerLayout.close();
                return true;
            }
        });

        euro = findViewById(R.id.euro);

        euro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, Euro.class);
                startActivity(intent);
            }
        });

        prem = findViewById(R.id.prem);

        prem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, PL.class);
                startActivity(intent);
            }
        });

        name = findViewById(R.id.text_name);
        String username = getIntent().getStringExtra("keyname");

        name.setText(username);

    }

    public static final int TIME_INTERVAL = 2000;

    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed+TIME_INTERVAL>System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else{
            Toast.makeText(this, "Tap back button again to exit.", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

}

