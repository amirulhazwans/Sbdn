package com.example.sbdn;

import android.os.Bundle;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    Button saveProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        saveProfileBtn = findViewById(R.id.saveProfileBtn);
        saveProfileBtn.setOnClickListener(v ->
                Toast.makeText(ProfileActivity.this, "Profile saved successfully!", Toast.LENGTH_SHORT).show()
        );
    }
}