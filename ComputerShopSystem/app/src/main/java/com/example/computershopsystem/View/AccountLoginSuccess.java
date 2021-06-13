package com.example.computershopsystem.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.computershopsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class AccountLoginSuccess extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    TextView account;

    Button btLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login_success);

        firebaseAuth = firebaseAuth.getInstance();
        checkUser();

        btLogOut = findViewById(R.id.btnProfile);

        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, AccountNotLogin.class));
            finish();
        } else {

            String email = firebaseUser.getEmail();
            account = findViewById(R.id.tvAccount2);
            account.setText(email);
        }
    }


}