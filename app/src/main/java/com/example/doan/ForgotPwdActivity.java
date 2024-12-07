package com.example.doan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class ForgotPwdActivity extends AppCompatActivity {
    private EditText etRecoveryEmail;
    private Button btnConfirmEmail;

    class ApiResponse{
        boolean success;
        String message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.forgot_passwd);
        etRecoveryEmail = findViewById(R.id.recovery_email);
        btnConfirmEmail = findViewById(R.id.btn_for_confirm_email);
        btnConfirmEmail.setOnClickListener(v -> {
            String recoveryEmail = etRecoveryEmail.getText().toString();
            if (recoveryEmail.isEmpty()) {
                Toast.makeText(ForgotPwdActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
            else{
                sendRecoveryEmail(recoveryEmail);
            }
        });
    }

    private void sendRecoveryEmail(String recoveryEmail) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://potholescannerapi.onrender.com").addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);

        ForgotPwdRequest forgotPwdRequest = new ForgotPwdRequest(recoveryEmail);
        apiService.sendRecoveryEmail(forgotPwdRequest).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.success){
                        Toast.makeText(ForgotPwdActivity.this, "Email sent successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgotPwdActivity.this, CheckMailActivity.class));
                    }
                }
                else{
                    Log.e("Error", "Request failed: " + response.code() + " " + response.message());
                    try{
                        String errorBody = response.errorBody().string();
                        Log.e("Error", "Error body: " + errorBody);
                    }
                    catch (Exception e){
                        Log.e("Error", "Error parsing error body", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("Error", "Network error", t);
            }
        });

    }

    interface ApiService{
        @POST("api/email/resetpassword")
        Call<ApiResponse> sendRecoveryEmail(@Body ForgotPwdRequest forgotPwdRequest);
    }
    class ForgotPwdRequest{
        String email;
        public ForgotPwdRequest(String email){
            this.email = email;
        }
    }
}