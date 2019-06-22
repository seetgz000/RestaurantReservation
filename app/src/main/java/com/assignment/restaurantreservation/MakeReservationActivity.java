package com.assignment.restaurantreservation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class MakeReservationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {



    String CurrentFragmentName = "HomeFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(this);

        loadFirstFragment(new HomeFragment());
    }

    private boolean loadFirstFragment(Fragment fragment){
        if(fragment != null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    private boolean loadFragment(Fragment fragment, String fragmentname){



        if(fragment != null){

            if(CurrentFragmentName == fragmentname){

                Log.d("test","same");

                return true;
            } else {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .addToBackStack(null)
                    .commit();

                CurrentFragmentName = fragmentname;
                Log.d("test","not same");

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;
        String fragmentname = null;

        switch(menuItem.getItemId()){
            case R.id.navigation_home:
                fragment = new HomeFragment();
                fragmentname = "HomeFragment";
                break;

            case R.id.navigation_reservation:
                fragment = new ReservationFragment();
                fragmentname = "ReservationFragment";
                break;

            case R.id.navigation_account:
                fragment = new AccountFragment();
                fragmentname = "AccountFragment";
                break;

        }
        return loadFragment(fragment, fragmentname);
    }
}
