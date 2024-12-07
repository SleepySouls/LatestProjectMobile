package com.example.doan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class CreatePasswdActivity extends AppCompatActivity {
    private Button btnConfirm;
    private TextView tvLogin;
    private boolean isPasswordVisible = false;
    private EditText etPassword, etRePassword;

    class ApiResponse{
        boolean success;
        String message;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_passwd);

        btnConfirm = findViewById(R.id.confirm_button_createpasswd);
        etPassword = findViewById(R.id.enter_passwd);
        etRePassword = findViewById(R.id.enter_repasswd);

        btnConfirm.setOnClickListener(v -> {
            startActivity(new Intent(CreatePasswdActivity.this, LoginActivity.class));
        });

        tvLogin = findViewById(R.id.text_login);
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(CreatePasswdActivity.this, LoginActivity.class));
        });

        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    int drawableEnd = 2; // right drawable index
                    if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[drawableEnd].getBounds().width())) {
                        togglePasswordVisibility();
                        return true;
                    }
                }
                return false;
            }
        });

        etRePassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    int drawableEnd = 2; // right drawable index
                    if (motionEvent.getRawX() >= (etRePassword.getRight() - etRePassword.getCompoundDrawables()[drawableEnd].getBounds().width())) {
                        togglePasswordVisibility();
                        return true;
                    }
                }
                return false;
            }
        });

        btnConfirm.setOnClickListener(v -> {
            String email = getIntent().getStringExtra("email");
            String newPassword = etPassword.getText().toString();
            String reEnterNewPassword = etRePassword.getText().toString();
            if (newPassword.isEmpty() || reEnterNewPassword.isEmpty()){
                Toast.makeText(CreatePasswdActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!newPassword.equals(reEnterNewPassword)) {
                Toast.makeText(CreatePasswdActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                createPasswd(email, newPassword, reEnterNewPassword);
            }
        });

    }

    private void createPasswd(String email, String newPassword, String reEnterNewPassword) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://potholescannerapi.onrender.com").addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);

        CreatePasswdRequest createPasswdRequest = new CreatePasswdRequest(email, newPassword, reEnterNewPassword);
        apiService.createPasswd(createPasswdRequest).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.success && apiResponse.message != null) {
                        Toast.makeText(CreatePasswdActivity.this, apiResponse.message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreatePasswdActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(CreatePasswdActivity.this, apiResponse.message, Toast.LENGTH_SHORT).show();
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
        @POST("api/user/resetpassword")
        Call<ApiResponse> createPasswd(@Body CreatePasswdRequest createPasswdRequest);
    }

    class CreatePasswdRequest{
        String email;
        String newPassword;
        String reEnterNewPassword;

        public CreatePasswdRequest(String email, String newPassword, String reEnterNewPassword){
            this.email = email;
            this.newPassword = newPassword;
            this.reEnterNewPassword = reEnterNewPassword;
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void togglePasswordVisibility() {
        // Save cursor
        int cursorPosition = etPassword.getSelectionStart();

        // Toggle password visibility
        if (isPasswordVisible) {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.visibility_off), null);
            isPasswordVisible = false;
        } else {
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.visibility_on), null);
            isPasswordVisible = true;
        }

        // Restore cursor pos
        etPassword.setSelection(cursorPosition);
    }
}