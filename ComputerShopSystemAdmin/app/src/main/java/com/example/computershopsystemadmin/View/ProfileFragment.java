package com.example.computershopsystemadmin.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystemadmin.DAO.ProfileFirebaseHelper;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.ProfileFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    ProfileFirebaseHelper helper;
    DatabaseReference databaseReference;
    ProfileFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer");
        helper = new ProfileFirebaseHelper(databaseReference, getActivity());
        helper.loadACustomer(firebaseUser.getUid(), binding.tvBirthday, binding.tvGender);
        binding.tvName.setText(firebaseUser.getDisplayName());
        binding.tvEmail.setText(firebaseUser.getEmail());
        binding.tvPhone.setText(firebaseUser.getPhoneNumber());
        if (firebaseUser.getPhotoUrl() != null) {
            Picasso.get().load(firebaseUser.getPhotoUrl()).into(binding.imgAvatar);
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
        binding.tvPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassFragment fragment = new ChangePassFragment();
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