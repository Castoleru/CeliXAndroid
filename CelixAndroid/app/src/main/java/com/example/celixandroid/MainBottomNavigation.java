package com.example.celixandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainBottomNavigation extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private  Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom_navigation);
        bottomNavigationView = findViewById(R.id.NavBar);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.accountFragment:
                    fragment = new AccountFragment();
                    loadFragment(fragment);
                    Toast.makeText(MainBottomNavigation.this, "Account",
                            Toast.LENGTH_SHORT).show();
                    break;
                case R.id.homeFragment:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    Toast.makeText(MainBottomNavigation.this, "Home",
                            Toast.LENGTH_SHORT).show();
                    break;
                case R.id.accSetingsFragment:
                    fragment = new AccSetingsFragment();
                    loadFragment(fragment);
                    Toast.makeText(MainBottomNavigation.this, "Settings",
                            Toast.LENGTH_SHORT).show();
                    break;

            }
            return true;
        });


    }

    public void loadFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, fragment);
        transaction.commit();
    }
}