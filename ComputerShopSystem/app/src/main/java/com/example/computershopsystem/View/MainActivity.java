package com.example.computershopsystem.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.computershopsystem.R;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnActive, btnWrong, btnRegister, btnHome, btnHomeLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActiveActivity.class);
                startActivity(intent);
            }
        });

        btnWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginWrongActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        btnHomeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeLoginActivity.class);
                startActivity(intent);
            }
        });




    }

    private void map() {
        btnLogin = findViewById(R.id.btnLogin);
        btnActive = findViewById(R.id.btnActive);
        btnWrong = findViewById(R.id.btnWrong);
        btnRegister = findViewById(R.id.btnREgis);
        btnHome = findViewById(R.id.btnHome);
        btnHomeLogin = findViewById(R.id.btnHomeLogin);
    }
}