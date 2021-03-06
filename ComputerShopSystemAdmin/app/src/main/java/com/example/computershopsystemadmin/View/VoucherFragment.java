package com.example.computershopsystemadmin.View;

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

import com.example.computershopsystemadmin.DAO.VoucherFirebaseHelper;
import com.example.computershopsystemadmin.Model.Voucher;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.VoucherFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class VoucherFragment extends Fragment {
VoucherFragmentBinding binding;
  DatabaseReference databaseReference;
  SharedPreferences sharedpreferences;
  SharedPreferences.Editor editor;
  @Override
  public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
    binding = VoucherFragmentBinding.inflate(getLayoutInflater());
    sharedpreferences = getActivity().getSharedPreferences(FirebaseAuth.getInstance().getCurrentUser().getUid(), Context.MODE_PRIVATE);
    editor = sharedpreferences.edit();
    databaseReference = FirebaseDatabase.getInstance().getReference("Voucher");
    VoucherFirebaseHelper helper=new VoucherFirebaseHelper(databaseReference,getContext());
    helper.getList(binding.lvVoucher);

    binding.btnAddVoucher.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        editor.remove("voucher");
        editor.apply();
        InputVoucherFragment fragment = new InputVoucherFragment();
        switchFragment(fragment);
      }
    });
    View view=binding.getRoot();
    return  view;
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