package com.example.sbdn;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView nameText, phoneText, bloodGroupText, genderText, addressText;
    private Button editProfileButton;

    private DatabaseReference databaseRef;
    private String userId = "user123"; // Replace with actual user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Set up Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Show back arrow
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("My Profile");
        }

        nameText = findViewById(R.id.nameText);
        phoneText = findViewById(R.id.phoneText);
        bloodGroupText = findViewById(R.id.bloodGroupText);
        genderText = findViewById(R.id.genderText);
        addressText = findViewById(R.id.addressText);
        editProfileButton = findViewById(R.id.editProfileButton);

        databaseRef = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    nameText.setText(snapshot.child("name").getValue(String.class));
                    phoneText.setText(snapshot.child("phone").getValue(String.class));
                    bloodGroupText.setText(snapshot.child("bloodGroup").getValue(String.class));
                    genderText.setText(snapshot.child("gender").getValue(String.class));
                    addressText.setText(snapshot.child("address").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    // Handle toolbar back button press
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // Close this activity and go back
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            String bloodGroup = data.getStringExtra("bloodGroup");
            String gender = data.getStringExtra("gender");
            String address = data.getStringExtra("address");

            nameText.setText(name);
            phoneText.setText(phone);
            bloodGroupText.setText(bloodGroup);
            genderText.setText(gender);
            addressText.setText(address);

            databaseRef.child("name").setValue(name);
            databaseRef.child("phone").setValue(phone);
            databaseRef.child("bloodGroup").setValue(bloodGroup);
            databaseRef.child("gender").setValue(gender);
            databaseRef.child("address").setValue(address);
        }
    }
}
