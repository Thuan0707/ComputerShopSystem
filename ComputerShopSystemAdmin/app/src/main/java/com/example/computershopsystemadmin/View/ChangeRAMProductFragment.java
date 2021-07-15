package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computershopsystemadmin.databinding.ChangeNameProductFragmentBinding;
import com.example.computershopsystemadmin.databinding.ChangeRamProductFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class ChangeRAMProductFragment extends Fragment {
ChangeRamProductFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = ChangeRamProductFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }
}