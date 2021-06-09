package com.example.computershopsystem.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.computershopsystem.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginVerifyOTP extends AppCompatActivity {
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private FirebaseAuth firebaseAuth;


    Button btnVerify;
    EditText etOTP;
    private static final String TAG = "LOGIN_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_verify_otp);
        firebaseAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @org.jetbrains.annotations.NotNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull @org.jetbrains.annotations.NotNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull @org.jetbrains.annotations.NotNull String s, @NonNull @org.jetbrains.annotations.NotNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(s, token);
                mVerificationId=s;
                forceResendingToken=token;
            }

        };
        btnVerify = findViewById(R.id.btnVerify);
        etOTP = findViewById(R.id.etPhone);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone ="";
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(LoginVerifyOTP.this, "Please enter phone number...", Toast.LENGTH_SHORT).show();
                } else {
                    startPhoneNumberVerification("+84"+phone);
                }
            }
        });

    }


    private void startPhoneNumberVerification(String phone) {
        PhoneAuthOptions options=
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
}