package com.flex.market;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;

public class ProductInfoFragment extends Fragment {
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
    }
}
