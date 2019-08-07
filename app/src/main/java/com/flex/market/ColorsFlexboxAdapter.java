package com.flex.market;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ColorsFlexboxAdapter extends RecyclerView.Adapter<ColorsFlexboxAdapter.ColorViewHolder> {
    static ArrayList<String> colors = new ArrayList<>();
    static int selectedColor = -1;
    private static ColorViewHolder previousSelectedViewHolder;
    private Context Context;

    ColorsFlexboxAdapter(Context context) {
        Context = context;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_color_recycler_item, viewGroup, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ColorViewHolder viewHolder, int i) {
        GlideApp.with(Context)
                .asBitmap()
                .fitCenter()
                .load(MarketAPI.SERVER + "colors/" + colors.get(i) + ".jpg")
                .into(viewHolder.image);

        viewHolder.name.setText(colors.get(i));

        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEvent(v, viewHolder);
            }
        });

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEvent(v, viewHolder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }

    private void clickEvent(View v, ColorViewHolder viewHolder) {
        if (previousSelectedViewHolder != null) {
            previousSelectedViewHolder.layout.setCardBackgroundColor(
                    v.getResources().getColor(android.R.color.white)
            );
        }

        selectedColor = viewHolder.getAdapterPosition();

        Toast.makeText(v.getContext(), colors.get(selectedColor), Toast.LENGTH_LONG).show();

        viewHolder.layout.setCardBackgroundColor(
                v.getResources().getColor(R.color.colorControlHighlight)
        );

        previousSelectedViewHolder = viewHolder;
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {
        CardView layout;
        TextView name;
        ImageView image;

        ColorViewHolder(View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layoutColor);
            name = itemView.findViewById(R.id.textViewColor);
            image = itemView.findViewById(R.id.imageViewColor);
        }
    }
}
