package com.example.medwheels_hos1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class patientInfo extends AppCompatActivity {

    TextView Name,Dob,Bloodg,Allergy,MedH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_patient_info);

        Name = findViewById(R.id.name1);
        Dob = findViewById(R.id.dob1);
        Bloodg = findViewById(R.id.group1);
        Allergy = findViewById(R.id.Allergies1);
        MedH = findViewById(R.id.Medical_History1);

        Name.setText(getIntent().getStringExtra("name"));
        Dob.setText(getIntent().getStringExtra("dob"));
        Bloodg.setText(getIntent().getStringExtra("bloodg"));
        Allergy.setText(getIntent().getStringExtra("allergy"));
        MedH.setText(getIntent().getStringExtra("medh"));


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}