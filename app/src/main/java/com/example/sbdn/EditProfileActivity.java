package com.example.sbdn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editName, editPhone, editBloodGroup, editGender, editAddress;
    private Button saveProfileButton;
    private Button cancelProfileButton; // <-- Added this line

    private DatabaseReference databaseRef;
    private String userId = "user123"; // Replace with actual user ID if using FirebaseAuth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editBloodGroup = findViewById(R.id.editBloodGroup);
        editGender = findViewById(R.id.editGender);
        editAddress = findViewById(R.id.editAddress);
        saveProfileButton = findViewById(R.id.saveProfileButton);
        cancelProfileButton = findViewById(R.id.cancelProfileButton); // <-- Added this line

        Intent intent = getIntent();
        editName.setText(intent.getStringExtra("name"));
        editPhone.setText(intent.getStringExtra("phone"));
        editBloodGroup.setText(intent.getStringExtra("bloodGroup"));
        editGender.setText(intent.getStringExtra("gender"));
        editAddress.setText(intent.getStringExtra("address"));

        databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                String bloodGroup = editBloodGroup.getText().toString().trim();
                String gender = editGender.getText().toString().trim();
                String address = editAddress.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty() || bloodGroup.isEmpty() || gender.isEmpty() || address.isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save to Firebase
                databaseRef.child("name").setValue(name);
                databaseRef.child("phone").setValue(phone);
                databaseRef.child("bloodGroup").setValue(bloodGroup);
                databaseRef.child("gender").setValue(gender);
                databaseRef.child("address").setValue(address);

                Toast.makeText(EditProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

                // Go back to ProfileActivity
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        cancelProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Discard changes and go back to ProfileActivity
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
