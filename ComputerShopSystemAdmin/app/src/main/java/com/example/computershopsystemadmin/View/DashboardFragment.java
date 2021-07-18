package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.computershopsystemadmin.databinding.CustomerProfileFragmentBinding;
import com.example.computershopsystemadmin.databinding.DashboardFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class DashboardFragment extends Fragment {
DashboardFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DashboardFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return  view;
    }
}