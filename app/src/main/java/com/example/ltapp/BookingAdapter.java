package com.example.ltapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private List<booking> bookings;
    private Context context;

    public BookingAdapter(Context context, List<booking> bookings) {
        this.context = context;
        this.bookings = bookings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        booking booking = bookings.get(position);
        holder.date.setText(booking.getStringdate());
        holder.timeSlots.setText(booking.getTime_slots());
        holder.totalPrice.setText(String.valueOf(booking.getTotal_price()));
        holder.courtNumber.setText(String.valueOf(booking.getCourt_number()));
        holder.courtName.setText(booking.getCourt_name());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, timeSlots, totalPrice, courtNumber, courtName;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            timeSlots = itemView.findViewById(R.id.timeSlots);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            courtNumber = itemView.findViewById(R.id.courtNumber);
            courtName = itemView.findViewById(R.id.courtName);
        }
    }
}
