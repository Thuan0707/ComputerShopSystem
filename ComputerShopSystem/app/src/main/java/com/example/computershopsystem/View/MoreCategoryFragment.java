package com.example.computershopsystem.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.computershopsystem.DAO.ProductFirebaseHelper;
import com.example.computershopsystem.databinding.MoreCategoryFragmentBinding;
import com.google.firebase.database.DatabaseReference;

import org.jetbrains.annotations.NotNull;

public class MoreCategoryFragment extends Fragment {
    MoreCategoryFragmentBinding binding;
    ProductFirebaseHelper helper;
    DatabaseReference databaseReference;
    GridView gridProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        MoreCategoryFragmentBinding binding;
        binding = MoreCategoryFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

//        databaseReference = FirebaseDatabase.getInstance().getReference("Product");
//        helper = new ProductFirebaseHelper(databaseReference);
//        GridAdapter gridAdapter = new GridAdapter(getActivity(), helper.retrieve());
//        gridProduct.setAdapter(gridAdapter);
//        binding.btnGiaRe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle result = new Bundle();
//                result.putString("giaReKey", "giaRe");
//                getParentFragmentManager().setFragmentResult("requestKey", result);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
//                fragTransaction.remove(fragmentManager.findFragmentById(R.id.fl_wrapper));
//                fragTransaction.commit();
//            }
//        });


        return view;

    }

    MoreCategoryFragment(GridView gridProduct) {
        this.gridProduct = gridProduct;
    }

}