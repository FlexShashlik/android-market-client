package com.flex.market;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ColorFlexboxAdapter extends RecyclerView.Adapter<ColorFlexboxAdapter.ColorViewHolder> {
    static ArrayList<String> colors = new ArrayList<>();
    private Context Context;

    ColorFlexboxAdapter(Context context) {
        Context = context;
    }

    @NonNull
    @Override
    public ColorFlexboxAdapter.ColorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_color_recycler_item, viewGroup, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorFlexboxAdapter.ColorViewHolder viewHolder, int i) {
        GlideApp.with(Context)
                .asBitmap()
                .load(MarketAPI.SERVER + "colors/" + colors.get(i) + ".jpg")
                .into(viewHolder.image);

        viewHolder.name.setText(colors.get(i));
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image;

        ColorViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewColor);
            image = itemView.findViewById(R.id.imageViewColor);
        }
    }
}
