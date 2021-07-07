package com.example.computershopsystem.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computershopsystem.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class RegisterOTP extends AppCompatActivity {
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    DatabaseReference mDatabase;
    String numberPhone;
    Button btnVerify;
    Button btnResend;
    EditText etOTP;

    private static final String TAG = "LOGIN_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_otp);
        if (getIntent().getStringExtra("changePhone") == null) {
            numberPhone = getIntent().getStringExtra("phone");
        } else {
            numberPhone = getIntent().getStringExtra("changePhone");
        }

        numberPhone = numberPhone.substring(1);

        firebaseAuth = FirebaseAuth.getInstance();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @org.jetbrains.annotations.NotNull PhoneAuthCredential phoneAuthCredential) {
                if (getIntent().getStringExtra("changePhone") == null) {
                    signInWithPhoneAuthCredentail(phoneAuthCredential);
                } else {
                    ChangePhone(phoneAuthCredential);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull @org.jetbrains.annotations.NotNull FirebaseException e) {
                Toast.makeText(RegisterOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull @org.jetbrains.annotations.NotNull String verificationId, @NonNull @org.jetbrains.annotations.NotNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, token);

                Log.d(TAG, "onCodeSent" + verificationId);
                mVerificationId = verificationId;
                forceResendingToken = token;
                Toast.makeText(RegisterOTP.this, "Verification code sent" + numberPhone, Toast.LENGTH_SHORT).show();

            }
        };

        startPhoneNumberVerification(numberPhone);
        btnResend = findViewById(R.id.btnResend);
        btnVerify = findViewById(R.id.btnVerify);
        etOTP = findViewById(R.id.etOTP);
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode(numberPhone, forceResendingToken);
            }
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = etOTP.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(RegisterOTP.this, "Please enter code", Toast.LENGTH_SHORT).show();
                } else {
                    verifyPhoneNumberWithCode(mVerificationId, code);
                }
            }
        });
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        if (getIntent().getStringExtra("changePhone") == null) {
            signInWithPhoneAuthCredentail(credential);
        } else {
            ChangePhone(credential);
        }
    }

    private void resendVerificationCode(String phone, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber("+84" + phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .setForceResendingToken(token)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void startPhoneNumberVerification(String phone) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber("+84" + phone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void ChangePhone(PhoneAuthCredential credential) {
        firebaseAuth.getCurrentUser().updatePhoneNumber(credential);
        Intent intent = new Intent(RegisterOTP.this, MainActivity.class);
        startActivity(intent);
    }

    private void signInWithPhoneAuthCredentail(PhoneAuthCredential credential) {

        firebaseAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                if (firebaseAuth.getCurrentUser().getDisplayName() == null) {
                    Intent intent = new Intent(RegisterOTP.this, InputFullNameRegisterActivity.class);
                    intent.putExtra("phone", "0" + numberPhone);
                    startActivity(intent);
                    RegisterOTP.this.finish();
                } else {
                    String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
                    //Add customer
//                CustomerAccount customerAccount=new CustomerAccount(firebaseAuth.getCurrentUser().getUid(), "0"+numberPhone, null,null,null,null);
//                Customer customer=new Customer(firebaseAuth.getCurrentUser().getUid(), customerAccount, String fullName, Date createAt);

//                mDatabase = FirebaseDatabase.getInstance().getReference("Customer");
//                mDatabase.child(firebaseAuth.getCurrentUser().getUid()).setValue(customer);
                    Intent intent = new Intent(RegisterOTP.this, MainActivity.class);
                    startActivity(intent);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(RegisterOTP.this, e.getMessage() + numberPhone, Toast.LENGTH_SHORT).show();
            }
        });
    }
}