package com.example.computershopsystem.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.computershopsystem.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountLoginSuccess extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private GoogleSignInClient googleSignInClient;

    TextView account;

    Button btLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login_success);

        firebaseAuth = firebaseAuth.getInstance();
        checkUser();

        btLogOut = findViewById(R.id.btnLogout);

        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                signOut();
                checkUser();
            }
        });
    }

    private void signOut() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, options);
        googleSignInClient.signOut();
    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {

            String email = firebaseUser.getEmail();
            account = findViewById(R.id.tvAccount2);
            account.setText(email);
        }
    }


}