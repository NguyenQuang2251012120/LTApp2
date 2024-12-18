package com.example.ltapp;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import android.widget.TextView;

import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    TextView signupText;
    Button loginButton;
    DatabaseHelper databaseHelper;
    MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.userNameText);
        password = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.textSignup);
        databaseHelper = new DatabaseHelper(this);
        database = new MyDatabase(this);

        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tài khoản VÀ mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    User loginUser = new User(0, user, pass, "");
                    String[] loginResult = database.checkLogin(loginUser);
                    if (loginResult != null) {
                        String loggedInUserName = loginResult[0];
                        String userType = loginResult[1];
                        Intent intent;
                        if ("admin".equals(userType)) {
                            intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                        } else {
                            intent = new Intent(LoginActivity.this, MainActivity.class);
                        }
                        intent.putExtra("USERNAME", loggedInUserName);
                        SharedPreferences sharedPreferences = getSharedPreferences("LoginUser", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("User", user);
                        editor.apply();
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}



