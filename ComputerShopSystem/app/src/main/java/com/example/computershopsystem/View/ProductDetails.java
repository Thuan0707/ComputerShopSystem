package com.example.computershopsystem.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.R;

public class ProductDetails extends AppCompatActivity {

    public Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

    }
}