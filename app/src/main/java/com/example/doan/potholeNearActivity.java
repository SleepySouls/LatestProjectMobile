package com.example.doan;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class potholeNearActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private ArrayList<potholeNearList> potholeOnStreetType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.potholes_near_you_list);

        this.recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        this.potholeOnStreetType = new ArrayList<potholeNearList>();
        this.potholeOnStreetType.add(new potholeNearList(R.drawable.locationicon, "Street Name",R.drawable.car,"Distance in","m"));
        this.potholeOnStreetType.add(new potholeNearList(R.drawable.locationicon, "Street Name",R.drawable.car,"Distance in","m"));
        this.potholeOnStreetType.add(new potholeNearList(R.drawable.locationicon, "Street Name",R.drawable.car,"Distance in","m"));
        this.potholeOnStreetType.add(new potholeNearList(R.drawable.locationicon, "Street Name",R.drawable.car,"Distance in","m"));
        this.potholeOnStreetType.add(new potholeNearList(R.drawable.locationicon, "Street Name",R.drawable.car,"Distance in","m"));
        this.potholeOnStreetType.add(new potholeNearList(R.drawable.locationicon, "Street Name",R.drawable.car,"Distance in","m"));
        this.recyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        this.recyclerview.setLayoutManager(layoutManager);
        this.recyclerview.setAdapter(new potholeNearAdapter(potholeNearActivity.this, this.potholeOnStreetType));
    }
}