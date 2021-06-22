package com.example.computershopsystem.View;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.computershopsystem.R;
import com.example.computershopsystem.Utilities.Validation;
import com.example.computershopsystem.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding activityRegisterBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        activityRegisterBinding.txtName.setBackground(getDrawable(R.drawable.border_red));
        activityRegisterBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation validation=new Validation();
                v
                if ( )
            }
        });
        View view = activityRegisterBinding.getRoot();
        setContentView(view);
    }
}