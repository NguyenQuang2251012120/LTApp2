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


public class RegisterActivity extends AppCompatActivity {


    EditText username, password;

    TextView loginText;

    Button signupButton;

    DatabaseHelper databaseHelper ;
    MyDatabase database;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);


        username = findViewById(R.id.userNameText);

        password = findViewById(R.id.passwordText);

        signupButton = findViewById(R.id.signupButton);

        loginText = findViewById(R.id.textLogin);

        databaseHelper = new DatabaseHelper(this);
        database = new MyDatabase(this);


        loginText.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                startActivity(intent);

            }

        });



        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String type = "user";

                if(user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập tên tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    User userR = new User(0, user, pass, type);
                    if(database.checkUserExists(userR)) {
                        Toast.makeText(RegisterActivity.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                    } else {
                        if(database.insertUser(userR)) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            SharedPreferences sharedPreferences = getSharedPreferences("LoginUser", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("User", user);
                            editor.apply();
                            intent.putExtra("USERNAME", user);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });}}