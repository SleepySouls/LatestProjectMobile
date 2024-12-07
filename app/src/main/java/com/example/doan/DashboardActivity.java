package com.example.doan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {
    private RelativeLayout icSetting, icMap, icHome, icYourDetection, icPotholesNearYou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        icSetting = findViewById(R.id.iconsetting);
        icMap = findViewById(R.id.iconlocation);
        icHome = findViewById(R.id.iconhome);
        icPotholesNearYou = findViewById(R.id.rectangle_2);
        icYourDetection = findViewById(R.id.rectangle_3);

        icSetting.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, SettingActivity.class));
        });
        icMap.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, MapActivity.class));
        });
        icHome.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, DashboardActivity.class));
        });
        icYourDetection.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, YourDetectionActivity.class));
        });
        icPotholesNearYou.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, PotholesNearYouListActivity.class));
        });

    }
}