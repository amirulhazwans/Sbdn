package com.example.sbdn;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ListView historyListView;
    ArrayList<String> historyList;
    ArrayAdapter<String> adapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // List setup
        historyListView = findViewById(R.id.historyListView);
        historyList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        historyListView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("BloodRequests");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                historyList.clear();
                for (DataSnapshot requestSnapshot : snapshot.getChildren()) {
                    String hospital = requestSnapshot.child("hospital").getValue(String.class);
                    String date = requestSnapshot.child("date").getValue(String.class);
                    String time = requestSnapshot.child("time").getValue(String.class);

                    if (hospital != null && date != null && time != null) {
                        String entry = "Hospital: " + hospital + "\nDate: " + date + "\nTime: " + time;
                        historyList.add(entry);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                historyList.clear();
                historyList.add("Failed to load data: " + error.getMessage());
                adapter.notifyDataSetChanged();
            }
        });
    }

    // Handle back navigation
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // Go back to previous activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
