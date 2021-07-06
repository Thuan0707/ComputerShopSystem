package com.example.computershopsystem.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.ProfileFragmentBinding;

public class ProfileFragment extends Fragment {

    ProfileFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        binding.txtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderFragment fragment = new GenderFragment();
                switchFragment(fragment);
            }
        });
        binding.txtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BirthdayFragment fragment = new BirthdayFragment();
                switchFragment(fragment);
            }
        });
        binding.txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeEmailFragment fragment = new ChangeEmailFragment();
                switchFragment(fragment);
            }
        });
        binding.txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePhoneFragment fragment = new ChangePhoneFragment();
                switchFragment(fragment);
            }
        });
        binding.txtPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassFragment fragment = new ChangePassFragment();
                switchFragment(fragment);
            }
        });
        binding.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeNameFragment fragment = new ChangeNameFragment();
                switchFragment(fragment);
            }
        });
        return view;
    }

   void switchFragment(Fragment fragment){
       FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
       FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
       fragTransaction.setCustomAnimations(android.R.animator.fade_in,
               android.R.animator.fade_out);
       fragTransaction.addToBackStack(null);
       fragTransaction.replace(R.id.fl_wrapper, fragment);
       fragTransaction.commit();
    }
}