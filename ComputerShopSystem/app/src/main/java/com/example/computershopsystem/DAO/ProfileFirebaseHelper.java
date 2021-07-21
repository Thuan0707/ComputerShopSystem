package com.example.computershopsystem.DAO;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.computershopsystem.Model.Customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ProfileFirebaseHelper {
    DatabaseReference db;
    Context context;
    Customer customer;

    public ProfileFirebaseHelper(DatabaseReference db, Context context) {
        this.context = context;
        this.db = db;
    }

    public Customer loadACustomer(String id, TextView birth, TextView gender, TextView fullName, ImageView iv) {
        Query query = db.child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    customer = snapshot.getValue(Customer.class);
                    if (customer.getDateOfBirth()!=null){
                        birth.setText(customer.getDateOfBirth());
                    }
                    if (customer.getFullName()!=null){
                        fullName.setText(customer.getFullName());
                    }
                    if (customer.getImage() != null) {

                        Picasso.get().load(customer.getImage()).into(iv);
                    }
                    String strGender=null;
                    switch (customer.getGender()){
                        case 1:
                            strGender="Male";
                            break;
                        case 0:
                            strGender="Female";
                            break;
                        case 2:
                            strGender="Other";
                            break;
                    }
                    gender.setText(strGender);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return customer;
    }

    public Customer loadCheckoutCustomer(String id, TextView fullName, TextView Address, String numberphone) {
        Query query = db.child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    customer = snapshot.getValue(Customer.class);

                    if (customer.getFullName()!=null){
                        fullName.setText(customer.getFullName() + " - " + changeNumberPhone(numberphone));
                    }

                    if (customer.getAddress() != null) {
                        Address.setText(customer.getAddress());
                    } else {
                        Address.setText(null);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return customer;
    }



    public String changeNumberPhone(String phone) {
        String[] spl = phone.split("[+84]");

        StringBuilder builder = new StringBuilder();
        for (String item: spl) {
            builder.append(item);
        }
        return "0" + builder.toString();
    }

}