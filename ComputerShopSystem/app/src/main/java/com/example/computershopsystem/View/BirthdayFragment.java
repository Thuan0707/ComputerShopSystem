package com.example.computershopsystem.View;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystem.R;
import com.example.computershopsystem.Utilities.Validation;
import com.example.computershopsystem.databinding.BirthdayFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class BirthdayFragment extends Fragment {

    BirthdayFragmentBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BirthdayFragmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String str = bundle.getString("dateOfBirth");
            binding.edBirthday.setText(str);
        }
        binding.edBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                //getting current day,month and year.
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        binding.edBirthday.setBackground(getActivity().getDrawable(R.drawable.border));
                        binding.edBirthday.setError(null);
                        Validation validation=new Validation();
                        String notify=validation.CheckDOB(year);
                        if(notify!=null){
                            Toast.makeText(getContext(), notify , Toast.LENGTH_SHORT).show();
                        }
                        else{
                            binding.edBirthday.setText( (monthOfYear + 1)+ "/" +dayOfMonth  + "/" + year);
                        }

                    }
                };

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        dateSetListener, year, month, day);

                datePickerDialog.show();
            }
        });
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Customer/"+user.getUid()+"/dateOfBirth");
                mDatabase.setValue(binding.edBirthday.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "Change Date Of Birth Successfully" , Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getContext(), "Change Date Of Birth Fail" , Toast.LENGTH_SHORT).show();
                        }
                        switchFragment(new ProfileFragment());
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