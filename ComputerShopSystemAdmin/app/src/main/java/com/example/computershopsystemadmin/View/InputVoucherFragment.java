package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.example.computershopsystemadmin.DAO.VoucherFirebaseHelper;
import com.example.computershopsystemadmin.Model.CreditCard;
import com.example.computershopsystemadmin.Model.Voucher;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.InputVoucherFragmentBinding;
import com.example.computershopsystemadmin.databinding.VoucherFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Date;

public class InputVoucherFragment extends Fragment {
    InputVoucherFragmentBinding binding;
    DatabaseReference databaseReference;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    EditText name, code, description, discount;
    Voucher voucher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = InputVoucherFragmentBinding.inflate(getLayoutInflater());
        sharedpreferences = getActivity().getSharedPreferences(FirebaseAuth.getInstance().getCurrentUser().getUid(), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String strVoucher = sharedpreferences.getString("voucher", null);
        editor.remove("voucher");
        editor.apply();
        voucher = gson.fromJson(strVoucher, Voucher.class);
        View view=binding.getRoot();
        name = binding.edNameAdd;
        code = binding.edCodedAdd;
        description = binding.edDescriptiondAdd;
        discount = binding.edDiscountAdd;

        if(strVoucher != null){
            binding.tvNameTop14.setText("Update Voucher");
            binding.btnAddVoucher.setText("Update Voucher");

            name.setText(voucher.getName());
            code.setText(voucher.getCode());
            discount.setText(String.valueOf(voucher.getDiscount()));
            description.setText(voucher.getDescription());
        }

        discount.setInputType(InputType.TYPE_CLASS_NUMBER);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name.setBackground(getActivity().getDrawable(R.drawable.border));
                if(s.toString().equals("")){
                    name.setError("Please enter Name Of Voucher!");
                    name.setBackground(getActivity().getDrawable(R.drawable.border_red));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                code.setBackground(getActivity().getDrawable(R.drawable.border));
                if(s.toString().equals("")){
                    code.setError("Please enter Code Of Voucher!");
                    code.setBackground(getActivity().getDrawable(R.drawable.border_red));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                description.setBackground(getActivity().getDrawable(R.drawable.border));
                if(s.toString().equals("")){
                    description.setError("Please enter Description Of Voucher!");
                    description.setBackground(getActivity().getDrawable(R.drawable.border_red));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        discount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                discount.setBackground(getActivity().getDrawable(R.drawable.border));
                if(s.toString().equals("")){
                    discount.setError("Please enter Discout Of Voucher!");
                    discount.setBackground(getActivity().getDrawable(R.drawable.border_red));
                }
                try {
                    if(Integer.parseInt(s.toString()) <= 0){
                        discount.setError("Discout must greater than $0!");
                        discount.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    }
                }catch (Exception ex){

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Add new voucher in voucher management
        binding.btnAddVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean check = true;
                if(String.valueOf(name.getText()).equals("")){
                    name.setError("Please enter Name Of Voucher !");
                    name.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    check = false;
                }

                if(String.valueOf(code.getText()).equals("")){
                    code.setError("Please enter Code Of Voucher !");
                    code.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    check = false;
                }

                if(String.valueOf(discount.getText()).equals("")){
                    discount.setError("Please enter Discout Of Voucher !");
                    discount.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    check = false;
                }

                if(String.valueOf(description.getText()).equals("")){
                    description.setError("Please enter Description Of Voucher !");
                    description.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    check = false;
                }

                if(check){
                    String id = null;
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Voucher");
                    if(strVoucher == null){
                        id = databaseReference.push().getKey();
                    }else{
                        id = voucher.getId();
                    }
                    Voucher voucher = new Voucher(id, code.getText().toString(), Double.parseDouble(discount.getText().toString()), name.getText().toString(),
                            description.getText().toString());
                    databaseReference.child(id).setValue(voucher);
                    VoucherFragment fragment = new VoucherFragment();
                    switchFragment(fragment);
                }
            }
        });
        return  view;
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
