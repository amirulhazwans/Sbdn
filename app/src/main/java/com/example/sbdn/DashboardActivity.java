package com.example.sbdn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    Button findDonorBtn, donateNowBtn, myProfileBtn, historyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findDonorBtn = findViewById(R.id.findDonorBtn);
        donateNowBtn = findViewById(R.id.donateNowBtn);
        myProfileBtn = findViewById(R.id.myProfileBtn);
        historyBtn = findViewById(R.id.historyBtn);

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

        // For Find Donor button, you can add a map or search feature later
    }
}