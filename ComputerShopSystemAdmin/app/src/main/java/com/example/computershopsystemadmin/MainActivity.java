package com.example.computershopsystemadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                mDatabase = FirebaseDatabase.getInstance().getReference("abc");
                String  id=mDatabase.push().getKey();

        mDatabase.child(id).setValue("ádfsaf");
    }
}