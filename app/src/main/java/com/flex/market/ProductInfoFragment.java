package com.flex.market;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.concurrent.TimeUnit;

public class ProductInfoFragment extends Fragment {
    static ColorFlexboxAdapter colorFlexboxAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageViewProduct = view.findViewById(R.id.imageViewProduct);
        TextView textViewProductName = view.findViewById(R.id.textViewProductName);
        TextView textViewProductPrice = view.findViewById(R.id.textViewProductPrice);

        GlideApp.with(view.getContext())
                .load(
                        MarketAPI.SERVER +
                                "images/" +
                                ProductsListAdapter.products.get
                                        (ProductsListAdapter.selectedProductID).ID +
                                "." +
                                ProductsListAdapter.products.get
                                        (ProductsListAdapter.selectedProductID).ImageExtension
                )
                .placeholder(R.drawable.ic_image_placeholder)
                .apply(new RequestOptions().transform(new FitCenter()))
                .into(imageViewProduct);

        textViewProductName.setText(ProductsListAdapter.products.get(
                ProductsListAdapter.selectedProductID).Name
        );

        textViewProductPrice.setText(
                String.valueOf(
                        ProductsListAdapter.products.get(
                                ProductsListAdapter.selectedProductID).Price
                )
        );


        RecyclerView recyclerView = view.findViewById(R.id.colorRecyclerView);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(
                view.getContext(),
                LinearLayoutManager.HORIZONTAL
        );


        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.FLEX_START);

        recyclerView.setLayoutManager(layoutManager);

        colorFlexboxAdapter = new ColorFlexboxAdapter(view.getContext());
        recyclerView.setAdapter(colorFlexboxAdapter);

        // Preventing lags
        new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(
                            getResources().getInteger(
                                    android.R.integer.config_mediumAnimTime
                            )
                    );
                    MarketAPI.GetColors(getContext());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
