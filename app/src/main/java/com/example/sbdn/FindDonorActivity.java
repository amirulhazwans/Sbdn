package com.example.sbdn;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FindDonorActivity extends AppCompatActivity {

    private RecyclerView donorRecyclerView;
    private DonorAdapter donorAdapter;
    private EditText searchDonorEditText;
    private List<Donor> donorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finddonor);

        donorRecyclerView = findViewById(R.id.donorRecyclerView);
        searchDonorEditText = findViewById(R.id.searchDonorEditText);

        // Sample donor data
        donorList = new ArrayList<>();
        donorList.add(new Donor("John Doe", "O+", "Kuala Lumpur"));
        donorList.add(new Donor("Jane Smith", "A+", "Shah Alam"));
        donorList.add(new Donor("Mike Johnson", "B-", "Putrajaya"));

        // Set up RecyclerView with LinearLayoutManager
        donorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        donorAdapter = new DonorAdapter(donorList);
        donorRecyclerView.setAdapter(donorAdapter);

        searchDonorEditText.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                filterDonors(charSequence.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });
    }

    private void filterDonors(String query) {
        if (TextUtils.isEmpty(query)) {
            donorAdapter.updateList(donorList);
        } else {
            List<Donor> filteredList = new ArrayList<>();
            for (Donor donor : donorList) {
                if (donor.getName().toLowerCase().contains(query.toLowerCase()) ||
                        donor.getBloodType().toLowerCase().contains(query.toLowerCase()) ||
                        donor.getLocation().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(donor);
                }
            }
            donorAdapter.updateList(filteredList);
        }
    }
}
