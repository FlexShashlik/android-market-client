package com.flex.market;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CoveringsFlexboxAdapter extends RecyclerView.Adapter<CoveringsFlexboxAdapter.CoveringViewHolder> {
    static ArrayList<Covering> coverings = new ArrayList<>();
    static int selectedCoveringID = -1;
    private static CoveringViewHolder previousSelectedViewHolder;

    private Context Context;

    public CoveringsFlexboxAdapter(Context context) {
        Context = context;
    }

    @NonNull
    @Override
    public CoveringViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_covering_recycler_item, viewGroup, false);
        return new CoveringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CoveringViewHolder viewHolder, int i) {
        if (selectedCoveringID == coverings.get(i).ID) {
            viewHolder.layout.setCardBackgroundColor(
                    Context.getResources().getColor(R.color.colorControlHighlight)
            );
        }

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (previousSelectedViewHolder != null) {
                    if (previousSelectedViewHolder == viewHolder) {
                        viewHolder.layout.setCardBackgroundColor(
                                v.getResources().getColor(android.R.color.white)
                        );

                        previousSelectedViewHolder = null;
                        selectedCoveringID = -1;

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                MarketAPI.GetColors(v.getContext());
                            }
                        }).start();

                        return;
                    }

                    previousSelectedViewHolder.layout.setCardBackgroundColor(
                            v.getResources().getColor(android.R.color.white)
                    );
                }

                selectedCoveringID = coverings.get(viewHolder.getAdapterPosition()).ID;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MarketAPI.GetColorsBySelectedCovering(v.getContext());
                    }
                }).start();

                viewHolder.layout.setCardBackgroundColor(
                        v.getResources().getColor(R.color.colorControlHighlight)
                );

                previousSelectedViewHolder = viewHolder;
            }
        });

        viewHolder.name.setText(coverings.get(i).Name);
    }

    @Override
    public int getItemCount() {
        return coverings.size();
    }

    class CoveringViewHolder extends RecyclerView.ViewHolder {
        CardView layout;
        TextView name;

        CoveringViewHolder(View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layoutCovering);
            name = itemView.findViewById(R.id.textViewCovering);
        }
    }
}
