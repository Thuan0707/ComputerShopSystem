package com.example.computershopsystem.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystem.Addapter.GridAdapter;
import com.example.computershopsystem.DAO.ProductFirebaseHelper;
import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.MoreCategoryFragmentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class MoreCategoryFragment extends Fragment {
    MoreCategoryFragmentBinding binding;
    ProductFirebaseHelper helper;
    DatabaseReference databaseReference;
    GridView gridProduct;
    Fragment parent;
    CusHomeFragment cusHomeFragment = new CusHomeFragment();
    FragmentManager fragmentManager;
    FragmentTransaction fragTransaction ;
    Bundle result = new Bundle();

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = MoreCategoryFragmentBinding.inflate(getLayoutInflater());
        fragmentManager = getActivity().getSupportFragmentManager();
        fragTransaction = fragmentManager.beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragTransaction.addToBackStack(null);
        buttonHighPrice();
        buttonMediumPrice();
        buttonHighPrice();

        View view = binding.getRoot();
        return view;

    }

    MoreCategoryFragment(GridView gridProduct) {
        this.gridProduct = gridProduct;
    }

    void buttonLowPrice() {
        binding.btnLowPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.putString("FilterLowPrice", "LowPrice");
                cusHomeFragment.setArguments(result);
                fragTransaction.replace(R.id.fl_wrapper, cusHomeFragment);
                fragTransaction.commit();
            }
        });
    }

    void buttonMediumPrice() {
        binding.btnLowPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.putString("FilterMediumPrice", "MediumPrice");
                cusHomeFragment.setArguments(result);
                fragTransaction.replace(R.id.fl_wrapper, cusHomeFragment);
                fragTransaction.commit();
            }
        });
    }

    void buttonHighPrice() {
        binding.btnLowPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.putString("FilterHighPrice", "HighPrice");
                cusHomeFragment.setArguments(result);
                fragTransaction.replace(R.id.fl_wrapper, cusHomeFragment);
                fragTransaction.commit();
            }
        });
    }
}