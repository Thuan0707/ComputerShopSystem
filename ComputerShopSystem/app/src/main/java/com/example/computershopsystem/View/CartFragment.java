package com.example.computershopsystem.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.computershopsystem.Addapter.ListViewAdapter;
import com.example.computershopsystem.Model.CartProduct;
import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.R;

import com.example.computershopsystem.databinding.CartFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    public Product product;
    private ListView listView;
    CartFragmentBinding binding;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CartFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        listView = binding.listCart;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sharedpreferences = getActivity().getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        List<CartProduct> productList = getList();
        ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(), R.layout.cart_item, productList);
        listView.setAdapter(listViewAdapter);
        return view;
    }

    public List<CartProduct> getList() {
        List<CartProduct> listProduct = new ArrayList<>();
        String serializedObject = sharedpreferences.getString("cart", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<CartProduct>>() {
            }.getType();
            listProduct = gson.fromJson(serializedObject, type);
        }
        return listProduct;
    }
}
