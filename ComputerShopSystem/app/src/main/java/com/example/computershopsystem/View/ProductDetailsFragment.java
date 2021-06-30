package com.example.computershopsystem.View;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.databinding.ProductDetailsFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ProductDetailsFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    public Product product;
    ProductDetailsFragmentBinding binding;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProductDetailsFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sharedpreferences = getActivity().getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product = new Product(null, null, null, null, null, "122333", "Lenovo Legion y530", "https://firebasestorage.googleapis.com/v0/b/computershopsystem-c38da.appspot.com/o/Asus%20A%20series.png?alt=media&token=10182f40-0b8c-482d-9cfd-52fe706a4d17", "i5 10300H 8GB/512GB/4GB GTX1650/144Hz/Win10 (255VN)", 15, 9000000, 35990000, null, null);
                List<Product> listProductInCart = getList();
                listProductInCart.add(product);
                setList("cart", listProductInCart);
            }
        });

        return view;
    }

    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        update(key, json);
    }

    public void update(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }
    public List<Product> getList(){
        List<Product> listProduct=new ArrayList<>();
        String serializedObject = sharedpreferences.getString(firebaseUser.getUid(), null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Product>>(){}.getType();
            listProduct = gson.fromJson(serializedObject, type);
        }
        return  listProduct;
    }
}