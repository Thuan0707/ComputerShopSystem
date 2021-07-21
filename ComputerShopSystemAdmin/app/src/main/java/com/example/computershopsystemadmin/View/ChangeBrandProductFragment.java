package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.computershopsystemadmin.Model.Brand;
import com.example.computershopsystemadmin.Model.Product;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Utils;
import com.example.computershopsystemadmin.Utilities.Validation;
import com.example.computershopsystemadmin.Utilities.Variable;
import com.example.computershopsystemadmin.databinding.ChangeBrandProductFragmentBinding;
import com.example.computershopsystemadmin.databinding.ChangeRomProductFragmentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class ChangeBrandProductFragment extends Fragment {
    ChangeBrandProductFragmentBinding binding;
    Product pro;
    Product product;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChangeBrandProductFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle = this.getArguments();
        //set Data to edit text
        if (bundle != null) {
            if (bundle.getSerializable("newProduct") != null) {
                product = (Product) bundle.getSerializable("newProduct");
                binding.tvTitle.setText("ADD NEW PRODUCT");
                binding.btnSave.setText("Next");
            } else {
                pro = (Product) bundle.getSerializable("product");
                binding.edBrand.setText(pro.getBrand().getName());
            }

                //Save brand to edit text
            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = binding.edBrand.getText().toString();
                    Validation validation = new Validation();
                    String notify = validation.CheckBrandProduct(name);
                    if (notify != null) {
                        binding.edBrand.setBackground(getActivity().getDrawable(R.drawable.border_red));
                        binding.edBrand.setError(notify);
                    } else {

                        if (bundle.getSerializable("newProduct") != null) {
                            Bundle bundle = new Bundle();
                            Brand brand = new Brand();
                            brand.setName(name);
                            product.setBrand(brand);
                            ChangeCPUProductFragment fragment = new ChangeCPUProductFragment();
                            bundle.putSerializable("newProduct", product);
                            fragment.setArguments(bundle);
                            switchFragment(fragment);
                        } else {
                            Toast.makeText(getContext(), "Change brand is successfully", Toast.LENGTH_SHORT).show();
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + pro.getId() + "/brand/name");
                            mDatabase.setValue(name);
                            pro.getBrand().setName(name);
                            ProductDetailsFragment fragment = new ProductDetailsFragment();
                            Bundle bundle = new Bundle();
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

    /**
     * change fragment
     * @param fragment
     */
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