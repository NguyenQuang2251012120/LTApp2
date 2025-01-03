package com.example.ltapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PaymentNotification extends AppCompatActivity {

    TextView txtNotification;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_notification);
        txtNotification = findViewById(R.id.textViewNotify);
        btnBack = findViewById(R.id.button);

        Intent intent = getIntent();
        txtNotification.setText(intent.getStringExtra("result"));

        btnBack.setOnClickListener(v -> {
            Intent intent1 = new Intent(PaymentNotification.this, MainActivity.class);
            startActivity(intent1);
        });

    }
}