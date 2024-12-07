package com.example.doan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class potholeNearAdapter extends RecyclerView.Adapter<potholeNearAdapter.ViewHolder> {
    private Context context;
    private ArrayList<potholeNearList> potholeNearList = new ArrayList<>();
    public potholeNearAdapter(Context context, ArrayList<potholeNearList> potholeNearList) {
        this.context = context;
        this.potholeNearList = potholeNearList;
    }
    @NonNull
    @Override
    public potholeNearAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.streetlistnearu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull potholeNearAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.ImageView1.setImageResource(this.potholeNearList.get(position).getImageStreet());
        holder.textView1.setText(this.potholeNearList.get(position).getStreetName());
        holder.ImageView2.setImageResource(this.potholeNearList.get(position).getImageDistance());
        holder.textView2.setText(this.potholeNearList.get(position).getDistanceIn());
        holder.textView3.setText(this.potholeNearList.get(position).getDistance());
    }

    @Override
    public int getItemCount() {
        return this.potholeNearList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ImageView1;
        private TextView textView1;
        private ImageView ImageView2;
        private TextView textView2;
        private TextView textView3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ImageView1 = (ImageView) itemView.findViewById(R.id.location_img);
            this.textView1 = (TextView) itemView.findViewById(R.id.tv_street);
            this.ImageView2 = (ImageView) itemView.findViewById(R.id.car_img);
            this.textView2 = (TextView) itemView.findViewById(R.id.distanceIn);
            this.textView3 = (TextView) itemView.findViewById(R.id.distance);
        }
    }
}
