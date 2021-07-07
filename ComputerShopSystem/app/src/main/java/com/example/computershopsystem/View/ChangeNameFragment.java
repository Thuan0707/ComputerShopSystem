package com.example.computershopsystem.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.computershopsystem.databinding.ChangeNameFragmentBinding;

public class ChangeNameFragment extends Fragment {

    ChangeNameFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChangeNameFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

}