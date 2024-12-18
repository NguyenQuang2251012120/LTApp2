package com.example.ltapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private ListView listViewFavorites;
    private FavoriteAdapter favoriteAdapter;
    private List<FavoriteItem> favoriteItems = new ArrayList<>();
    private MyDatabase myDatabase;  // Database helper

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        listViewFavorites = rootView.findViewById(R.id.listViewFavorites);

        // Initialize database helper
        myDatabase = new MyDatabase(getActivity());

        // Fetch the favorite items for the logged-in user
        fetchFavoriteItems(getLoggedInUser());

        // Set up the adapter
        favoriteAdapter = new FavoriteAdapter(getActivity(), favoriteItems);
        listViewFavorites.setAdapter(favoriteAdapter);

        return rootView;
    }

    // Retrieve the logged-in user (you can implement this based on your app's login logic)
    private String getLoggedInUser() {
        SharedPreferences prefs = getActivity().getSharedPreferences("LoginUser", getActivity().MODE_PRIVATE);
        return prefs.getString("User", "default_user");
    }

    // Fetch favorite items for the logged-in user
    private void fetchFavoriteItems(String user) {
        Cursor cursor = myDatabase.getFavoritesByUser(user);  // Get the cursor for the user's favorites

        if (cursor.moveToFirst()) {
            do {
                String sportName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_FAVORITE_NAME));
                String district = cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_FAVORITE_DISTRICT));
                String price = cursor.getString(cursor.getColumnIndex(DatabaseHelper.C_FAVORITE_PRICE));
                favoriteItems.add(new FavoriteItem(sportName, district, price));
            } while (cursor.moveToNext());
        }

        cursor.close();
    }
}
