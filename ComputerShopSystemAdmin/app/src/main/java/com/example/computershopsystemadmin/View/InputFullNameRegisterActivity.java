package com.example.computershopsystemadmin.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.Model.CustomerAccount;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class InputFullNameRegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_full_name_register);

        Validation validation = new Validation();
        EditText edName = findViewById(R.id.etNameRegister);

        Button btnContinue = findViewById(R.id.btnFullNameContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullNameNotify = validation.CheckName(edName.getText().toString());
                if (fullNameNotify != null) {
                    edName.setBackground(getDrawable(R.drawable.border_red));
                    edName.setError(fullNameNotify);
                } else {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseUser = firebaseAuth.getCurrentUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(edName.getText().toString())
                            .build();
                    firebaseUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                String numberPhone = getIntent().getStringExtra("phone");
                                CustomerAccount customerAccount = new CustomerAccount(firebaseUser.getUid(), numberPhone, null, null, null, null);
                                Customer customer = new Customer(firebaseUser.getUid(), customerAccount, edName.getText().toString(), new Date());
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Customer");
                                mDatabase.child(firebaseAuth.getCurrentUser().getUid()).setValue(customer);
                                Intent intent = new Intent(InputFullNameRegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                InputFullNameRegisterActivity.this.finish();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
//        FirebaseAuth firebaseAuth;
//        firebaseAuth=FirebaseAuth.getInstance();
//        if (firebaseAuth.getCurrentUser()==null){
//            Intent
//        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser().getDisplayName()==null){
            firebaseAuth.signOut();
            Intent intent = new Intent(InputFullNameRegisterActivity.this, MainActivity.class);
            startActivity(intent);

        }
        this.finish();
        super.onStop();
    }

}