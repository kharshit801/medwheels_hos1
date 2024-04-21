package com.example.medwheels_hos1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.MyViewHolder>{

    Context context;

    ArrayList<HelperClass> list;

    public Myadapter(Context context, ArrayList<HelperClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_recycler,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        HelperClass helperclass = list.get(position);
        holder.Name.setText(helperclass.getName());

        holder.detailButton.setOnClickListener(v -> {
            Intent intent = new Intent(context,patientInfo.class);
            intent.putExtra("name",helperclass.getName());
            intent.putExtra("dob",helperclass.getDob());
            intent.putExtra("bloodg",helperclass.getBlood_d());
            intent.putExtra("allergy",helperclass.getAll());
            intent.putExtra("medh",helperclass.getMed());
            context.startActivity(intent);
        });


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Name;
        Button detailButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.patName);
            detailButton = itemView.findViewById(R.id.detailBtn);


        }
    }

}
