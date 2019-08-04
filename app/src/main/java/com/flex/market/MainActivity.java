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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_catalog:
                    selectedFragment = new CatalogFragment();
                    break;
                case R.id.navigation_shopping_list:
                    ClearBackStack();

                    selectedFragment = new ShoppingListFragment();
                    break;
                case R.id.navigation_profile:
                    ClearBackStack();

                    if (MarketAPI.token != null) {
                        selectedFragment = new ProfileFragment();
                    } else {
                        selectedFragment = new LoginFragment();
                    }
                    break;
            }

            if(selectedFragment != null)
            {
                transaction.replace(
                        R.id.fragment_container,
                        selectedFragment
                ).commit();
            }

            return true;
        }
    };

    private void ClearBackStack() {
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
    }
}
