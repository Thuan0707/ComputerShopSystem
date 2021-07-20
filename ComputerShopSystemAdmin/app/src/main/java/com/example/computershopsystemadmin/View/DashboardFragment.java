package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.computershopsystemadmin.DAO.CustomerFirebaseHelper;
import com.example.computershopsystemadmin.databinding.CustomerProfileFragmentBinding;
import com.example.computershopsystemadmin.databinding.DashboardFragmentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class DashboardFragment extends Fragment {
    DashboardFragmentBinding binding;
    CustomerFirebaseHelper helper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DashboardFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Customer");
        helper = new CustomerFirebaseHelper(databaseReference, getActivity());
        helper.profit(binding.tvProfit);
        return view;
    }
}