package com.example.medwheels_hos1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Ambulance_list extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    ArrayList<HelperClass_driver> driverList;
    DriverAdapter driverAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ambulance_list);

        recyclerView = findViewById(R.id.recyclerAmbulance);
        database = FirebaseDatabase.getInstance().getReference("drivers");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        driverList = new ArrayList<>();
        driverAdapter = new DriverAdapter(this, driverList);
        recyclerView.setAdapter(driverAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                driverList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String pass = dataSnapshot.child("pass").getValue(String.class);
                    String name = dataSnapshot.child("name").getValue(String.class);
                    Double latitudeValue = dataSnapshot.child("latitude").getValue(Double.class);
                    Double longitudeValue = dataSnapshot.child("longitude").getValue(Double.class);

                    double latitude = latitudeValue != null ? latitudeValue : 0.0;
                    double longitude = longitudeValue != null ? longitudeValue : 0.0;

                    HelperClass_driver helper = new HelperClass_driver(email, pass, latitude, longitude,name);
                    driverList.add(helper);
                }
                driverAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error case
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}