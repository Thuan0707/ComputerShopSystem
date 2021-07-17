package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.CustomerProfileFragmentBinding;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class CustomerProfileFragment extends Fragment {
    CustomerProfileFragmentBinding binding;
    Customer customer;
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = CustomerProfileFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle=this.getArguments();
        if (bundle!=null){
            customer=(Customer) bundle.getSerializable("customer");
        }
        binding.tvEmail.setText(customer.getCustomerAccount().getEmail());
        binding.tvPhone.setText(customer.getCustomerAccount().getPhone());
        binding.tvName.setText(customer.getFullName());
        if (customer.getDateOfBirth() != null) {
            binding.tvBirthday.setText(customer.getDateOfBirth());
        }
        String strGender = null;
        switch (customer.getGender()) {
            case 1:
                strGender = "Male";
                break;
            case 0:
                strGender = "Female";
                break;
            case 2:
                strGender = "Other";
                break;
        }
        binding.tvGender.setText(strGender);
        if (customer.getImage() != null) {

            Picasso.get().load(customer.getImage()).into(binding.imgAvatar);
        }

        binding.tvGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderFragment fragment = new GenderFragment();
                switchFragment(fragment);
                Bundle bundle = new Bundle();
                int gender = 0;
                if (binding.tvGender.getText() != null) {
                    switch (binding.tvGender.getText().toString()) {
                        case "Male":
                            gender = 1;
                            break;
                        case "Female":
                            gender = 0;
                            break;
                        case "Other":
                            gender = 2;
                            break;
                    }
                    bundle.putInt("gender", gender);
                    fragment.setArguments(bundle);
                }
                switchFragment(fragment);
            }
        });
        binding.tvBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                BirthdayFragment fragment = new BirthdayFragment();
                if (binding.tvPhone.getText() != null) {
                    bundle.putString("dateOfBirth", binding.tvBirthday.getText().toString());
                    fragment.setArguments(bundle);
                }
                switchFragment(fragment);
            }
        });
        binding.tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.tvEmail.getText().toString();
                if (email == "") {
                    ChangeEmailFragment fragment = new ChangeEmailFragment();

                    switchFragment(fragment);
                } else {
                    Toast.makeText(getContext(), "You can not change email", Toast.LENGTH_SHORT).show();

                }

            }
        });
        binding.tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                ChangePhoneFragment fragment = new ChangePhoneFragment();
                if (binding.tvPhone.getText() != null) {
                    bundle.putString("phone", binding.tvPhone.getText().toString());
                    fragment.setArguments(bundle);
                }

                switchFragment(fragment);
            }
        });

        binding.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ChangeNameFragment fragment = new ChangeNameFragment();
                if (binding.tvName.getText() != null) {
                    bundle.putString("name", binding.tvName.getText().toString());
                    fragment.setArguments(bundle);
                }
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