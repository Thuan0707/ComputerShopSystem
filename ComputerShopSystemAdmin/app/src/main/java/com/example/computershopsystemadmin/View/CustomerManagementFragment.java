package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.computershopsystemadmin.DAO.CustomerFirebaseHelper;
import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.CustomerManagementFragmentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerManagementFragment extends Fragment {
    CustomerManagementFragmentBinding binding;
    DatabaseReference databaseReference;
    CustomerFirebaseHelper helper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CustomerManagementFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        databaseReference = FirebaseDatabase.getInstance().getReference("Customer");
        helper = new CustomerFirebaseHelper(databaseReference, binding.lvCustomer, getActivity());
        helper.retrieve();
        binding.lvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("customer", (Customer) parent.getAdapter().getItem(position));
                CustomerProfileFragment fragment = new CustomerProfileFragment();
                fragment.setArguments(bundle);
                switchFragment(fragment);
            }
        });
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