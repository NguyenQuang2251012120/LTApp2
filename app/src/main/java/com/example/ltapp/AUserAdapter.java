package com.example.ltapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AUserAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> users;

    public AUserAdapter(Context context, List<User> users) {
        super(context, R.layout.user_list_item, users);
        this.context = context;
        this.users = users;
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

        usernameTextView.setText(user.getU_USERNAME());
        passwordTextView.setText(user.getU_PASSWORD());
        usertypeTextView.setText(user.getU_USERTYPE());

        return convertView;
    }
}


