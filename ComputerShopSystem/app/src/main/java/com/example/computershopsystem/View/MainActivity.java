package com.example.computershopsystem.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.computershopsystem.Model.CartProduct;
import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.CartFragmentBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    public Product product;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<CartProduct> cartProductList = new ArrayList<>();

        getSupportFragmentManager().beginTransaction().add(R.id.fl_wrapper, new CusHomeFragment()).commit();
        BottomNavigationView nav_bot = findViewById(R.id.nav_bot);
        nav_bot.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                Fragment selectedFragment = null;
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    sharedpreferences = getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
                    editor = sharedpreferences.edit();
                    setList("cart", cartProductList);
                }
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        selectedFragment = new CusHomeFragment();

                        break;
                    case R.id.ic_user:
                        if (firebaseUser != null) {
                            selectedFragment = new AccountLoginSuccessFragment();
                        } else {
                            selectedFragment = new TestLoginLogoutFragment();
                        }
                        break;
                    case R.id.ic_cart:
                        if (firebaseUser != null) {
                            selectedFragment = new CartFragment();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_wrapper, selectedFragment).commit();
                return true;
            }
        });
        //Add Product
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

}