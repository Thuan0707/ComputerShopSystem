package com.example.computershopsystemadmin.DAO;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.computershopsystemadmin.Model.Admin;
import com.example.computershopsystemadmin.Model.Customer;
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
    Admin admin;

    public ProfileFirebaseHelper(DatabaseReference db, Context context) {
        this.context = context;
        this.db = db;
    }

    public Customer loadACustomer(String id, TextView birth, TextView gender) {
        Query query = db.child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    customer = snapshot.getValue(Customer.class);
                    if (customer.getDateOfBirth() != null) {
                        birth.setText(customer.getDateOfBirth().toString());
                    }

                    String strGender = null;
                    switch (customer.getGender()) {
                        case 1:
                            strGender = "Male";
                            break;
                        case 0:
                            strGender = "Female";
                            break;
                        case 2:
                            strGender = "Other";
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

    public Admin loadAAdmin(String id,TextView birth, TextView gender,TextView email,TextView phone,ImageView iv) {
        Query query = db.child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    admin = snapshot.getValue(Admin.class);
                    if (admin.getDateOfBirth() != null) {
                        birth.setText(admin.getDateOfBirth());
                    }
                    if (admin.getEmail() != null) {
                        email.setText(admin.getEmail());
                    }
                    if (admin.getPhone() != null) {
                        phone.setText(admin.getPhone());
                    }
                    Picasso.get().load(admin.getImage()).into(iv);
                    String strGender = null;
                    switch (admin.getGender()) {
                        case 1:
                            strGender = "Male";
                            break;
                        case 0:
                            strGender = "Female";
                            break;
                        case 2:
                            strGender = "Other";
                            break;
                    }
                    gender.setText(strGender);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return admin;
    }
}