package com.example.ltapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

public class AUserAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> users;
    private int selectedPosition = -1;
    private OnUserSelectedListener listener;

    public interface OnUserSelectedListener {
        void onUserSelected(User user);
    }

    public AUserAdapter(Context context, List<User> users, OnUserSelectedListener listener) {
        super(context, R.layout.user_list_item, users);
        this.context = context;
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false);
        }

        User user = users.get(position);

        TextView usernameTextView = convertView.findViewById(R.id.usernameTextView);
        TextView passwordTextView = convertView.findViewById(R.id.passwordTextView);
        TextView usertypeTextView = convertView.findViewById(R.id.usertypeTextView);

        usernameTextView.setText("Người dùng: " + user.getU_USERNAME());
        passwordTextView.setText("Mật khẩu: " + user.getU_PASSWORD());
        usertypeTextView.setText("Loại người dùng: " + user.getU_USERTYPE());

        // Change the background color of the selected item
        if (selectedPosition == position) {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_item_color));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.default_item_color));
        }

        convertView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            // Notify the listener about the selected item
            if (listener != null) {
                listener.onUserSelected(user);
            }
        });

        return convertView;
    }
}
