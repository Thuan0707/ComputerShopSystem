package com.example.computershopsystem.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.computershopsystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference();
        mDatabase.setValue("asfdsadfasd1111fdsfasfsdsssssssfasf");
        Log.e("sadffffffffffffffffff",mDatabase.toString());

    }
}