package com.example.ltapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewBookings;
    private BookingAdapter bookingAdapter;
    private MyDatabase db;
    private List<booking> bookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        recyclerViewBookings = findViewById(R.id.recyclerViewBookings);
        db = new MyDatabase(this);

        // Assume you pass the username or user ID to fetch specific user's bookings
        String username = getIntent().getStringExtra("USERNAME");

        // Load booking data
        loadBookingData(username);
    }

    private void loadBookingData(String username) {
        // Fetch bookings from database based on username
        bookingList = db.getBookingsByUsername(username);

        // Set up the RecyclerView and Adapter
        bookingAdapter = new BookingAdapter(this, bookingList);
        recyclerViewBookings.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBookings.setAdapter(bookingAdapter);
    }
}

