package com.example.computershopsystem.View;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
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
        activityRegisterBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetOriginBackground();
                Validation validation=new Validation();
                String fullNameNotify=validation.CheckName(activityRegisterBinding.txtName.getText().toString());
                String phoneNotify=validation.CheckPhone(activityRegisterBinding.txtPhone.getText().toString());
                String passwordNotify=validation.CheckPassword(activityRegisterBinding.txtPass.getText().toString());
                String confirmPasswordNotify=validation.CheckConfirmPassword(activityRegisterBinding.txtPass.getText().toString(),activityRegisterBinding.txtConfirmPass.getText().toString());

                if ( fullNameNotify!=null){
                    activityRegisterBinding.txtName.setBackground(getDrawable(R.drawable.border_red));
                    activityRegisterBinding.txtName.setError(fullNameNotify);
                }
                if ( phoneNotify!=null){
                    activityRegisterBinding.txtPhone.setBackground(getDrawable(R.drawable.border_red));
                    activityRegisterBinding.txtPhone.setError(phoneNotify);
                }
                if ( passwordNotify!=null){
                    activityRegisterBinding.txtPass.setBackground(getDrawable(R.drawable.border_red));
                    activityRegisterBinding.txtPass.setError(passwordNotify);
                }
                if ( confirmPasswordNotify!=null){
                    activityRegisterBinding.txtConfirmPass.setBackground(getDrawable(R.drawable.border_red));
                    activityRegisterBinding.txtConfirmPass.setError(confirmPasswordNotify);
                }
            }
        });
        View view = activityRegisterBinding.getRoot();
        setContentView(view);
    }

   void SetOriginBackground(){
       activityRegisterBinding.txtName.setBackground(getDrawable(R.drawable.border));
       activityRegisterBinding.txtPhone.setBackground(getDrawable(R.drawable.border));
       activityRegisterBinding.txtPass.setBackground(getDrawable(R.drawable.border));
       activityRegisterBinding.txtConfirmPass.setBackground(getDrawable(R.drawable.border));
   }
}