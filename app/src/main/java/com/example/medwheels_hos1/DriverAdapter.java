package com.example.medwheels_hos1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        HelperClass_driver helperClassDriver = driverList.get(position);
        holder.Name.setText(helperClassDriver.getName());


        holder.itemView.setOnClickListener(v -> {
            //navigate to chat activity;
            Intent intent = new Intent(context, MainActivity.class);
//            intent.putExtra("username","Dr."+helperclass.getName());
//            intent.putExtra("specialist",helperclass.getSpeacilist());
//            intent.putExtra("doc_mail",helperclass.getEmail());
//            intent.putExtra("Image",list.get(position).getImage());
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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.driverName);


        }
    }

}
