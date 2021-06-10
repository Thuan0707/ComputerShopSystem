package com.example.computershopsystem.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.computershopsystem.DAO.DAO;
import com.example.computershopsystem.Model.GridAdapter;
import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.ActivityHomeBinding;
//import com.example.computershopsystem.databinding.ActivityMainBinding;

public class HomeActivity extends AppCompatActivity {
ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DAO dao=new DAO();
        dao.loadProduct();
        GridAdapter gridAdapter=new GridAdapter(HomeActivity.this,dao.getListProduct());
        binding.gridProduct.setAdapter(gridAdapter);
    }
}