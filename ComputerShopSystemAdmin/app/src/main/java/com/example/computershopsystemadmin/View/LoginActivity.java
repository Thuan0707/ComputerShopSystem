package com.example.computershopsystemadmin.View;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    Button btnLo;
    EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLo = findViewById(R.id.btnLogin);
        etPhone = findViewById(R.id.txtPhone);

        btnLo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, RegisterOTP.class);
                intent.putExtra("PhoneNo",etPhone.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}