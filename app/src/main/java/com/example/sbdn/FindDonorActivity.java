package com.example.sbdn;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FindDonorActivity extends AppCompatActivity {

    private TextView infoTextView;
    private ImageView hospitalImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_donor);

        // Setup Toolbar with back navigation
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back arrow
            getSupportActionBar().setTitle("Find Donor"); // Set title
        }

        // Initialize views
        Spinner hospitalSpinner = findViewById(R.id.hospitalSpinner);
        infoTextView = findViewById(R.id.infoTextView);
        hospitalImageView = findViewById(R.id.hospitalImageView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.hospitals, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hospitalSpinner.setAdapter(adapter);

        hospitalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        infoTextView.setText("Hospital Shah Alam is a government hospital located at Persiaran Kayangan, Seksyen 7, 40000 Shah Alam, Selangor, Malaysia. It serves as a major public healthcare facility in the region, offering a wide range of medical services." +
                                "Address:Hospital Shah Alam\n" +
                                "Persiaran Kayangan, Seksyen 7\n" +
                                "40000 Shah Alam\n" +
                                "Selangor, Malaysia");
                        hospitalImageView.setImageResource(R.drawable.hospital1);
                        break;
                    case 1:
                        infoTextView.setText("Hospital Tengku Ampuan Rahimah (HTAR) is  a major government hospital located in Klang, Selangor, and is one of the busiest hospitals in Malaysia. It offers a wide range of healthcare services to the local population and surrounding areas." +
                                "Address: Jalan Langat, 41200 Klang, Selangor, Malaysia");
                        hospitalImageView.setImageResource(R.drawable.hospital2);
                        break;
                    case 2:
                        infoTextView.setText("Setia City Healthcare is a modern ambulatory care centre located in Setia Alam, Shah Alam, Selangor. Established in 2022 through a joint venture between S P Setia Berhad and Qualitas Medical Group Sdn Bhd, the centre aims to provide comprehensive medical services to the community. \n" +
                                "Address:\n" +
                                "R-G-7, 8 & 9, Setia City Residences,\n" +
                                "6, Jalan Setia Dagang AH U13/AH,\n" +
                                "Setia Alam, 40170 Shah Alam, Selangor, Malaysia");
                        hospitalImageView.setImageResource(R.drawable.hospital3);
                        break;
                    default:
                        infoTextView.setText("");
                        hospitalImageView.setImageDrawable(null);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                infoTextView.setText("");
            }
        });
    }

    // Handle back button in toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close this activity and return to previous
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
