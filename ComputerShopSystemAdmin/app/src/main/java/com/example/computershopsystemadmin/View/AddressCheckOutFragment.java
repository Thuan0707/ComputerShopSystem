package com.example.computershopsystemadmin.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Validation;
import com.example.computershopsystemadmin.databinding.AddressCheckOutFragmentBinding;

public class AddressCheckOutFragment extends Fragment {

    AddressCheckOutFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AddressCheckOutFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle=this.getArguments();
        if (bundle!=null){
            binding.edAddressFullNameCheckOut.setText(bundle.getString("nameCheckOut"));
            binding.edAddressPhoneCheckOut.setText(bundle.getString("phoneCheckOut"));
            binding.edAddressAddressCheckOut.setText(bundle.getString("addressCheckOut"));
        }
        binding.btnChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation validation = new Validation();
                String name = binding.edAddressFullNameCheckOut.getText().toString().trim();
                String phone = binding.edAddressPhoneCheckOut.getText().toString().trim();
                String address = binding.edAddressAddressCheckOut.getText().toString();
                String fullNameNotify = validation.CheckName(name);
                String phoneNotify = validation.CheckPhone(phone);

                String addressNotify = validation.CheckAddress(address);
                if (fullNameNotify != null) {
                    binding.edAddressFullNameCheckOut.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    binding.edAddressFullNameCheckOut.setError(fullNameNotify);
                }
                if (phoneNotify != null) {
                    binding.edAddressPhoneCheckOut.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    binding.edAddressPhoneCheckOut.setError(phoneNotify);
                }
                if (addressNotify != null) {
                    binding.edAddressAddressCheckOut.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    binding.edAddressAddressCheckOut.setError(addressNotify);
                }
                if (validation.isValid) {
                    Bundle bundle = new Bundle();
                    bundle.putString("nameCheckOut", name);
                    bundle.putString("phoneCheckOut", phone);
                    bundle.putString("addressCheckOut", address);
                    CheckOutFragment fragment = new CheckOutFragment();
                    fragment.setArguments(bundle);
                    switchFragment(fragment);
                }

            }
        });


        return view;
    }

    void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragTransaction.addToBackStack(null);
        fragTransaction.replace(R.id.fl_wrapper, fragment);
        fragTransaction.commit();
    }
}