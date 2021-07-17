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

import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Validation;
import com.example.computershopsystemadmin.databinding.ChangePassFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                String oldPassword = binding.txtOldPass.getText().toString().trim();
                String newPassword = binding.txtNewPass.getText().toString().trim();
                String confirmPassword = binding.txtConfirmNewPass.getText().toString().trim();
                Validation validation = new Validation();
                String newPasswordNotify = validation.CheckPassword(newPassword);
                String confirmPasswordNotify = validation.CheckConfirmPassword(newPassword, confirmPassword);
                if (newPasswordNotify != null) {
                    binding.txtNewPass.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    binding.txtNewPass.setError(newPasswordNotify);
                }
                if (confirmPasswordNotify != null) {
                    binding.txtConfirmNewPass.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    binding.txtConfirmNewPass.setError(confirmPasswordNotify);
                }
                if (validation.isValid()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider
                            .getCredential("admin@fpt.edu.vn", oldPassword);
                    if (credential!=null) {


                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(getContext(), "Change password is successfully!!!!", Toast.LENGTH_SHORT).show();
                                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Admin/" + FirebaseAuth.getInstance().getCurrentUser().getUid()+"/password");
                                                        mDatabase.setValue(newPassword);
                                                        switchFragment(new ProfileAdminFragment());
                                                    } else {
                                                        binding.txtOldPass.setBackground(getActivity().getDrawable(R.drawable.border_red));
                                                        binding.txtOldPass.setError("Old Password is wrong");
                                                    }
                                                }
                                            });
                                        } else {
                                            binding.txtOldPass.setBackground(getActivity().getDrawable(R.drawable.border_red));
                                            binding.txtOldPass.setError("Old Password is wrong");

                                        }
                                    }
                                });
                    }else{
                        binding.txtOldPass.setBackground(getActivity().getDrawable(R.drawable.border_red));
                        binding.txtOldPass.setError("Old Password is wrong");
                    }
                }
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