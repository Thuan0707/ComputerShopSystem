package com.example.computershopsystem.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import android.view.View;


import com.example.computershopsystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference mDatabase=FirebaseDatabase.getInstance().getReference();
        mDatabase.setValue("asf");
        Log.e("sadffffffffffffffffff",mDatabase.toString());

    }

    public  void  changePage(View view){
        Intent intent = new Intent(this, ProductDetails.class);
        startActivity(intent);
    }
    public  void  pageSearch(View view){
        Intent intent = new Intent(this, SearchInput.class);
        startActivity(intent);
    }
    public  void  pageSearchResult(View view){
        Intent intent = new Intent(this, SearchResult.class);
        startActivity(intent);
    }
    public  void  pageSearchNotFound(View view){
        Intent intent = new Intent(this, SearchNotFound.class);
        startActivity(intent);
    }
    public  void  pageNotLogin(View view){
        Intent intent = new Intent(this, AccountNotLogin.class);
        startActivity(intent);
    }
    public  void  pageAccountLoginSuccess(View view){
        Intent intent = new Intent(this, AccountLoginSuccess.class);
        startActivity(intent);
    }
    public  void  pageMoreCategory(View view){
        Intent intent = new Intent(this, MoreCategory.class);
        startActivity(intent);
    }

}