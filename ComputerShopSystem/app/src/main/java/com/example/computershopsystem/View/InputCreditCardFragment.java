package com.example.computershopsystem.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.computershopsystem.Model.CreditCard;
import com.example.computershopsystem.R;
import com.example.computershopsystem.Utilities.Validation;
import com.example.computershopsystem.databinding.CreditCardFragmentBinding;
import com.example.computershopsystem.databinding.InputCreditCardFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InputCreditCardFragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    InputCreditCardFragmentBinding binding;
    EditText numberCard;
    EditText money;
    EditText expirationDate;
    EditText holderCard;


    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = InputCreditCardFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        numberCard = binding.edCardNumberAdd;
        money = binding.edMoneyCardAdd;
        expirationDate = binding.edExpirationCardAdd;
        holderCard = binding.edCardHolderAdd;

        numberCard.setInputType(InputType.TYPE_CLASS_NUMBER);
        expirationDate.setInputType(InputType.TYPE_CLASS_DATETIME);
        money.setInputType(InputType.TYPE_CLASS_NUMBER);
        numberCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    numberCard.setError("Please enter Number Card");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    money.setError("Please enter Money");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        expirationDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    expirationDate.setError("Please enter Expiration Date");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holderCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    holderCard.setError("Please enter Holder Card");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        binding.btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean check = true;
                String cardNumber = String.valueOf(numberCard.getText());
                if(cardNumber.equals("")){
                    numberCard.setError("Please enter Number Card");
                    check = false;
                }
                Date cardExpiration = new Date();
                try {
                    cardExpiration = formatter.parse(String.valueOf(expirationDate.getText())) ;
                } catch (ParseException e) {
                    expirationDate.setError("Please enter Expiration Date following format MM/dd/yyyy");
                    check = false;
                }
                if(expirationDate.equals("")){
                    expirationDate.setError("Please enter Expiration Date");
                    check = false;
                }
                String cardHolder = String.valueOf(holderCard.getText());
                if(holderCard.equals("")){
                    holderCard.setError("Please enter Holder card");
                    check = false;
                }
                String cardMoney = String.valueOf(money.getText());
                if(money.equals("")){
                    money.setError("Please enter Money");
                    check = false;
                }
                if(check) {
                    CreditCard creditCard = new CreditCard(cardNumber, cardMoney, cardNumber, cardExpiration, cardHolder, new Date(), new Date());
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer").child(firebaseUser.getUid()).child("card");
                    databaseReference.push().setValue(creditCard);
                    CreditCardFragment fragment = new CreditCardFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
                    fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                            android.R.animator.fade_out);
                    fragTransaction.addToBackStack(null);
                    fragTransaction.replace(R.id.fl_wrapper, fragment);
                    fragTransaction.commit();
                }
            }
        });

        return  view;
    }

}