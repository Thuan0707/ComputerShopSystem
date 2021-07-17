package com.example.computershopsystemadmin.View;

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

import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.ManagementFragmentBinding;
import com.example.computershopsystemadmin.databinding.ProductDetailsFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class ManagementFragment extends Fragment {

    ManagementFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ManagementFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.btnCustomerManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerManagementFragment fragment = new CustomerManagementFragment();
                switchFragment(fragment);
            }
        });

        binding.btnProductManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductManagementFragment fragment = new ProductManagementFragment();
                switchFragment(fragment);
            }
        });
        binding.btnVoucherManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoucherFragment fragment = new VoucherFragment();
                switchFragment(fragment);
            }
        });
        return view;
    }

    public void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragTransaction.addToBackStack(null);
        fragTransaction.replace(R.id.fl_wrapper, fragment);
        fragTransaction.commit();
    }
}