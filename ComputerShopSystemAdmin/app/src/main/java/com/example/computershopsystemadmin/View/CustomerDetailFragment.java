package com.example.computershopsystemadmin.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class CustomerDetailFragment extends Fragment {

    Customer customer;
    TextView account;

    Button btnPayment;
    Button btnProfile;
    Button btnOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.customer_detail_fragment, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            customer = (Customer) bundle.getSerializable("customer");
        }
        String name = customer.getFullName();
        account = v.findViewById(R.id.tvAccount2);
        account.setText(name);
        btnPayment = v.findViewById(R.id.btnPayment);
        btnProfile = v.findViewById(R.id.btnProfile);
        btnOrder = v.findViewById(R.id.btnOrder);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerProfileFragment fragment = new CustomerProfileFragment();
                Bundle bun = new Bundle();
                bun.putSerializable("customer", customer);
                fragment.setArguments(bun);
                switchFragment(fragment);
            }
        });

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditCardFragment fragment = new CreditCardFragment();
                Bundle bun = new Bundle();
                bun.putSerializable("customer", customer);
                fragment.setArguments(bun);
                switchFragment(fragment);
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderHistoryFragment fragment = new OrderHistoryFragment();
                Bundle bun = new Bundle();
                bun.putSerializable("customer", customer);
                fragment.setArguments(bun);
                switchFragment(fragment);
            }
        });

        return v;
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