package com.example.medwheels_hos1;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.MyViewHolder>{

    Context context;
    ArrayList<HelperClass_driver> driverList;

    public DriverAdapter(Context context, ArrayList<HelperClass_driver> driverList) {
        this.context = context;
        this.driverList = driverList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.driver_card,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        HelperClass_driver helperClassDriver = driverList.get(position);
        holder.Name.setText(helperClassDriver.getName());

        holder.assign.setOnClickListener(v ->
        {

            String patMail = "hardcoded@gmail.com", patName = "hardcoded", driverName = helperClassDriver.getName(), driverMail = helperClassDriver.getEmail();
            double latitude = 1, longitude = 2;
            Log.d("Tag", "name" + driverName + driverMail);
            FirebaseDatabase database1 = FirebaseDatabase.getInstance("https://medwheels-4b07d-default-rtdb.asia-southeast1.firebasedatabase.app");
            DatabaseReference reference1 = database1.getReference("assign");
            HelperClass_assign helperClass = new HelperClass_assign(patMail, driverMail, patName, driverName, latitude, longitude);

            // Add null check for patMail and driverMail
            String patMailKey = patMail == null ? "" : patMail.replace(".", ",");
            String driverMailKey = driverMail == null ? "" : driverMail.replace(".", ",");
            String key = patMailKey + "and" + driverMailKey;
            reference1.child(key).setValue(helperClass);
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("name", patName);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude",longitude);
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Name;
        Button assign;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.driverName);
            assign = itemView.findViewById(R.id.assign);
        }
    }
}