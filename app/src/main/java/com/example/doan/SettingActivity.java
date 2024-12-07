package com.example.doan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doan.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class SettingActivity extends AppCompatActivity {
    private RelativeLayout icSetting, icMap, icHome, icLogout;

    class apiResponse{
        boolean success;
        String message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.setting);

        icSetting = findViewById(R.id.iconsetting);
        icMap = findViewById(R.id.iconlocation);
        icHome = findViewById(R.id.iconhome);
        icLogout = findViewById(R.id.rectangle_for_logout);

        icSetting.setOnClickListener(v -> {
            startActivity(new Intent(SettingActivity.this, SettingActivity.class));
        });
        icMap.setOnClickListener(v -> {
            startActivity(new Intent(SettingActivity.this, MapActivity.class));
        });
        icHome.setOnClickListener(v -> {
            startActivity(new Intent(SettingActivity.this, DashboardActivity.class));
        });
        icLogout.setOnClickListener(v -> {
            TokenManager tokenManager = new TokenManager(SettingActivity.this);
            String token = tokenManager.getToken();
            Logout(token);
        });
    }

    private void Logout(String token){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://potholescannerapi.onrender.com").addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.Logout(token).enqueue(new Callback<apiResponse>() {
            @Override
            public void onResponse(Call<apiResponse> call, Response<apiResponse> response) {
                if (response.isSuccessful()){
                    apiResponse apiResponse = response.body();
                    if (apiResponse != null){
                        TokenManager tokenManager = new TokenManager(SettingActivity.this);
                        tokenManager.clearToken();
                        Toast.makeText(SettingActivity.this, apiResponse.message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }
            @Override
            public void onFailure(Call<apiResponse> call, Throwable t) {
                Log.e("Error", "Network error", t);
            }
        });
    }

    interface ApiService{
        @POST("api/user/logout")
        Call<apiResponse> Logout(@Header("Authorization") String token);
    }

}