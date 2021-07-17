package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.Model.CustomerAccount;
import com.example.computershopsystemadmin.Model.Product;
import com.example.computershopsystemadmin.Model.Ram;
import com.example.computershopsystemadmin.Model.Rom;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Utils;
import com.example.computershopsystemadmin.Utilities.Validation;
import com.example.computershopsystemadmin.Utilities.Variable;
import com.example.computershopsystemadmin.databinding.ChangeRamProductFragmentBinding;
import com.example.computershopsystemadmin.databinding.ChangeRomProductFragmentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class ChangeROMProductFragment extends Fragment {
    ChangeRomProductFragmentBinding binding;
    Product pro;
    Product product;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = ChangeRomProductFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.getSerializable("newProduct") != null) {
                product = (Product) bundle.getSerializable("newProduct");
                binding.tvTitle.setText("ADD NEW PRODUCT");
                binding.btnSave.setText("Next");
            } else {
                pro = (Product) bundle.getSerializable("product");

                binding.edDesROM.setText(pro.getRom().getDescription());
                int pos = 0;
                switch (pro.getRom().getCapacity()) {
                    case "SDD 128GB":
                        pos = 0;
                        break;
                    case "SSD 256GB":
                        pos = 1;
                        break;
                    case "SSD 512GB":
                        pos = 2;
                        break;
                    case "SSD 1TB":
                        pos = 3;
                        break;
                    case "HDD 512GB":
                        pos = 4;
                        break;
                    case "HDD 1TB":
                        pos = 5;
                        break;
                    case "HDD 2TB":
                        pos = 6;
                        break;
                }
                binding.spROM.setSelection(pos);
            }
            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String capacity = binding.spROM.getSelectedItem().toString();
                    String des = binding.edDesROM.getText().toString();
                    Validation validation = new Validation();
                    String notify = validation.CheckDescriptionProduct(des);
                    if (notify != null) {
                        binding.edDesROM.setBackground(getActivity().getDrawable(R.drawable.border_red));
                        binding.edDesROM.setError(notify);
                    } else {
                    if (bundle.getSerializable("newProduct") != null) {
                        Bundle bundle = new Bundle();
                        Rom rom = new Rom();
                        rom.setCapacity(capacity);
                        rom.setDescription(des);
                        product.setRom(rom);
                        ChangeScreenProductFragment fragment = new ChangeScreenProductFragment();
                        bundle.putSerializable("newProduct", product);
                        fragment.setArguments(bundle);
                        switchFragment(fragment);
                    } else {

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + pro.getId() + "/rom/capacity");
                        mDatabase.setValue(capacity);
                        pro.getRom().setCapacity(capacity);
                        mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + pro.getId() + "/rom/description");
                        mDatabase.setValue(des);
                        pro.getRom().setDescription(des);
                        ProductDetailsFragment fragment = new ProductDetailsFragment();
                        Bundle bundle = new Bundle();
                        String productJsonString = Utils.getGsonParser().toJson(pro);
                        bundle.putString(Variable.DETAIL_KEY, productJsonString);
                        fragment.setArguments(bundle);
                        switchFragment(fragment);
                    }}
                }
            });
        }
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