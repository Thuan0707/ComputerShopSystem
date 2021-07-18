package com.example.computershopsystemadmin.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.GridView;

import androidx.annotation.NonNull;


import com.example.computershopsystemadmin.Controller.GridProductAdapter;
import com.example.computershopsystemadmin.Model.Product;
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
    GridProductAdapter gridProductAdapter;
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
                    Log.e("n    ame", product.getName());
                    listProduct.add(product);
                }
            }
            gridProductAdapter = new GridProductAdapter(context, listProduct);
            gridView.setAdapter(gridProductAdapter);
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
        Query query = db.orderByChild("name");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<Product> listProduct = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot shot : snapshot.getChildren()) {
                        if(shot.getValue(Product.class).getName().toLowerCase().contains(s.toLowerCase())){
                            Product product = shot.getValue(Product.class);
                            listProduct.add(product);
                        }
                    }
                }
                gridProductAdapter = new GridProductAdapter(context, listProduct);
                gridView.setAdapter(gridProductAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
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
    public ArrayList<Product> retrieveByMediumPrice() {
        Query query = db.orderByChild("sellPrice").endAt(1000).startAt(500);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }

    public ArrayList<Product> retrieveByHighPrice() {
        Query query = db.orderByChild("sellPrice").startAt(1000);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }

    public ArrayList<Product> retrieveByRam(int ram) {
        Query query = db.orderByChild("ram/capacity").equalTo(ram);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }

    public ArrayList<Product> retrieveByRom(String rom) {
        Query query = db.orderByChild("rom/capacity").equalTo(rom);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }

    public ArrayList<Product> retrieveByScreenSize(String size) {
        Query query = db.orderByChild("screen/size").equalTo(size);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }

    public ArrayList<Product> retrieveBySPU(String num) {
        Query query = db.orderByChild("cpu/series").equalTo("i"+num);
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

