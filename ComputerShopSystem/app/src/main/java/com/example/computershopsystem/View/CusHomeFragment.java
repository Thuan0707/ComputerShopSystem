package com.example.computershopsystem.View;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystem.DAO.ProductFirebaseHelper;
import com.example.computershopsystem.Addapter.GridAdapter;
import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.CusHomeFragmentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;


public class CusHomeFragment extends Fragment {
    CusHomeFragmentBinding binding;
    ProductFirebaseHelper helper;
    DatabaseReference databaseReference;
    GridAdapter gridAdapter;
    GridView gridView;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CusHomeFragmentBinding.inflate(getLayoutInflater());

        databaseReference = FirebaseDatabase.getInstance().getReference("Product");
        helper = new ProductFirebaseHelper(databaseReference,binding.gridProduct,getActivity());
        helper.retrieve();





        binding.tvMoreCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreCategoryFragment fragment = new MoreCategoryFragment(binding.gridProduct);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
                fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                        android.R.animator.fade_out);
                fragTransaction.addToBackStack(null);
                fragTransaction.add(R.id.fl_wrapper, fragment);
                fragTransaction.commit();
            }
        });
        binding.txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    helper.retrieveByName(s.toString());

                } else {
                    helper.retrieve();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.btnAsus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.retrieveByBrand("Asus".trim());
            }
        });
        binding.btnHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.retrieveByBrand("HP".trim());
            }
        });
        binding.btnAcer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.retrieveByBrand("Acer".trim());
            }
        });
        binding.btnDell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.retrieveByBrand("Dell".trim());
            }
        });
        View view = binding.getRoot();
        return view;
    }



    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}
