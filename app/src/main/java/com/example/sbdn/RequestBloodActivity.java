package com.example.sbdn;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RequestBloodActivity extends AppCompatActivity {

    Spinner bloodTypeSpinner, urgencySpinner, hospitalSpinner;
    EditText dateEditText, timeEditText;
    Button submitRequestBtn;
    ListView requestListView;
    ArrayList<String> requestList;
    ArrayAdapter<String> listAdapter;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

        // Set up toolbar with back button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Request Blood");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        bloodTypeSpinner = findViewById(R.id.bloodTypeSpinner);
        urgencySpinner = findViewById(R.id.urgencySpinner);
        hospitalSpinner = findViewById(R.id.hospitalSpinner);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        submitRequestBtn = findViewById(R.id.submitRequestBtn);
        requestListView = findViewById(R.id.requestListView);

        String[] bloodTypes = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        String[] urgencyLevels = {"Low", "Medium", "High", "Critical"};
        String[] hospitals = {"Hospital Shah Alam", "Hospital Klang", "Setia City Healthcare "};

        bloodTypeSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodTypes));
        urgencySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, urgencyLevels));
        hospitalSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hospitals));

        requestList = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requestList);
        requestListView.setAdapter(listAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("BloodRequests");

        dateEditText.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        dateEditText.setText(selectedDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        timeEditText.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            new TimePickerDialog(this,
                    (view, hourOfDay, minute) -> {
                        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        timeEditText.setText(selectedTime);
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true).show();
        });

        submitRequestBtn.setOnClickListener(v -> {
            String bloodType = bloodTypeSpinner.getSelectedItem().toString();
            String urgency = urgencySpinner.getSelectedItem().toString();
            String hospital = hospitalSpinner.getSelectedItem().toString();
            String date = dateEditText.getText().toString().trim();
            String time = timeEditText.getText().toString().trim();

            if (!date.isEmpty() && !time.isEmpty()) {
                new AlertDialog.Builder(this)
                        .setTitle("Confirm Request")
                        .setMessage("Do you want to submit this blood request?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            String status = "Pending";
                            String requestDetails = "Blood Type: " + bloodType
                                    + "\nUrgency: " + urgency
                                    + "\nHospital: " + hospital
                                    + "\nDate: " + date
                                    + "\nTime: " + time
                                    + "\nStatus: " + status;

                            requestList.add(requestDetails);
                            listAdapter.notifyDataSetChanged();

                            String requestId = databaseReference.push().getKey();
                            Map<String, String> requestMap = new HashMap<>();
                            requestMap.put("bloodType", bloodType);
                            requestMap.put("urgency", urgency);
                            requestMap.put("hospital", hospital);
                            requestMap.put("date", date);
                            requestMap.put("time", time);
                            requestMap.put("status", status);

                            if (requestId != null) {
                                databaseReference.child(requestId).setValue(requestMap);
                            }

                            dateEditText.setText("");
                            timeEditText.setText("");

                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            }
        });

        requestListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedRequest = requestList.get(position);
            if (selectedRequest.contains("Status: Pending")) {
                new AlertDialog.Builder(this)
                        .setTitle("Cancel Request")
                        .setMessage("Do you want to cancel this request?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            databaseReference.get().addOnSuccessListener(dataSnapshot -> {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    String bloodType = snapshot.child("bloodType").getValue(String.class);
                                    String urgency = snapshot.child("urgency").getValue(String.class);
                                    String hospital = snapshot.child("hospital").getValue(String.class);
                                    String date = snapshot.child("date").getValue(String.class);
                                    String time = snapshot.child("time").getValue(String.class);
                                    String status = snapshot.child("status").getValue(String.class);

                                    String requestDetails = "Blood Type: " + bloodType
                                            + "\nUrgency: " + urgency
                                            + "\nHospital: " + hospital
                                            + "\nDate: " + date
                                            + "\nTime: " + time
                                            + "\nStatus: " + status;

                                    if (selectedRequest.equals(requestDetails)) {
                                        snapshot.getRef().removeValue();
                                        requestList.remove(position);
                                        listAdapter.notifyDataSetChanged();
                                        Toast.makeText(this, "Request cancelled", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                            });
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                Toast.makeText(this, "Only pending requests can be cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Handle toolbar back button to DashboardActivity
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(RequestBloodActivity.this, DashboardActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
