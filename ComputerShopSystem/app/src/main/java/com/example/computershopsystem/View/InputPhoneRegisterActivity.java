package com.example.computershopsystem.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.computershopsystem.R;
import com.example.computershopsystem.Utilities.Validation;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class InputPhoneRegisterActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    private GoogleSignInClient googleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_phone_register);
        Button btnContinue=findViewById(R.id.btnPhoneContinue);
        EditText phone=findViewById(R.id.edPhoneRegister);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation validation=new Validation();
                String  notify=validation.CheckPhone(phone.getText().toString());
                if ( notify!=null){
                    phone.setBackground(getDrawable(R.drawable.border_red));
                   phone.setError(notify);
                }else{
                    Intent intent = new Intent(InputPhoneRegisterActivity.this, RegisterOTP.class);
                    if (getIntent().getStringExtra("facebook") != null) {
                        intent.putExtra("facebook",true);
                    }
                    intent.putExtra("changePhone",phone.getText().toString());
                    startActivity(intent);
                }

            }
        });

//        databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer");
//        CustomerAccount customerAccount = new CustomerAccount(null, phoneNumber, null, email, uid, null);
//        Customer customer = new Customer(uid, customerAccount, name, Calendar.getInstance().getTime());
//        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(customer);
    }

    @Override
    protected void onDestroy() {
        Log.e("d","destroy");
        FirebaseAuth.getInstance().signOut();
        signOut();
        super.onDestroy();
    }
    private void signOut() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(InputPhoneRegisterActivity.this, options);
        googleSignInClient.signOut();
    }
}