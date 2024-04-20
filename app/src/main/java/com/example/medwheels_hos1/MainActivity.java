package com.example.medwheels_hos1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.maps.DistanceMatrixApiRequest;
//import com.google.maps.android.DistanceMatrixApi;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Circle locationCircle;
    Button assign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        assign=findViewById(R.id.assign_ambu);
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Ambulance_list.class);
                startActivity(intent);
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableMyLocation();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        enableMyLocation();
        getDeviceLocation();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            getDeviceLocation();
        }
    }

//    private void getDeviceLocation() {
//        double latitude = 25.431474; //IIIT ALLAHABAD
//        double longitude = 81.770500;
//
//        LatLng desiredLocation = new LatLng(latitude, longitude);
//        googleMap.addMarker(new MarkerOptions().position(desiredLocation).title("Accident Location"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(desiredLocation, 15f));
//
//        // Draw a circle around the desired location
//        if (desiredLocation != null) {
//            if (locationCircle != null) {
//                locationCircle.remove();
//            }
//            CircleOptions circleOptions = new CircleOptions()
//                    .center(desiredLocation)
//                    .radius(1000) // Set the radius in meters
//                    .fillColor(0xE2856E) // Set the fill color
//                    .strokeColor(0x1e1e1e) // Set the stroke color
//                    .strokeWidth(5); // Set the stroke width
//            locationCircle = googleMap.addCircle(circleOptions);}
//    }

    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            double latitude = 25.431474; //IIIT ALLAHABAD
                            double longitude = 81.770500;
                            LatLng currentLocation = new LatLng(latitude, longitude);
                            googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Accident Location"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));

                            // Draw a circle around the current location
                            if (locationCircle != null) {
                                locationCircle.remove();
                            }



                            CircleOptions circleOptions = new CircleOptions()
                                    .center(currentLocation)
                                    .radius(10000) // Set the radius in meters
                                    .fillColor(0x20FF0000) // Set the fill color with transparency
                                    .strokeColor(Color.RED) // Set the stroke color
                                    .strokeWidth(5); // Set the stroke width
                            locationCircle = googleMap.addCircle(circleOptions);

                        }
                    });
        }
    }

//    private void getDeviceLocation() {
//        // Set the desired latitude and longitude
//        double latitude = 25.431474; // San Francisco, CA
//        double longitude = 81.770500;
//
//        LatLng desiredLocation = new LatLng(latitude, longitude);
//        googleMap.addMarker(new MarkerOptions().position(desiredLocation).title("Accident Location"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(desiredLocation, 15f));
//
//        // Draw a circle around the desired location
//        if (desiredLocation != null) {
//            if (locationCircle != null) {
//                locationCircle.remove();
//            }
//            CircleOptions circleOptions = new CircleOptions()
//                    .center(desiredLocation)
//                    .radius(500) // Set the radius in meters
//                    .fillColor(0xE2856E) // Set the fill color
//                    .strokeColor(0x1e1e1e) // Set the stroke color
//                    .strokeWidth(5); // Set the stroke width
//            locationCircle = googleMap.addCircle(circleOptions);
//
//            // Calculate the distance between your current location and the desired location
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            fusedLocationClient.getLastLocation()
//                    .addOnSuccessListener(this, location -> {
//                        if (location != null) {
//                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
////                            calculateDistance(currentLocation, desiredLocation);
//                        }
//                    });
//        }
//    }
//    private void calculateDistance(LatLng origin, LatLng destination) {
//        String[] origins = {origin.latitude + "," + origin.longitude};
//        String[] destinations = {destination.latitude + "," + destination.longitude};
//
//        DistanceMatrixApiRequest request = DistanceMatrixApi.newRequest(getGeoContext())
//                .origins(origins)
//                .destinations(destinations)
//                .mode(TravelMode.DRIVING)
//                .language("en")
//                .await();
//
//        try {
//            DistanceMatrix matrix = request.await();
//            if (matrix.rows.length > 0 && matrix.rows[0].elements.length > 0) {
//                DistanceMatrixApiResponse.DistanceMatrixRow row = matrix.rows[0];
//                DistanceMatrixApiResponse.DistanceMatrixElement element = row.elements[0];
//                String distance = element.distance.humanReadable;
//                Log.d("Distance", "Distance between locations: " + distance);
//                // You can display the distance on the map or in a UI element
//            }
//        } catch (ApiException e) {
//            Log.e("Distance", "Failed to calculate distance: " + e.getMessage());
//        } catch (InterruptedException e) {
//            Log.e("Distance", "Thread interrupted: " + e.getMessage());
//        } catch (IOException e) {
//            Log.e("Distance", "I/O error: " + e.getMessage());
//        }
//    }

}

