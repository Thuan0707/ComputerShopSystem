package com.example.computershopsystemadmin.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Validation;
import com.example.computershopsystemadmin.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding activityRegisterBinding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        activityRegisterBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetOriginBackground();
                Validation validation=new Validation();
                String name=activityRegisterBinding.txtName.getText().toString().trim();
                String email=activityRegisterBinding.txtEmail.getText().toString().trim();
                String password=activityRegisterBinding.txtPass.getText().toString();
                String  confirmPassword=activityRegisterBinding.txtConfirmPass.getText().toString();
                String fullNameNotify=validation.CheckName(name);

                String passwordNotify=validation.CheckPassword(password);
                String confirmPasswordNotify=validation.CheckConfirmPassword(password,confirmPassword);

                if ( fullNameNotify!=null){
                    activityRegisterBinding.txtName.setBackground(getDrawable(R.drawable.border_red));
                    activityRegisterBinding.txtName.setError(fullNameNotify);
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

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent intent=new Intent(RegisterActivity.this, LoginActiveActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }  else
                            {

                            }
                        }

                    });
//                    CustomerAccount customerAccount=new CustomerAccount(null, null, null,email, null,password);
//                    Customer customer=new Customer(null, customerAccount, name, new Date());
//                    Intent intent=new Intent(RegisterActivity.this, RegisterOTP.class);
//                    intent.putExtra("Customer",customer);
//                    startActivity(intent);
                }
            }
        });
        View view = activityRegisterBinding.getRoot();
        setContentView(view);
    }

   void SetOriginBackground(){
       activityRegisterBinding.txtName.setBackground(getDrawable(R.drawable.border));
       activityRegisterBinding.txtEmail.setBackground(getDrawable(R.drawable.border));
       activityRegisterBinding.txtPass.setBackground(getDrawable(R.drawable.border));
       activityRegisterBinding.txtConfirmPass.setBackground(getDrawable(R.drawable.border));
   }
}