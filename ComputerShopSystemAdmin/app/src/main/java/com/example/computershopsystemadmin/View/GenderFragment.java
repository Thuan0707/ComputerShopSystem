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
import com.example.computershopsystemadmin.databinding.GenderFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class GenderFragment extends Fragment {
    GenderFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = GenderFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int gender = bundle.getInt("gender");
         switch (gender){
             case 0:
                 binding.rbFemale.setChecked(true);
                 break;
             case 1:
                 binding.rbMale.setChecked(true);
                 break;
             case 2:
                 binding.rbOther.setChecked(true);
                 break;

         }


        }
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int gender = 1;
                int rgGender = binding.rgGender.getCheckedRadioButtonId();
                int female = binding.rbFemale.getId();
                int male = binding.rbMale.getId();
                int other = binding.rbOther.getId();
                if (rgGender == female) {
                    gender = 0;
                } else if (rgGender == male) {
                    gender = 1;
                } else if (rgGender == other) {
                    gender = 2;
                }
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Admin/"+user.getUid()+"/gender");
                mDatabase.setValue(gender).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()){
                            ProfileAdminFragment fragment=new ProfileAdminFragment();
                            switchFragment(fragment);
                        }
                    }
                });
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