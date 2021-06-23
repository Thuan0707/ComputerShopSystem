package com.example.computershopsystem.View;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.computershopsystem.Model.Customer;
import com.example.computershopsystem.Model.CustomerAccount;
import com.example.computershopsystem.R;
import com.example.computershopsystem.Utilities.Validation;
import com.example.computershopsystem.databinding.ActivityRegisterBinding;

import java.util.Date;

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
                String name=activityRegisterBinding.txtName.getText().toString().trim();
                String phone=activityRegisterBinding.txtPhone.getText().toString().trim();
                String password=activityRegisterBinding.txtPass.getText().toString();
                String  confirmPassword=activityRegisterBinding.txtConfirmPass.getText().toString();
                String fullNameNotify=validation.CheckName(name);
                String phoneNotify=validation.CheckPhone(phone);
                String passwordNotify=validation.CheckPassword(password);
                String confirmPasswordNotify=validation.CheckConfirmPassword(password,confirmPassword);

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
                if (validation.isValid()){
                    phone=phone.substring(1);
                    Log.e("asfafs",phone);
                    CustomerAccount customerAccount=new CustomerAccount(null, phone, null, null,password);
                    Customer customer=new Customer(null, customerAccount, null, name, new Date());
                    Intent intent=new Intent(RegisterActivity.this, RegisterOTP.class);
                    intent.putExtra("Customer",customer);
                    startActivity(intent);
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