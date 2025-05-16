package com.example.sbdn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    Button findDonorBtn, donateNowBtn, myProfileBtn, historyBtn ,logoutBtn;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findDonorBtn = findViewById(R.id.findDonorBtn);
        donateNowBtn = findViewById(R.id.donateNowBtn);
        myProfileBtn = findViewById(R.id.myProfileBtn);
        historyBtn = findViewById(R.id.historyBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        findDonorBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, FindDonorActivity.class);
            startActivity(intent);
        });


        myProfileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        donateNowBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, RequestBloodActivity.class);
            startActivity(intent);
        });

        historyBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        logoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear back stack
            startActivity(intent);
            finish(); // close dashboard
        });

        // For Find Donor button, you can add a map or search feature later
    }
}