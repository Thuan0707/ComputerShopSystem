package com.example.computershopsystem.DAO;

import com.example.computershopsystem.Model.Product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ProductFirebaseHelper {


    DatabaseReference db;
    Boolean saved = null;
    ArrayList<Product> list = new ArrayList<>();

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

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return list;
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        list.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Product product = ds.getValue(Product.class);
            list.add(product);
        }
    }

}

