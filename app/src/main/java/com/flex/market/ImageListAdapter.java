package com.flex.market;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

class ImageListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private List<String> imageUrls;

    static List<String> images = new ArrayList<>();

    ImageListAdapter(Context context) {
        super(context, R.layout.list_products_item, images);

        this.context = context;
        this.imageUrls = images;

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_products_item, parent, false);
        }

        GlideApp.with(context)
                .load(imageUrls.get(position))
                .into((ImageView) convertView);


        return convertView;
    }
}
