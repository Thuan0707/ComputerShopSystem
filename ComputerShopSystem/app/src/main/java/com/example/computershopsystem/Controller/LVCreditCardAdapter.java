package com.example.computershopsystem.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystem.Model.CreditCard;
import com.example.computershopsystem.R;
import com.example.computershopsystem.View.CheckOutFragment;
import com.example.computershopsystem.View.CreditCardFragment;
import com.example.computershopsystem.View.InputCreditCardFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.List;

public class LVCreditCardAdapter  extends ArrayAdapter<CreditCard> {
    private Context context;
    private int resource;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    public LVCreditCardAdapter(@NonNull Context context, int resource, @NonNull List<CreditCard> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null){
            sharedpreferences = getContext().getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
        }
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        TextView cardNumber = convertView.findViewById(R.id.txtCardNumber);
        TextView cardHolder = convertView.findViewById(R.id.txtCardHolder);
        TextView cardExpiration = convertView.findViewById(R.id.txtCardExpiration);
        TextView cardMoney = convertView.findViewById(R.id.txtCardMoney);
        ImageButton deleteCard = convertView.findViewById(R.id.ibtnDeleteCreditCard);
        boolean isCheckout = sharedpreferences.getBoolean("isCheckoutCreditCard",false);
        if(isCheckout){
            deleteCard.setVisibility(View.INVISIBLE);
        }
        else {
            deleteCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer").child(firebaseUser.getUid()).child("cardList").child(getItem(position).getId());
                    databaseReference.removeValue();
                    CreditCardFragment fragment = new CreditCardFragment();
                    switchFragment(fragment);
                }
            });
        }
        cardNumber.setText(getItem(position).getCardNumber());
        cardHolder.setText(String.valueOf(getItem(position).getCardHolder()));
        cardExpiration.setText(String.valueOf(getItem(position).getExpirationDate()));
        cardMoney.setText(String.valueOf(getItem(position).getMoney()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCheckout){
                    CreditCard creditCard=(CreditCard) getItem(position);
                    Gson gson = new Gson();
                    String json = gson.toJson(creditCard);
                    editor.putString("creditCard", json);
                    editor.apply();
                    CheckOutFragment fragment = new CheckOutFragment();
                    switchFragment(fragment);
                }else {
                    editor.putString("IdCard", getItem(position).getId());
                    editor.apply();
                    Log.e("Lala", "nnnnnnnnnnnnnnnnnnnnnnnnnnnn" + getItem(position).getCardHolder());
                    InputCreditCardFragment fragment = new InputCreditCardFragment();
                    switchFragment(fragment);
                }
            }
        });
        return convertView;
    }

    void switchFragment(Fragment fragment) {
        FragmentActivity f = (FragmentActivity) context;
        FragmentManager fragmentManager = f.getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragTransaction.addToBackStack(null);
        fragTransaction.replace(R.id.fl_wrapper, fragment);
        fragTransaction.commit();
    }
}
