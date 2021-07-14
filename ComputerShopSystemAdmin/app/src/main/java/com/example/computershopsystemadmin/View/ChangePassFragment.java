package com.example.computershopsystemadmin.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.computershopsystemadmin.databinding.ChangePassFragmentBinding;

public class ChangePassFragment extends Fragment {

    ChangePassFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChangePassFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }
}