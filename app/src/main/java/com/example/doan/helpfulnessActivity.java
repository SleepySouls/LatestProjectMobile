package com.example.doan;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class helpfulnessActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private ArrayList<helpfulnessList> PeopleLikeType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpfulness);
        this.recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        this.PeopleLikeType = new ArrayList<helpfulnessList>();
        this.PeopleLikeType.add(new helpfulnessList(R.drawable.avatar, "User Name 1",R.drawable.bluelocation,"Street Name 1"));
        this.PeopleLikeType.add(new helpfulnessList(R.drawable.avatar, "User Name 2",R.drawable.bluelocation,"Street Name 2"));
        this.PeopleLikeType.add(new helpfulnessList(R.drawable.avatar, "User Name 3",R.drawable.bluelocation,"Street Name 3"));
        this.PeopleLikeType.add(new helpfulnessList(R.drawable.avatar, "User Name 4",R.drawable.bluelocation,"Street Name 4"));
        this.PeopleLikeType.add(new helpfulnessList(R.drawable.avatar, "User Name 5",R.drawable.bluelocation,"Street Name 5"));
        this.PeopleLikeType.add(new helpfulnessList(R.drawable.avatar, "User Name 6",R.drawable.bluelocation,"Street Name 6"));
        this.PeopleLikeType.add(new helpfulnessList(R.drawable.avatar, "User Name 7",R.drawable.bluelocation,"Street Name 7"));
        this.PeopleLikeType.add(new helpfulnessList(R.drawable.avatar, "User Name 8",R.drawable.bluelocation,"Street Name 8"));
        this.recyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        this.recyclerview.setLayoutManager(layoutManager);
        this.recyclerview.setAdapter(new helpfulnessAdapter(helpfulnessActivity.this, this.PeopleLikeType));
    }
}