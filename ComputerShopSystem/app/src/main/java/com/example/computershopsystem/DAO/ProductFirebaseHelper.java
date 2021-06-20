package com.example.computershopsystem.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.GridView;

import androidx.annotation.NonNull;

import com.example.computershopsystem.Addapter.GridAdapter;
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
    GridView gridView;
    GridAdapter gridAdapter;
    Context context;

    public ProductFirebaseHelper(DatabaseReference db, GridView gridView, Context context) {
        this.gridView = gridView;
        this.context = context;
        this.db = db;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
            ArrayList<Product> listProduct = new ArrayList<>();
            if (snapshot.exists()) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    Product product = shot.getValue(Product.class);
                    Log.e("nameBrand", product.getBrand().getName());
                    Log.e("name", product.getName());
                    listProduct.add(product);
                }
            }
            gridAdapter = new GridAdapter(context, listProduct);
            gridView.setAdapter(gridAdapter);
        }

        @Override
        public void onCancelled(@NonNull @NotNull DatabaseError error) {

        }
    };


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

    public ArrayList<Product> retrieveByName(String s) {
        Query query = db.orderByChild("name").equalTo(s);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }

    public ArrayList<Product> retrieveByBrand(String s) {
        Query query = db.orderByChild("brand/name").equalTo(s);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }
    public ArrayList<Product> retrieveByLowPrice() {
        Query query = db.orderByChild("sellPrice").endAt(500).startAt(200);
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

