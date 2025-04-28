package com.example.sbdn;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RequestBloodActivity extends AppCompatActivity {

    AutoCompleteTextView bloodTypeDropdown, urgencyDropdown, hospitalDropdown;
    Button submitRequestBtn;
    ListView requestListView;
    ArrayList<String> requestList;
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);

        // Initialize views
        bloodTypeDropdown = findViewById(R.id.bloodTypeDropdown);
        urgencyDropdown = findViewById(R.id.urgencyDropdown);
        hospitalDropdown = findViewById(R.id.hospitalDropdown);
        submitRequestBtn = findViewById(R.id.submitRequestBtn);
        requestListView = findViewById(R.id.requestListView);

        // Set up dropdown options
        String[] bloodTypes = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        String[] urgencyLevels = {"Low", "Medium", "High", "Critical"};
        String[] hospitals = {"Hospital A", "Hospital B", "Hospital C"};

        bloodTypeDropdown.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, bloodTypes));
        urgencyDropdown.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, urgencyLevels));
        hospitalDropdown.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, hospitals));

        // Initialize request list
        requestList = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requestList);
        requestListView.setAdapter(listAdapter);

        // Submit button action with confirmation
        submitRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bloodType = bloodTypeDropdown.getText().toString().trim();
                String urgency = urgencyDropdown.getText().toString().trim();
                String hospital = hospitalDropdown.getText().toString().trim();

                if (!bloodType.isEmpty() && !urgency.isEmpty() && !hospital.isEmpty()) {
                    // Show confirmation popup
                    new AlertDialog.Builder(RequestBloodActivity.this)
                            .setTitle("Confirm Request")
                            .setMessage("Do you want to submit this blood request?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String status = "Request Status: Pending";
                                    String requestDetails = "Blood Type: " + bloodType
                                            + "\nUrgency: " + urgency
                                            + "\nHospital: " + hospital
                                            + "\nStatus: " + status;

                                    requestList.add(requestDetails);
                                    listAdapter.notifyDataSetChanged();

                                    // Clear fields
                                    bloodTypeDropdown.setText("");
                                    urgencyDropdown.setText("");
                                    hospitalDropdown.setText("");
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                } else {
                    Toast.makeText(RequestBloodActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
