package com.example.computershopsystemadmin.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Validation;
import com.example.computershopsystemadmin.databinding.ChangePassFragmentBinding;

public class ChangePassFragment extends Fragment {

    ChangePassFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChangePassFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  oldPassword=binding.txtOldPass.getText().toString();
                String  newPassword=binding.txtNewPass.getText().toString();
                String  confirmPassword=binding.txtConfirmNewPass.getText().toString();
                Validation validation=new Validation();
                String newPasswordNotify=validation.CheckPassword(newPassword);
                String confirmPasswordNotify=validation.CheckConfirmPassword(newPassword,confirmPassword);
                if (newPasswordNotify!=null){
                    binding.txtNewPass.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    binding.txtNewPass.setError(newPasswordNotify);
                }
                if (confirmPasswordNotify!=null){
                    binding.txtConfirmNewPass.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    binding.txtConfirmNewPass.setError(confirmPasswordNotify);
                }
            }
        });
        return view;
    }
}