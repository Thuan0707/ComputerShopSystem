package com.example.computershopsystem.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computershopsystem.Model.Customer;
import com.example.computershopsystem.Model.CustomerAccount;
import com.example.computershopsystem.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RegisterOTP extends AppCompatActivity {
    DatabaseReference databaseReference;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private GoogleSignInClient googleSignInClient;
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
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(RegisterOTP.this);
        firebaseAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer");
                        CustomerAccount customerAccount = new CustomerAccount(user.getUid(), numberPhone, null, user.getEmail(), user.getUid(), null);
                        Customer customer = new Customer(user.getUid(), customerAccount, user.getDisplayName(), new Date());
                        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(customer);
                        Intent intent = new Intent(RegisterOTP.this, MainActivity.class);
                        startActivity(intent);
                    } else {

                        if (firebaseAuth.getCurrentUser().getPhoneNumber() != null || acct == null) {
                            firebaseAuth.getCurrentUser().updatePhoneNumber(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Customer/"+user.getUid()+"/customerAccount/phone");
                                        mDatabase.setValue(numberPhone).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(RegisterOTP.this, "Change Phone Number Successfully" , Toast.LENGTH_SHORT).show();

                                                }else{
                                                    Toast.makeText(RegisterOTP.this, "Change Phone Number Fail" , Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                                    }else{
                                        Toast.makeText(RegisterOTP.this, "This phone has existed in another account", Toast.LENGTH_SHORT).show();

                                    }
                                    Intent intent = new Intent(RegisterOTP.this, MainActivity.class);

                                    startActivity(intent);
                                }
                            });
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterOTP.this);
                            builder.setTitle("AlertDialog");
                            builder.setMessage("This number phone is has already existed account, If you save this phone for this account, the phone account (including data) will be deleted ?");
                            // add the buttons
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    LinkPhone(credential, acct);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(RegisterOTP.this, InputPhoneRegisterActivity.class);
//                            firebaseAuth.signOut();
//                            signOut();
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            // create and show the alert dialog
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    }

                }
            }
        });

//        if (firebaseAuth.getCurrentUser().getPhoneNumber() == null) {
//            firebaseAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//                        Intent intent = new Intent(RegisterOTP.this, MainActivity.class);
//
//                        startActivity(intent);
//                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterOTP.this);
//                        builder.setTitle("AlertDialog");
//                        builder.setMessage("This number phone is has already existed account, If you save this phone for this account, the phone account (including data) will be deleted ?");
//                        // add the buttons
//                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                LinkPhone(credential);
//                            }
//                        });
//                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(RegisterOTP.this, MainActivity.class);
//                                startActivity(intent);
//                            }
//                        });
//
//                        // create and show the alert dialog
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
//
//
//                    }
//                }
//            });
//        } else {
//            firebaseAuth.getCurrentUser().updatePhoneNumber(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull @NotNull Task<Void> task) {
//                    if (task.isSuccessful()) {
//                        Intent intent = new Intent(RegisterOTP.this, MainActivity.class);
//                        startActivity(intent);
//                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterOTP.this);
//                        builder.setTitle("AlertDialog");
//                        builder.setMessage("This number phone is has already existed account, If you save this phone for this account, the phone account (including data) will be deleted ?");
//                        // add the buttons
//                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                UpdatePhone(credential);
//                            }
//                        });
//                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(RegisterOTP.this, MainActivity.class);
//
//                                startActivity(intent);
//                            }
//                        });
//
//                        // create and show the alert dialog
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
//                    }
//                }
//            });
//        }


        // setup the alert builder


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

    void UpdatePhone(PhoneAuthCredential credential) {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(RegisterOTP.this);

        if (acct == null) {
            firebaseAuth.getCurrentUser().updatePhoneNumber(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (!task.isSuccessful()) {
                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                firebaseAuth.getCurrentUser().updatePhoneNumber(credential);
                                Intent intent = new Intent(RegisterOTP.this, MainActivity.class);

                                startActivity(intent);
                            }
                        });
                        Log.e("asdf", task.getException().toString());
                    }
                }
            });
            Intent intent = new Intent(RegisterOTP.this, MainActivity.class);

            startActivity(intent);
        } else {

            AuthCredential credentialGmail = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        firebaseAuth.signInWithCredential(credentialGmail).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                                firebaseAuth.getCurrentUser().updatePhoneNumber(credential);
                                                Intent intent = new Intent(RegisterOTP.this, MainActivity.class);

                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }
                            });
                }
            });
        }
    }

    void LinkPhone(PhoneAuthCredential credential, GoogleSignInAccount acct) {
        AuthCredential credentialGmail = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.unlink(PhoneAuthProvider.PROVIDER_ID);
                if (user.isAnonymous()) {
                    user.delete();
                }
                firebaseAuth.signInWithCredential(credentialGmail).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        firebaseAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer");
                                        CustomerAccount customerAccount = new CustomerAccount(user.getUid(), numberPhone, null, user.getEmail(), user.getUid(), null);
                                        Customer customer = new Customer(user.getUid(), customerAccount, user.getDisplayName(), new Date());
                                        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(customer);
                                }
                            }
                        });
                        Toast.makeText(RegisterOTP.this, "Create new account", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RegisterOTP.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

    }

    private void signOut() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(RegisterOTP.this, options);
        googleSignInClient.signOut();
    }

}