package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.finalproject.Fragments.CartFragment;
import com.example.finalproject.Fragments.HomeFragment;
import com.example.finalproject.Fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Successful_MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_main);

        bottomNavView = findViewById(R.id.bottomNavView);
        frameLayout = findViewById(R.id.FrameLayout);

        loadFragment(new HomeFragment());

        bottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                if (item.getItemId() == R.id.home_icon) {
                    fragment = new HomeFragment();
                    return loadFragment(fragment);
                }
                else if (item.getItemId() == R.id.cart_icon) {
                    fragment = new CartFragment();
                    return loadFragment(fragment);
                }
                else if (item.getItemId() == R.id.profile_icon)
                    fragment = new ProfileFragment();
                return loadFragment(fragment);
            }

        });
    }



    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.FrameLayout, fragment);
            transaction.commit();
            return true;
        }
        return false;
    }
}
