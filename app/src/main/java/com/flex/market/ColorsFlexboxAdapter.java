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

import java.util.ArrayList;

public class ColorsFlexboxAdapter extends RecyclerView.Adapter<ColorsFlexboxAdapter.ColorViewHolder> {
    static final ArrayList<Color> colors = new ArrayList<>();
    static int selectedColorID = -1;
    private ColorViewHolder previousSelectedViewHolder;
    private final Context Context;

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
        if (selectedColorID == colors.get(i).ID) {
            previousSelectedViewHolder = viewHolder;

            viewHolder.layout.setCardBackgroundColor(
                    Context.getResources().getColor(R.color.colorControlHighlight)
            );
        } else {
            viewHolder.layout.setCardBackgroundColor(
                    Context.getResources().getColor(android.R.color.white)
            );
        }

        GlideApp.with(Context)
                .asBitmap()
                .fitCenter()
                .load(MarketAPI.SERVER + "colors/" + colors.get(i).RAL + ".jpg")
                .into(viewHolder.image);

        viewHolder.name.setText(String.valueOf(colors.get(i).RAL));

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

    private void clickEvent(final View v, final ColorViewHolder viewHolder) {
        if (previousSelectedViewHolder != null) {
            if (previousSelectedViewHolder == viewHolder) {
                viewHolder.layout.setCardBackgroundColor(
                        v.getResources().getColor(android.R.color.white)
                );

                previousSelectedViewHolder = null;
                selectedColorID = -1;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MarketAPI.GetCoverings(v.getContext());
                    }
                }).start();

                return;
            }

            previousSelectedViewHolder.layout.setCardBackgroundColor(
                    v.getResources().getColor(android.R.color.white)
            );
        }

        selectedColorID = colors.get(viewHolder.getAdapterPosition()).ID;

        if (CoveringsFlexboxAdapter.selectedCoveringID != -1) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MarketAPI.GetCustomComboPrice(v.getContext());
                }
            }).start();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                MarketAPI.GetCoveringsBySelectedColors(v.getContext());
            }
        }).start();

        viewHolder.layout.setCardBackgroundColor(
                v.getResources().getColor(R.color.colorControlHighlight)
        );

        previousSelectedViewHolder = viewHolder;
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {
        final CardView layout;
        final TextView name;
        final ImageView image;

        ColorViewHolder(View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layoutColor);
            name = itemView.findViewById(R.id.textViewColor);
            image = itemView.findViewById(R.id.imageViewColor);
        }
    }
}
