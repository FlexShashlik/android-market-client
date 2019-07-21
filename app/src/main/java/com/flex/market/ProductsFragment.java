package com.flex.market;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {
    static ImageListAdapter imageListAdapter;
    static List<Product> products = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageListAdapter.images.clear();
        MarketAPI.GetProducts(getContext());

        ListView listView = view.findViewById(R.id.listViewProducts);

        imageListAdapter = new ImageListAdapter(getContext());
        listView.setAdapter(imageListAdapter);
    }

    public static void SetProducts(List<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            ImageListAdapter.images.add(MarketAPI.SERVER + "images/" + products.get(i).ID + "." + products.get(i).ImageExtension);
        }

        imageListAdapter.notifyDataSetChanged();
    }
}
