package com.flex.market;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.concurrent.TimeUnit;

public class ProductsFragment extends Fragment {
    static ProductsListAdapter productsListAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProductsListAdapter.products.clear();
        ListView listView = view.findViewById(R.id.listViewProducts);

        // Preventing lags
        new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(
                            getResources().getInteger(
                                    android.R.integer.config_mediumAnimTime
                            )
                    );
                    MarketAPI.GetProducts(getContext());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        productsListAdapter = new ProductsListAdapter(getContext());
        listView.setAdapter(productsListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductsListAdapter.selectedProductID = position;

                SingletonFragmentManager.getInstance()
                        .getManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .setCustomAnimations(R.anim.enter_left, R.anim.exit_left)
                        .replace(
                                R.id.fragment_container,
                                new ProductInfoFragment()
                        ).commit();
            }
        });
    }
}
