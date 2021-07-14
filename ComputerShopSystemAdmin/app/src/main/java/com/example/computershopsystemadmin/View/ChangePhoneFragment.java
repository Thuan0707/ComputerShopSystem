package com.example.computershopsystemadmin.View;

import android.content.Intent;
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
import com.example.computershopsystemadmin.databinding.ChangePhoneFragmentBinding;

public class ChangePhoneFragment extends Fragment {

    ChangePhoneFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChangePhoneFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle = this.getArguments();
        String str = bundle.getString("phone");
        if (str != "") {
            binding.edPhone.setText("0"+str.substring(3));
        }
        binding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation validation=new Validation();
                String  notify=validation.CheckPhone(binding.edPhone.getText().toString());
                if ( notify!=null) {
                    binding.edPhone.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    binding.edPhone.setError(notify);
                }else{
                    Intent intent = new Intent(getContext(), RegisterOTP.class);
                    intent.putExtra("changePhone",binding.edPhone.getText().toString());
                    startActivity(intent);
                }
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