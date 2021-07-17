package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computershopsystemadmin.Model.Product;
import com.example.computershopsystemadmin.Model.Ram;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Utils;
import com.example.computershopsystemadmin.Utilities.Validation;
import com.example.computershopsystemadmin.Utilities.Variable;
import com.example.computershopsystemadmin.databinding.ChangeCpuProductFragmentBinding;
import com.example.computershopsystemadmin.databinding.ChangeNameProductFragmentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class ChangeNameProductFragment extends Fragment {

    ChangeNameProductFragmentBinding binding;
    Product product;
Product pro;
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = ChangeNameProductFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle = this.getArguments();

        if (bundle != null) {


            if (bundle.getSerializable("newProduct") != null) {
                product=(Product)bundle.getSerializable("newProduct");
                binding.tvTitle.setText("ADD NEW PRODUCT: Add Name");
                binding.btnSave.setText("Next");
            }else{
       pro = (Product) bundle.getSerializable("product");
                binding.edName.setText(pro.getName());
            }
            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = binding.edName.getText().toString();
                    Validation validation = new Validation();
                    String notify = validation.CheckNameProduct(name);
                    if (notify != null) {
                        binding.edName.setBackground(getActivity().getDrawable(R.drawable.border_red));
                        binding.edName.setError(notify);
                    } else {

                        if (bundle.getSerializable("newProduct") != null) {
                            Bundle bundle = new Bundle();
                            product.setName(name);
                            ChangeBrandProductFragment fragment = new ChangeBrandProductFragment();
                            bundle.putSerializable("newProduct", product);
                            fragment.setArguments(bundle);
                            switchFragment(fragment);
                        } else {
                            Bundle bundle = new Bundle();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + pro.getId() + "/name");
                            mDatabase.setValue(name);
                            pro.setName(name);
                            ProductDetailsFragment fragment = new ProductDetailsFragment();
                            String productJsonString = Utils.getGsonParser().toJson(pro);
                            bundle.putString(Variable.DETAIL_KEY, productJsonString);
                            fragment.setArguments(bundle);
                            switchFragment(fragment);
                        }
                    }
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