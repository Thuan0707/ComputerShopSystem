package com.example.computershopsystem.View;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    Button btnLo;
    EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLo = findViewById(R.id.btnLo);
        etPhone = findViewById(R.id.etPhone);

        btnLo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, LoginVerifyOTP.class);
                intent.putExtra("PhoneNo",etPhone.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}