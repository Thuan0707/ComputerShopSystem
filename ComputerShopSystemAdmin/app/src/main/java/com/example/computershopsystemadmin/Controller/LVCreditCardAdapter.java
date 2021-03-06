package com.example.computershopsystemadmin.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.computershopsystemadmin.Model.CreditCard;
import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.View.CheckOutFragment;
import com.example.computershopsystemadmin.View.CreditCardFragment;
import com.example.computershopsystemadmin.View.InputCreditCardFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.List;

public class LVCreditCardAdapter  extends ArrayAdapter<CreditCard> {
    private Context context;
    private int resource;
    Customer customer;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    //Constructor
    public LVCreditCardAdapter(@NonNull Context context, int resource, @NonNull List<CreditCard> objects, Customer customer) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.customer = customer;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //if user is null, create sharedprefernces
        if (firebaseUser!=null){
            sharedpreferences = getContext().getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
            editor = sharedpreferences.edit();
        }
    }

//Show view to screen
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

        //Delete the card
        deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get path to card in DB
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer").child(customer.getId()).child("cardList").child(getItem(position).getId());
                databaseReference.removeValue();
                //go to show card view
                CreditCardFragment fragment = new CreditCardFragment();
                switchFragment(fragment);
            }
        });
        //Set information card
        cardNumber.setText(getItem(position).getCardNumber());
        cardHolder.setText(String.valueOf(getItem(position).getCardHolder()));
        cardExpiration.setText(String.valueOf(getItem(position).getExpirationDate()));
        cardMoney.setText(String.valueOf(getItem(position).getMoney()));

        //when click to card, go to input card fragment
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("IdCard", getItem(position).getId());
                editor.apply();
                InputCreditCardFragment fragment = new InputCreditCardFragment();
                switchFragment(fragment);

            }
        });
        return convertView;
    }

    //Change fragment
    void switchFragment(Fragment fragment) {
        Bundle bun=new Bundle();
        bun.putSerializable("customer",customer);
        fragment.setArguments(bun);
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
