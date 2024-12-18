package com.example.ltapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserSportAdapter extends RecyclerView.Adapter<UserSportAdapter.ViewHolder> {
    private List<Sport> sports;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Sport sport);
    }

    public UserSportAdapter(Context context, List<Sport> sports, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.sports = sports;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_item_user_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sport sport = sports.get(position);
        holder.sportType.setText(sport.getS_TYPE());
        holder.sportName.setText(sport.getS_NAME());
        holder.sportDistrict.setText(sport.getS_DISTRICT());
        try {
            float rating = Float.parseFloat(sport.getS_RATING());
            String formattedRating = String.format("%.1f", rating);  // Format to 1 decimal place
            holder.sportRating.setText(formattedRating);  // Set the formatted rating to the TextView
        } catch (NumberFormatException e) {
            // Handle case where the rating is not a valid float
            holder.sportRating.setText("N/A");
        }
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(sport));
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
        TextView sportType, sportName, sportDistrict, sportRating;

        public ViewHolder(View itemView) {
            super(itemView);
            sportType = itemView.findViewById(R.id.sportType);
            sportName = itemView.findViewById(R.id.sportName);
            sportDistrict = itemView.findViewById(R.id.sportLocation);
            sportRating = itemView.findViewById(R.id.sportRating);
        }
    }
}
