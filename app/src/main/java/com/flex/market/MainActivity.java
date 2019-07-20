package com.flex.market;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        transaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_left, R.anim.exit_left, R.anim.enter_left);

        SingletonFragmentManager.getInstance().setManager(fragmentManager);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_container,
                    new CatalogFragment()
            ).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_catalog:
                    selectedFragment = new CatalogFragment();
                    break;
                case R.id.navigation_shopping_list:
                    selectedFragment = new ShoppingListFragment();
                    break;
                case R.id.navigation_profile:
                    if (MarketAPI.token != null) {
                        selectedFragment = new ProfileFragment();
                    } else {
                        selectedFragment = new LoginFragment();
                    }
                    break;
            }

            if(selectedFragment != null)
            {
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container,
                        selectedFragment
                ).commit();
            }

            return true;
        }
    };
}
