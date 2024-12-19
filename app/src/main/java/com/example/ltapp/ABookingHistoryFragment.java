package com.example.ltapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ABookingHistoryFragment extends Fragment {

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

        // Load booking data
        loadBookingData();

        return view;
    }

    private void loadBookingData() {
        // Fetch all bookings from database
        bookingList = db.getAllBookings();

        // Set up the RecyclerView and Adapter
        bookingAdapter = new BookingAdapter(getContext(), bookingList);
        recyclerViewBookings.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewBookings.setAdapter(bookingAdapter);
    }
}
