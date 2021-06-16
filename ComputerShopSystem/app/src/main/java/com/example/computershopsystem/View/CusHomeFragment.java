package com.example.computershopsystem.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.computershopsystem.DAO.ProductFirebaseHelper;
import com.example.computershopsystem.Model.GridAdapter;
import com.example.computershopsystem.databinding.CusHomeFragmentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CusHomeFragment extends Fragment {
    CusHomeFragmentBinding binding;
    ProductFirebaseHelper helper;
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CusHomeFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        helper=new ProductFirebaseHelper(databaseReference);
        GridAdapter gridAdapter = new GridAdapter(getActivity(), helper.retrieve());
        binding.gridProduct.setAdapter(gridAdapter);
        return view;
    }




}
