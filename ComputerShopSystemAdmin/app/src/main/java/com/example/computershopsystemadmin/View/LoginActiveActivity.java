package com.example.computershopsystemadmin.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computershopsystemadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;


public class LoginActiveActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edUsername;
    EditText edPassword;
    TextView tvErrorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_active);

        edUsername = findViewById(R.id.edUserName);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString() + "@fpt.edu.vn";
                String password = edPassword.getText().toString().isEmpty() ? "123456" : edPassword.getText().toString();
                FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActiveActivity.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActiveActivity.this, "Login Successfully!!!", Toast.LENGTH_SHORT).show();
                            LoginActiveActivity.this.finish();
                        } else {
                            tvErrorLogin = findViewById(R.id.tvErrorLogin);
                            tvErrorLogin.setText("Your Username or Password is Wrong!!!!");
                        }
                    }
                });
            }
        });

    }

}