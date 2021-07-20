package com.example.computershopsystem.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystem.DAO.VoucherFirebaseHelper;
import com.example.computershopsystem.Model.CreditCard;
import com.example.computershopsystem.Model.Voucher;
import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.VoucherFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VoucherFragment extends Fragment {
    VoucherFragmentBinding binding;
    DatabaseReference databaseReference;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
CreditCard creditCard;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = VoucherFragmentBinding.inflate(getLayoutInflater());
        sharedpreferences = getActivity().getSharedPreferences(FirebaseAuth.getInstance().getCurrentUser().getUid(), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReference("Voucher");
        VoucherFirebaseHelper helper = new VoucherFirebaseHelper(databaseReference, getContext());
        helper.getList(binding.lvVoucher);
        Bundle bundle=this.getArguments();
        binding.lvVoucher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Voucher voucher = (Voucher) parent.getAdapter().getItem(position);
           // creditCard=(CreditCard)bundle.getSerializable("creditCard");

                bundle.putSerializable("voucher", voucher);
                CheckOutFragment fragment = new CheckOutFragment();
                fragment.setArguments(bundle);
                switchFragment(fragment);
            }
        });
        View view = binding.getRoot();
        return view;
    }

    void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragTransaction.addToBackStack(null);
        fragTransaction.replace(R.id.fl_wrapper, fragment);
        fragTransaction.commit();
    }
}