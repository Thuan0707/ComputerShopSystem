package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computershopsystemadmin.databinding.ChangeRomProductFragmentBinding;
import com.example.computershopsystemadmin.databinding.ChangeScreenProductFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class ChangeScreenProductFragment extends Fragment {
ChangeScreenProductFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = ChangeScreenProductFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }
}