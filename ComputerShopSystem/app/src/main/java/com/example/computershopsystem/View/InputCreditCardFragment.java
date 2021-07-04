package com.example.computershopsystem.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.CreditCardFragmentBinding;
import com.example.computershopsystem.databinding.InputCreditCardFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class InputCreditCardFragment extends Fragment {
InputCreditCardFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = InputCreditCardFragmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();

        return  view;
    }
}