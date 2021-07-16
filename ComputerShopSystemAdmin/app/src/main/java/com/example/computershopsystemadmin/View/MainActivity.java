package com.example.computershopsystemadmin.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystemadmin.Model.OrderProduct;
import com.example.computershopsystemadmin.Model.Product;
import com.example.computershopsystemadmin.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    public Product product;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser!=null){
            sharedpreferences =getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();

        }
        switchFragment(new ProductManagementFragment());


        BottomNavigationView nav_bot = findViewById(R.id.nav_bot);
        nav_bot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Fragment selectedFragment = null;
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseUser = firebaseAuth.getCurrentUser();

                switch (item.getItemId()) {
                    case R.id.ic_home:
                        selectedFragment = new ProductManagementFragment();

                        break;
                    case R.id.ic_user:
                        if (firebaseUser != null) {
                            selectedFragment = new ProfileAdminFragment();
                        } else {
                            selectedFragment = new TestLoginLogoutFragment();
                        }
                        break;
                    case R.id.ic_cart:
                        if (firebaseUser != null) {
                            selectedFragment = new ManagementFragment();
                        } else {
                            selectedFragment = new TestLoginLogoutFragment();
                        }
                        break;
                    case R.id.ic_location:
                        if (firebaseUser != null) {

                            selectedFragment = new ProductDetailsFragment();
                        } else {
                            selectedFragment = new TestLoginLogoutFragment();
                        }
                        break;

                }
                switchFragment(selectedFragment);
                return true;
            }
        });
        // Add Product
//        mDatabase = FirebaseDatabase.getInstance().getReference("Product");
//        String id = mDatabase.push().getKey();
//       Brand brand = new Brand(null, "HP", "Good", null, null);
//       CPU cpu = new CPU(null, "i7", "Intel Core i5 Comet Lake-10300H ", null, null);
//        Screen screen=new Screen(null,"17","Full HD (1920 x 1080), 144Hz",null,null);
//        Ram ram=new Ram(null,16,"DDR4 - 2666 MHz",null,null);
//        Rom rom=new Rom(null,"1TB","Supporting HDD SATASSD 512 GB NVMe PCIe", null,null);
//        Product product = new Product(cpu, ram, rom, brand, screen, id, "Lenovo Legion y530", "image", "i5 10300H 8GB/512GB/4GB GTX1650/144Hz/Win10 (255VN)", 15,9000000, 35990000, new Date(), null);
//        mDatabase.child(id).setValue(product);

        //Add customer
//        mDatabase = FirebaseDatabase.getInstance().getReference("Customer");
//        CustomerAccount a=new CustomerAccount("1QzUXC8c0bQxtJY0EiWOgdHwnIw2","012345678","null","null","null");
//        Customer customer=new Customer("1QzUXC8c0bQxtJY0EiWOgdHwnIw2",a,"A@gmail.com","Dang Minh A",new Date(),"Can Tho", 1,100000,new Date(),null);
//
//        mDatabase.child("1QzUXC8c0bQxtJY0EiWOgdHwnIw2").setValue(customer);

//              //  add voucher
//                mDatabase = FirebaseDatabase.getInstance().getReference("Voucher");
//                String  id=mDatabase.push().getKey();
//        Voucher voucher=new Voucher( id,"ABCDEF",1500, "SPRING SALE", "11/7/2021",null);
//        mDatabase.child(id).setValue(voucher);
    }

    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        commit(key, json);
    }

    public void commit(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    public List<OrderProduct> getList() {
        List<OrderProduct> listProduct = new ArrayList<>();
        String serializedObject = sharedpreferences.getString("cart", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<OrderProduct>>() {
            }.getType();
            listProduct = gson.fromJson(serializedObject, type);
        }
        return listProduct;
    }
    public void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragTransaction.addToBackStack(null);
        fragTransaction.replace(R.id.fl_wrapper, fragment);
        fragTransaction.commit();
    }
}