package com.flex.market;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

        MarketAPI.GetProducts(getContext());

        productsListAdapter = new ProductsListAdapter(getContext());
        listView.setAdapter(productsListAdapter);
    }
}
