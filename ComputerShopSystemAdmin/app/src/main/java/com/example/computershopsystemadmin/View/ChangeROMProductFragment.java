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
import com.example.computershopsystemadmin.Model.Rom;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Utils;
import com.example.computershopsystemadmin.Utilities.Variable;
import com.example.computershopsystemadmin.databinding.ChangeRamProductFragmentBinding;
import com.example.computershopsystemadmin.databinding.ChangeRomProductFragmentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class ChangeROMProductFragment extends Fragment {
    ChangeRomProductFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = ChangeRomProductFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Product product = (Product) bundle.getSerializable("product");

            binding.edDesROM.setText(product.getRom().getDescription());
            int pos = 0;
            switch (product.getRom().getCapacity()) {
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
            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String  capacity=binding.spROM.getSelectedItem().toString();
                    String  des=binding.edDesROM.getText().toString();
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + product.getId() + "/rom/capacity");
                    mDatabase.setValue(capacity);
                    product.getRom().setCapacity(capacity);
                    mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + product.getId() + "/rom/description");
                    mDatabase.setValue(des);
                    product.getRom().setDescription(des);
                    ProductDetailsFragment fragment = new ProductDetailsFragment();
                    Bundle bundle = new Bundle();
                    String productJsonString = Utils.getGsonParser().toJson(product);
                    bundle.putString(Variable.DETAIL_KEY, productJsonString);
                    fragment.setArguments(bundle);
                    switchFragment(fragment);
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