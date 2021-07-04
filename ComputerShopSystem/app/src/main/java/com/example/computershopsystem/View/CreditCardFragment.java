package com.example.computershopsystem.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computershopsystem.R;

import com.example.computershopsystem.databinding.CreditCardFragmentBinding;



public class CreditCardFragment extends Fragment {
    CreditCardFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = CreditCardFragmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        binding.btnGotoAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputCreditCardFragment fragment = new InputCreditCardFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
                fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                        android.R.animator.fade_out);
                fragTransaction.addToBackStack(null);
                fragTransaction.replace(R.id.fl_wrapper, fragment);
                fragTransaction.commit();
            }
        });
        return view;
    }
}