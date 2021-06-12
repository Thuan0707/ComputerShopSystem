package com.example.computershopsystem.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.util.Log;

import android.view.View;


import com.example.computershopsystem.Model.Brand;
import com.example.computershopsystem.Model.CPU;
import com.example.computershopsystem.Model.Customer;
import com.example.computershopsystem.Model.CustomerAccount;
import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.Model.Ram;
import com.example.computershopsystem.Model.Rom;
import com.example.computershopsystem.Model.Screen;
import com.example.computershopsystem.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnActive, btnWrong, btnRegister, btnHome, btnHomeLogin;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper,new CusHomeFragment()).commit();
        String id;
        BottomNavigationView nav_bot = findViewById(R.id.nav_bot);
        nav_bot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        selectedFragment=new CusHomeFragment();
                        break;
                    case R.id.ic_user:
                        selectedFragment=new TestLoginLogoutFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper,selectedFragment).commit();
                return true;
            }
        });
//        mDatabase = FirebaseDatabase.getInstance().getReference("Product");
//        id = mDatabase.push().getKey();
//       Brand brand = new Brand(null, "MSI", "Good", null, null);
//       CPU cpu = new CPU(null, "i5", "Intel Core i5 Comet Lake-10300H ", null, null);
//        Screen screen=new Screen(null,"15.6","Full HD (1920 x 1080), 144Hz",null,null);
//        Ram ram=new Ram(null,8,"DDR4 - 2666 MHz",null,null);
//        Rom rom=new Rom(null,512,"Supporting HDD SATASSD 512 GB NVMe PCIe", null,null);
//        Product product = new Product(cpu, ram, rom, brand, screen, id, "MSI GF63 10SC", "image", "i5 10300H 8GB/512GB/4GB GTX1650/144Hz/Win10 (255VN)", 15,9000000, 20990000, new Date(), null);
//        mDatabase.child(id).setValue(product);


    }


}