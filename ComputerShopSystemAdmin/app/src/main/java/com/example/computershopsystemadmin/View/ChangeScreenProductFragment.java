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

import com.example.computershopsystemadmin.Model.Product;
import com.example.computershopsystemadmin.Model.Rom;
import com.example.computershopsystemadmin.Model.Screen;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Utils;
import com.example.computershopsystemadmin.Utilities.Validation;
import com.example.computershopsystemadmin.Utilities.Variable;
import com.example.computershopsystemadmin.databinding.ChangeRomProductFragmentBinding;
import com.example.computershopsystemadmin.databinding.ChangeScreenProductFragmentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class ChangeScreenProductFragment extends Fragment {
    ChangeScreenProductFragmentBinding binding;
    Product pro;
    Product product;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = ChangeScreenProductFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            if (bundle.getSerializable("newProduct") != null) {
                product = (Product) bundle.getSerializable("newProduct");
                binding.tvTitle.setText("ADD NEW PRODUCT");
                binding.btnSave.setText("Next");
            } else {
                pro = (Product) bundle.getSerializable("product");
                binding.edDesScreen.setText(pro.getScreen().getDescription());
                int pos = 0;
                switch (pro.getScreen().getSize()) {
                    case "14 Inch":
                        pos = 0;
                        break;
                    case "15.6 Inch":
                        pos = 1;
                        break;
                    case "17 Inch":
                        pos = 2;
                        break;
                    case "19 Inch":
                        pos = 3;
                        break;

                }
                binding.spScreen.setSelection(pos);
            }
            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String size = binding.spScreen.getSelectedItem().toString();
                    String des = binding.edDesScreen.getText().toString();
                    Validation validation = new Validation();
                    String notify = validation.CheckDescriptionProduct(des);
                    if (notify != null) {
                        binding.edDesScreen.setBackground(getActivity().getDrawable(R.drawable.border_red));
                        binding.edDesScreen.setError(notify);
                    } else {
                    if (bundle.getSerializable("newProduct") != null) {
                        Bundle bundle = new Bundle();
                        Screen screen = new Screen();
                        screen.setSize(size);
                        screen.setDescription(des);
                        product.setScreen(screen);
                        ChangePriceQuantityProductFragment fragment = new ChangePriceQuantityProductFragment();
                        bundle.putSerializable("newProduct", product);
                        fragment.setArguments(bundle);
                        switchFragment(fragment);
                    } else {

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + pro.getId() + "/screen/size");
                        mDatabase.setValue(size);
                        pro.getScreen().setSize(size);
                        mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + pro.getId() + "/screen/description");
                        mDatabase.setValue(des);
                        pro.getScreen().setDescription(des);
                        ProductDetailsFragment fragment = new ProductDetailsFragment();
                        Bundle bundle = new Bundle();
                        String productJsonString = Utils.getGsonParser().toJson(pro);
                        bundle.putString(Variable.DETAIL_KEY, productJsonString);
                        fragment.setArguments(bundle);
                        switchFragment(fragment);
                    }
                }}
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