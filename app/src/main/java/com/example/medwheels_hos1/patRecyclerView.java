package com.example.medwheels_hos1;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class patRecyclerView extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    ArrayList<HelperClass> list;
    Myadapter myAdapter;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    double mylongitude,mylatitude;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pat_recycler_view);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    mylatitude = location.getLatitude()*Math.PI/180;
                    mylongitude = location.getLongitude()*Math.PI/180;
                    while(true)
                    {
                        if(mylatitude!=0.0 && mylongitude!=0.0)
                            break;
                    }

                    Log.d("LocationUpdate", "Latitude: " + mylatitude + ", Longitude: " + mylongitude);

                }
            }
        };

        requestLocationUpdates();

        recyclerView = findViewById(R.id.recyclerView);
        database = FirebaseDatabase.getInstance().getReference("patient");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new Myadapter(this, list);
        recyclerView.setAdapter(myAdapter);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(patRecyclerView.this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(patRecyclerView.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
            }
        }
        
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();



                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    HelperClass helper = dataSnapshot.getValue(HelperClass.class);

                    Double latitude = (helper.getLatitude())*Math.PI/180;
                    Double longitude = (helper.getLongitude())*Math.PI/180;

                    Log.d("Location", "Latitude_database: " + latitude + ", Longitude_database: " + longitude);



                    double distance = 2*6371*Math.asin(Math.sqrt(Math.pow(Math.sin((latitude-0.44490696726064405)/2),2)+Math.cos(latitude)*Math.cos(0.44490696726064405)*Math.pow(Math.sin((longitude-1.4287864166558406)/2),2)));
                    Log.d("LocationUpdate", "distance: " + distance );

                    if(2*6371*Math.asin(Math.sqrt(Math.pow(Math.sin((latitude-0.44490696726064405)/2),2)+Math.cos(latitude)*Math.cos(0.44490696726064405)*Math.pow(Math.sin((longitude-1.4287864166558406)/2),2)))<20)
                    {

                        if (helper.getSos().equals("1")){
                            list.add(helper);
//                            Toast.makeText(patRecyclerView.this,"notification given",Toast.LENGTH_SHORT).show();
                            makenotifications();

                        }


                    }
                    else
                        Toast.makeText(patRecyclerView.this,"no notification given",Toast.LENGTH_SHORT).show();



                }
                myAdapter.notifyDataSetChanged();
            }





            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void makenotifications(){
        Toast.makeText(patRecyclerView.this,"working rv",Toast.LENGTH_SHORT).show();
        String chanelID = "CHANEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),chanelID);
        builder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Notification Title")
                .setContentText("Emergency")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data","Some value to be passed here");

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,
                PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            NotificationChannel notificationChannel =
                    null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = notificationManager.getNotificationChannel(chanelID);
            }
            if(notificationChannel == null){
                int importance = NotificationManager.IMPORTANCE_HIGH;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationChannel = new NotificationChannel(chanelID,"Some description",importance);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationChannel.setLightColor(Color.GREEN);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationChannel.enableVibration(true);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.createNotificationChannel(notificationChannel);
                }
            }
        }
        notificationManager.notify(0,builder.build());

    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permissions if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000); // Update interval in milliseconds

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocationUpdates();
            } else {
                // Permission denied, handle accordingly
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

}