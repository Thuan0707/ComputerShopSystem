package com.example.computershopsystemadmin.View;

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

import com.example.computershopsystemadmin.DAO.OrderHistoryFirebaseHelper;
import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.Model.Order;
import com.example.computershopsystemadmin.databinding.OrderHistoryFragmentBinding;

import com.google.firebase.database.DatabaseReference;
import com.example.computershopsystemadmin.R;
import com.google.firebase.database.FirebaseDatabase;

public class OrderHistoryFragment extends Fragment {
    OrderHistoryFragmentBinding binding;
    DatabaseReference db;
Customer customer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = OrderHistoryFragmentBinding.inflate(getLayoutInflater());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            customer = (Customer) bundle.getSerializable("customer");
        }

        db = FirebaseDatabase.getInstance().getReference("Customer/" + customer.getId() + "/orderList");
        OrderHistoryFirebaseHelper helper = new OrderHistoryFirebaseHelper(db, getContext());
        helper.getList(binding.lvHistoryOrder);
        binding.lvHistoryOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order=(Order) parent.getAdapter().getItem(position);
                Bundle bundle = new Bundle();
                OrderHistoryDetailFragment fragment = new OrderHistoryDetailFragment();
                bundle.putSerializable("order", order);
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