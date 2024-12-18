package com.example.ltapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AUserFragment extends Fragment {

    public static DatabaseHelper dbHelper;
    private EditText usernameEditText, passwordEditText;
    public AutoCompleteTextView searchAutoCompleteTextView;
    private RecyclerView horizontalUserRecyclerView;
    private Spinner usertypeSpinner;
    public static ListView userListView, horizontalUserListView;
    public AUserAdapter AuserAdapter;
    public List<User> userList;
    public  MyDatabase database;
    public int selectedUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DatabaseHelper(getActivity());
        database = new MyDatabase(getActivity());
        usernameEditText = view.findViewById(R.id.username);
        passwordEditText = view.findViewById(R.id.password);
        usertypeSpinner = view.findViewById(R.id.usertypeSpinner);
        userListView = view.findViewById(R.id.userListView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.usertype_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usertypeSpinner.setAdapter(adapter);
        loadUserList();

        userListView.setOnItemClickListener((parent, view1, position, id) -> {
            User selectedUser = userList.get(position);
            selectedUserId = selectedUser.getU_ID();
            usernameEditText.setText(selectedUser.getU_USERNAME());
            passwordEditText.setText(selectedUser.getU_PASSWORD());
            usertypeSpinner.setSelection(adapter.getPosition(selectedUser.getU_USERTYPE()));
        });

        view.findViewById(R.id.addUser).setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String usertype = usertypeSpinner.getSelectedItem().toString().trim();

            if (username.isEmpty() || password.isEmpty() || usertype.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập hết tất cả ô.", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(0, username, password, usertype);
            if (database.checkUserExists(user)) {
                Toast.makeText(getContext(), "Người dùng đã tồn tại!.", Toast.LENGTH_SHORT).show();
            } else {
                database.insertUser(user);
                loadUserList();
            }
        });

        view.findViewById(R.id.deleteUser).setOnClickListener(v -> {
            User user = new User(selectedUserId, "", "", "");
            database.deleteUserN(user);
            loadUserList();
        });

        view.findViewById(R.id.updateUser).setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String usertype = usertypeSpinner.getSelectedItem().toString().trim();

            if (username.isEmpty() || password.isEmpty() || usertype.isEmpty()) {
                Toast.makeText(getContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if the username already exists for a different user
            if (database.checkUsernameExistsExcludingCurrentUser(username)) {
                Toast.makeText(getContext(), "Username already exists.", Toast.LENGTH_SHORT).show();
            } else {
                User user = new User(selectedUserId, username, password, usertype);
                database.updateUser(user);
                loadUserList();
            }
        });


    }

    private void loadUserList() {
        userList = database.getAllUsers();
        AuserAdapter = new AUserAdapter(getActivity(), userList);
        userListView.setAdapter(AuserAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (database != null) {
            database.close();  // Close the database connection when the activity is destroyed
        }
    }
}
