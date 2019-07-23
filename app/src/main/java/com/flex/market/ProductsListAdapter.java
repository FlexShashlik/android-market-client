package com.flex.market;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

class ProductsListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private RequestOptions requestOptions = new RequestOptions();

    static List<Product> products = new ArrayList<>();

    ProductsListAdapter(Context context) {
        super(context, R.layout.list_products_item, products);

        this.context = context;

        inflater = LayoutInflater.from(context);
        requestOptions = requestOptions.transform(new FitCenter(), new RoundedCorners(32));
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_products_item, parent, false);
        }

        GlideApp.with(context)
                .load(
                MarketAPI.SERVER +
                        "images/" +
                        products.get(position).ID +
                        "." +
                        products.get(position).ImageExtension
                )
                .placeholder(R.drawable.ic_image_placeholder)
                .apply(requestOptions)
                .into((ImageView) convertView.findViewById(R.id.imageViewProduct));

        TextView tvProductName = convertView.findViewById(R.id.textViewProductName);
        tvProductName.setText(products.get(position).Name);

        TextView tvProductPrice = convertView.findViewById(R.id.textViewProductPrice);
        tvProductPrice.setText(String.valueOf(products.get(position).Price));

        return convertView;
    }
}
