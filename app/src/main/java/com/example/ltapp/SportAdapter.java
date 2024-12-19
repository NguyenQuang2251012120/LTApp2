package com.example.ltapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.ViewHolder> {
    private List<Sport> sports;
    private Context context;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public SportAdapter(Context context, List<Sport> sports, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.sports = sports;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sport sport = sports.get(position);
        holder.sportName.setText(sport.getS_NAME());
        holder.sportLocation.setText(sport.getS_LOCATION());
        holder.sportDistrict.setText(sport.getS_DISTRICT());
        holder.sportPrice.setText(sport.getS_PRICE());
        holder.sportDescription.setText(sport.getS_DESCRIPTION());
        holder.sportType.setText(sport.getS_TYPE());
        holder.sportService.setText(sport.getS_SERVICE());

        // Change the background color of the selected item
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_item_color));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.default_item_color));
        }

        holder.itemView.setOnClickListener(v -> {
            // Notify the listener of the selected position
            onItemClickListener.onItemClick(position);
            // Update the selected position and refresh the RecyclerView
            notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return sports.size();
    }

    public void updateSports(List<Sport> newSports) {
        sports.clear();
        sports.addAll(newSports);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sportName, sportLocation, sportDistrict, sportPrice, sportDescription, sportType, sportService;

        public ViewHolder(View itemView) {
            super(itemView);
            sportName = itemView.findViewById(R.id.sportName);
            sportLocation = itemView.findViewById(R.id.sportLocation);
            sportDistrict = itemView.findViewById(R.id.sportDistrict);
            sportPrice = itemView.findViewById(R.id.sportPrice);
            sportDescription = itemView.findViewById(R.id.sportDescription);
            sportType = itemView.findViewById(R.id.sportType);
            sportService = itemView.findViewById(R.id.sportService);
        }
    }
}
