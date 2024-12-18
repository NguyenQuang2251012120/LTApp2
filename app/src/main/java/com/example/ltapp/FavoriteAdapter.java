package com.example.ltapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class FavoriteAdapter extends BaseAdapter {

    private Context context;
    private List<FavoriteItem> favoriteItems;

    public FavoriteAdapter(Context context, List<FavoriteItem> favoriteItems) {
        this.context = context;
        this.favoriteItems = favoriteItems;
    }

    @Override
    public int getCount() {
        return favoriteItems.size();
    }

    @Override
    public Object getItem(int position) {
        return favoriteItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        }

        // Bind data to the item view
        FavoriteItem item = favoriteItems.get(position);
        TextView nameTextView = convertView.findViewById(R.id.textViewFavoriteName);
        TextView districtTextView = convertView.findViewById(R.id.textViewFavoriteDistrict);
        TextView priceTextView = convertView.findViewById(R.id.textViewFavoritePrice);

        nameTextView.setText(item.getName());
        districtTextView.setText(item.getDistrict());
        priceTextView.setText(item.getPrice());

        return convertView;
    }
}

