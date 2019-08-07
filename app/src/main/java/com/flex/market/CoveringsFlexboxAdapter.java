package com.flex.market;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CoveringsFlexboxAdapter extends RecyclerView.Adapter<CoveringsFlexboxAdapter.CoveringViewHolder> {
    static ArrayList<String> coverings = new ArrayList<>();
    static int selectedCovering = -1;

    @NonNull
    @Override
    public CoveringViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_covering_recycler_item, viewGroup, false);
        return new CoveringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoveringViewHolder viewHolder, int i) {
        viewHolder.name.setText(coverings.get(i));
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
