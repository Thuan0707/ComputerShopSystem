package com.example.computershopsystem.View;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.databinding.ProductDetailsFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;



public class ProductDetailsFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    public Product product;
ProductDetailsFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
       binding=ProductDetailsFragmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        Log.e("admin",firebaseAuth.getCurrentUser().getDisplayName());
        return view;
    }
}