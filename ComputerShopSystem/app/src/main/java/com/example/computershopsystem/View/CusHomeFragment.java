package com.example.computershopsystem.View;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.computershopsystem.Model.GridAdapter;
import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.CusHomeFragmentBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.ArrayList;


public class CusHomeFragment extends Fragment {
    CusHomeFragmentBinding binding;
    ProductFirebaseHelper helper;
    DatabaseReference databaseReference;
    GridAdapter gridAdapter;
public static  CusHomeFragment newInstance(){
    CusHomeFragment cusHomeFragment=new CusHomeFragment();
    return  cusHomeFragment;
}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      //  binding = CusHomeFragmentBinding.inflate(getLayoutInflater());
        View view = inflater.inflate(R.layout.cus_home_fragment, container, false);

//        binding.tvMoreCate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MoreCategoryFragment fragment = new MoreCategoryFragment(binding.gridProduct);
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
//                fragTransaction.setCustomAnimations(android.R.animator.fade_in,
//                        android.R.animator.fade_out);
//                fragTransaction.addToBackStack(null);
//                fragTransaction.add(R.id.fl_wrapper, fragment);
//                fragTransaction.commit();
//            }
//        });
//        binding.txtSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!s.toString().isEmpty()) {
//                    helper.retrieveByName(s.toString());
//
//                } else {
//                    helper.retrieve();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.btnAsus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                helper.retrieveByBrand("Asus".trim());
//                if (helper.getList().size() == 0) {
//
//                }
//                gridAdapter.notifyDataSetChanged();
//            }
//        });
//        binding.btnHP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                helper.retrieveByBrand("HP".trim());
//                gridAdapter.notifyDataSetChanged();
//            }
//        });
//        binding.btnAcer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                helper.retrieveByBrand("Acer".trim());
//                gridAdapter.notifyDataSetChanged();
//            }
//        });
//        binding.btnDell.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                helper.retrieveByBrand("Dell".trim());
//                gridAdapter.notifyDataSetChanged();
//            }
//        });
   //     View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridView grid=view.findViewById(R.id.gridProduct);
        databaseReference = FirebaseDatabase.getInstance().getReference("Product");
        helper = new ProductFirebaseHelper(databaseReference);
        helper.retrieve();
        ArrayList<Product> listA = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listA.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot shot : snapshot.getChildren()) {
                        Product product = shot.getValue(Product.class);
                        Log.e("nameBrand",product.getBrand().getName());
                        Log.e("name",product.getName());
                        listA.add(product);
                    }
                }
                gridAdapter = new GridAdapter(getActivity(), listA);
                grid.setAdapter(gridAdapter);
                Log.e("list",String.valueOf(listA.size()));
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
        Log.e("num",String.valueOf(helper.getList().size()));

    }
}
