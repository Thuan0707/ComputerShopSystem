package com.example.computershopsystem.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computershopsystem.Model.CreditCard;
import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.CreditCardFragmentBinding;
import com.example.computershopsystem.databinding.InputCreditCardFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = InputCreditCardFragmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        binding.btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardNumber = String.valueOf(binding.edCardNumberAdd.getText());
                Date cardExpiration = new Date();
                try {
                    cardExpiration = formatter.parse(String.valueOf(binding.edExpirationCardAdd.getText())) ;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String cardHolder = String.valueOf(binding.edCardHolderAdd.getText());
                String cardMoney = String.valueOf(binding.edMoneyCardAdd.getText());
                CreditCard creditCard = new CreditCard(cardNumber,cardMoney,cardNumber,null,cardHolder,null,null);
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer").child(firebaseUser.getUid());
                databaseReference.push().setValue(creditCard);
            }
        });

        return  view;
    }
}