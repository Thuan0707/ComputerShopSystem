package com.example.computershopsystem.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.computershopsystem.DAO.OrderHistoryFirebaseHelper;
import com.example.computershopsystem.databinding.OrderHistoryFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderHistoryFragment extends Fragment {
    OrderHistoryFragmentBinding binding;
    DatabaseReference db;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = OrderHistoryFragmentBinding.inflate(getLayoutInflater());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference("Customer/" + user.getUid() + "/orderList");
        OrderHistoryFirebaseHelper helper = new OrderHistoryFirebaseHelper(db, getContext());
        helper.getList(binding.lvHistoryOrder);

        View view = binding.getRoot();
        return view;
    }
}