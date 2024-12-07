package com.example.doan;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

public class CheckMailActivity extends AppCompatActivity {
    private EditText etOtp1, etOtp2, etOtp3, etOtp4, etOtp5;
    private Button btnConfirm;
    private EditText[] otpFields;

    class apiResponse{
        boolean success;
        String message;
        String email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_mail);

        etOtp1 = findViewById(R.id.rectangle_otp_1);
        etOtp2 = findViewById(R.id.rectangle_otp_2);
        etOtp3 = findViewById(R.id.rectangle_otp_3);
        etOtp4 = findViewById(R.id.rectangle_otp_4);
        etOtp5 = findViewById(R.id.rectangle_otp_5);
        btnConfirm = findViewById(R.id.btn_confirm_checkmail);
        btnConfirm.setEnabled(false);

        otpFields = new EditText[]{etOtp1, etOtp2, etOtp3, etOtp4, etOtp5};

        for (int i = 0; i < otpFields.length; i++) {
            final int currentIndex = i;
            otpFields[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1) {
                        // Move next
                        if (currentIndex < otpFields.length - 1) {
                            otpFields[currentIndex + 1].requestFocus();
                        }
                    }
                    checkOtpFields();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            otpFields[i].setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (otpFields[currentIndex].getText().toString().isEmpty() && currentIndex > 0) {
                        otpFields[currentIndex - 1].requestFocus();
                    }
                }
                return false;
            });
        }

        btnConfirm.setOnClickListener(v -> {
            String otp = etOtp1.getText().toString() + etOtp2.getText().toString() + etOtp3.getText().toString() + etOtp4.getText().toString() + etOtp5.getText().toString();
            if (otp.isEmpty()){
                Toast.makeText(CheckMailActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
            else{
                confirmCode(otp);
            }
        });
    }

    private void checkOtpFields() {
        boolean isOtpComplete = true;

        for (EditText otpField : otpFields) {
            if (otpField.getText().toString().trim().isEmpty()) {
                isOtpComplete = false;
                break;
            }
        }

        // Enable/disable the button
        btnConfirm.setEnabled(isOtpComplete);

        // Change button color
        btnConfirm.setBackgroundTintList(
                ContextCompat.getColorStateList(this,
                        isOtpComplete ? R.color.custom_orange : R.color.custom_gray
                )
        );

    }

    private void confirmCode(String resetCode) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://potholescannerapi.onrender.com").addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);

        confirmCodeRequest confirmCodeRequest = new confirmCodeRequest(resetCode);
        apiService.confirmCode(confirmCodeRequest).enqueue(new Callback<apiResponse>() {
            @Override
            public void onResponse(Call<apiResponse> call, Response<apiResponse> response) {
                if (response.isSuccessful()){
                    apiResponse apiResponse = response.body();
                    if (apiResponse != null && apiResponse.success){
                        Toast.makeText(CheckMailActivity.this, "Email confirmed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CheckMailActivity.this, CreatePasswdActivity.class);
                        intent.putExtra("email", apiResponse.email);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(CheckMailActivity.this, apiResponse.message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<apiResponse> call, Throwable t) {
                Toast.makeText(CheckMailActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    interface ApiService{
        @POST("api/user/confirmcode")
        Call<apiResponse> confirmCode(@Body confirmCodeRequest confirmCodeRequest);

    }

    class confirmCodeRequest{
        String resetCode;
        public confirmCodeRequest(String resetCode){
            this.resetCode = resetCode;
        }
    }


}