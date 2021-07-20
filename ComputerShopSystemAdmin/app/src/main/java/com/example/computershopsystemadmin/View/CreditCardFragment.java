package com.example.computershopsystemadmin.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystemadmin.Controller.LVCreditCardAdapter;
import com.example.computershopsystemadmin.Model.CreditCard;
import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.CreditCardFragmentBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class CreditCardFragment extends Fragment {
    CreditCardFragmentBinding binding;

    private ListView listView;
    Customer customer;
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = CreditCardFragmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            customer = (Customer) bundle.getSerializable("customer");
        }
        getList();
        binding.btnGotoAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputCreditCardFragment fragment = new InputCreditCardFragment();
                Bundle bun=new Bundle();
                bun.putSerializable("customer",customer);
                fragment.setArguments(bun);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
                fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                        android.R.animator.fade_out);
                fragTransaction.addToBackStack(null);
                fragTransaction.replace(R.id.fl_wrapper, fragment);
                fragTransaction.commit();
            }
        });
        return view;
    }

    public void getList() {
        ArrayList<CreditCard> creditCards = new ArrayList<>();
        Query data = FirebaseDatabase.getInstance().getReference("Customer").child(customer.getId()).child("cardList");
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot shot : dataSnapshot.getChildren()) {
                        CreditCard creditCard = shot.getValue(CreditCard.class);
                        creditCards.add(creditCard);
                    }
                }

                listView = binding.lvCard;
                LVCreditCardAdapter LVCreditCardAdapter = new LVCreditCardAdapter(getActivity(), R.layout.card_item, creditCards, customer);
                listView.setAdapter(LVCreditCardAdapter);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}