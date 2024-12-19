package com.example.ltapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class BookingHistoryFragment extends Fragment {

    private RecyclerView recyclerViewBookings;
    private BookingAdapter bookingAdapter;
    private MyDatabase db;
    private List<booking> bookingList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_history, container, false);

        recyclerViewBookings = view.findViewById(R.id.recyclerViewBookings);
        db = new MyDatabase(getContext());

        // Retrieve the username from SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("User", null);

        if (username != null) {
            // Load booking data
            loadBookingData(username);
        } else {
            // Handle the error: Username not found in SharedPreferences
            // You might want to show an error message or log an error
        }

        return view;
    }

    private void loadBookingData(String username) {
        // Fetch bookings from database based on username
        bookingList = db.getBookingsByUsername(username);

        // Set up the RecyclerView and Adapter
        bookingAdapter = new BookingAdapter(getContext(), bookingList);
        recyclerViewBookings.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewBookings.setAdapter(bookingAdapter);
    }
}
