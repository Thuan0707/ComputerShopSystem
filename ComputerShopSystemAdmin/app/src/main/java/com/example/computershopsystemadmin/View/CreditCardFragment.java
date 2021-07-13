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

import com.example.computershopsystemadmin.Addapter.LVCreditCardAdapter;
import com.example.computershopsystemadmin.Model.CreditCard;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.CreditCardFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class CreditCardFragment extends Fragment {
    CreditCardFragmentBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = CreditCardFragmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        getList();
        binding.btnGotoAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputCreditCardFragment fragment = new InputCreditCardFragment();
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
        Query data = FirebaseDatabase.getInstance().getReference("Customer").child(firebaseUser.getUid()).child("card");
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
                LVCreditCardAdapter LVCreditCardAdapter = new LVCreditCardAdapter(getActivity(), R.layout.card_item, creditCards);
                listView.setAdapter(LVCreditCardAdapter);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}