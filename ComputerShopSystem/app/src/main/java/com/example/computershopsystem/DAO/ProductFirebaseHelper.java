package com.example.computershopsystem.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.computershopsystem.Model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductFirebaseHelper {
    DatabaseReference db;
    Boolean saved = null;
    ArrayList<Product> list = new ArrayList<>();
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
            list.clear();
            if (snapshot.exists()) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    Product product = shot.getValue(Product.class);

                    list.add(product);
                }
            }
            Log.e("list",String.valueOf(list.size()));
        }

        @Override
        public void onCancelled(@NonNull @NotNull DatabaseError error) {

        }
    };

    public ProductFirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    public Boolean save(Product Product) {
        if (Product == null) {
            saved = false;
        } else {
            try {
                db.child("Product").push().setValue(Product);
                saved = true;

            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }
        }

        return saved;

    }

    //RETRIEVE
    public ArrayList<Product> retrieve() {
        db.addValueEventListener(valueEventListener);
        return list;
    }

    public ArrayList<Product> retrieveByName(String  s) {
        Query query= db.orderByChild("name").equalTo(s);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }
    public ArrayList<Product> retrieveByBrand(String  s) {
        Query query= db.orderByChild("brand/name").equalTo(s);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }

    public ArrayList<Product> getList() {
        return list;
    }

    public void setList(ArrayList<Product> list) {
        this.list = list;
    }
}

