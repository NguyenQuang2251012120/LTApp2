package com.example.ltapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class   CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<category> categories;
    private Context context;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public CategoryAdapter(Context context, List<category> categories, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.categories = categories;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        category category = categories.get(position);
        holder.categoryName.setText(category.getName());

        holder.itemView.setSelected(selectedPosition == position);
        holder.itemView.setOnClickListener(v -> {
            notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);
            onItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void updateCategory(int position, category newCategory) {
        categories.set(position, newCategory);
        notifyItemChanged(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        public ViewHolder(View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
        }
    }
}





