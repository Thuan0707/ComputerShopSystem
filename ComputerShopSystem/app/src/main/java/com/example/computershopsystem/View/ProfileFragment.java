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

import com.example.computershopsystem.DAO.ProfileFirebaseHelper;
import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.ProfileFragmentBinding;
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
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfileFragmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer");
        helper=new ProfileFirebaseHelper(databaseReference,getActivity());
        binding.tvName.setText(firebaseUser.getDisplayName());
        binding.tvEmail.setText(firebaseUser.getEmail());
        binding.tvPhone.setText(firebaseUser.getPhoneNumber());
        if(firebaseUser.getPhotoUrl()!=null){
            Picasso.get().load(firebaseUser.getPhotoUrl()).into(binding.imgAvatar);
        }


        binding.tvGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderFragment fragment = new GenderFragment();
                switchFragment(fragment);
            }
        });
        binding.tvBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BirthdayFragment fragment = new BirthdayFragment();
                switchFragment(fragment);
            }
        });
        binding.tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeEmailFragment fragment = new ChangeEmailFragment();
                switchFragment(fragment);
            }
        });
        binding.tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("phone", binding.tvPhone.getText().toString());

                ChangePhoneFragment fragment = new ChangePhoneFragment();
                fragment.setArguments(bundle);
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