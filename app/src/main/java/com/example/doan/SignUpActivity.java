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

import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class SignUpActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etPhonenum, etEmail;
    private Button btnSignUp;
    private TextView tvSignIn;
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
        setContentView(R.layout.signup_main);
        // Find views by ID
        etUsername = findViewById(R.id.enter_username);
        etPassword = findViewById(R.id.enter_passwd);
        etPhonenum = findViewById(R.id.enter_phonenum);
        etEmail = findViewById(R.id.enter_email);
        btnSignUp = findViewById(R.id.sign_up_btn);
        tvSignIn = findViewById(R.id.text_login);

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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String phonenum = etPhonenum.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty() || phonenum.isEmpty() || email.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    signUpUser(username, password, phonenum, email);
                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    private void signUpUser(String username, String password, String phonenum, String email){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://potholescannerapi.onrender.com").addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);

        SignUpRequest signUpRequest = new SignUpRequest(username, email, phonenum, password);
        apiService.signUpUser(signUpRequest).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()){
                    ApiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.success){
                        Toast.makeText(SignUpActivity.this, "Sigging Up...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    }
                    if (response.headers().get("Authorization") != null){
                        String token = response.headers().get("Authorization");
                        Log.d("Token", "Authorization header: " + token);
                    }
                    else {
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
            }
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("Error", "Network error", t);
            }
        });
    }

    interface ApiService{
        @POST("api/user/register")
        Call<ApiResponse> signUpUser(@Body SignUpRequest signUpRequest);
    }

    class SignUpRequest{
        String username;
        String email;
        String phonenumber;
        String password;

        public SignUpRequest(String username, String email, String phonenum, String password){
            this.username = username;
            this.email = email;
            this.phonenumber = phonenum;
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