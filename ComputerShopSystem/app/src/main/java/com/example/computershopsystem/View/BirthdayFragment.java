package com.example.computershopsystem.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.computershopsystem.databinding.BirthdayFragmentBinding;

public class BirthdayFragment extends Fragment {

    BirthdayFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BirthdayFragmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        return view;
    }
}