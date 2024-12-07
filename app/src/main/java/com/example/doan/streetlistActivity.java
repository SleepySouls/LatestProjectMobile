package com.example.doan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class streetlistActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private ArrayList<streetlist> foodType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_detection);

        this.recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        this.foodType = new ArrayList<streetlist>();
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 1"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 2"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 3"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 4"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 5"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 6"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 7"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 8"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 9"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 10"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 11"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 12"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 13"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 14"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 15"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 16"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 17"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 18"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 19"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 20"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 21"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 22"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 23"));
        this.foodType.add(new streetlist(R.drawable.flag_street, "Street Name 24"));
        this.recyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        this.recyclerview.setLayoutManager(layoutManager);
        this.recyclerview.setAdapter(new streetlistAdapter(streetlistActivity.this, this.foodType));
    }
}