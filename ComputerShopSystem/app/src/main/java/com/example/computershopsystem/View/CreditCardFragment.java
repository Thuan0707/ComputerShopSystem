package com.example.computershopsystem.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystem.Addapter.LVCreditCardAdapter;
import com.example.computershopsystem.Model.CreditCard;
import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.CreditCardFragmentBinding;
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

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ArrayList<CreditCard> creditCards = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = CreditCardFragmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null){
            sharedpreferences = getContext().getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
        }
        getList();
        Log.e("LALALA","lllllllllllllllllllll"+creditCards.size());
        binding.btnGotoAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.remove("IdCard");
                editor.apply();
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
        Query data = FirebaseDatabase.getInstance().getReference("Customer").child(firebaseUser.getUid()).child("card");
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot shot : dataSnapshot.getChildren()) {
                        CreditCard creditCard = shot.getValue(CreditCard.class);
                        creditCards.add(creditCard);
                    }
                    listView = binding.lvCard;
                    LVCreditCardAdapter LVCreditCardAdapter = new LVCreditCardAdapter(getActivity(), R.layout.card_item, creditCards);
                    listView.setAdapter(LVCreditCardAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}