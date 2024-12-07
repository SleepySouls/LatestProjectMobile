package com.example.doan;

import android.annotation.SuppressLint;
import android.util.Log;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doan.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvSignUp;
    private boolean isPasswordVisible = false;

    class ApiResponse{
        boolean success;
        String message;
        String token;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        // Find views by ID
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.loginButton);
        tvForgotPassword = findViewById(R.id.forgotPassword);
        tvSignUp = findViewById(R.id.tvSignup);

        // Password visibility toggle
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

        // Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(email, password);
                }
            }
        });

        // Forgot password
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPwdActivity.class));
            }
        });

        // Sign-up
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void loginUser(String email, String password){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://potholescannerapi.onrender.com").addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);

        LoginRequest loginRequest = new LoginRequest(email, password);

        apiService.loginUser(loginRequest).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null){
                        TokenManager tokenManager = new TokenManager(LoginActivity.this);
                        tokenManager.saveToken(apiResponse.token);
                        Toast.makeText(LoginActivity.this, apiResponse.message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }

                    if (response.headers().get("Authorization") != null){
                        String token = response.headers().get("Authorization");
                        Log.d("Token", "Authorization header: " + token);
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
        @POST("api/user/login")
        Call<ApiResponse> loginUser(@Body LoginRequest loginRequest);
    }

    class LoginRequest{
        String email;
        String password;

        public LoginRequest(String email, String password){
            this.email = email;
            this.password = password;
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
