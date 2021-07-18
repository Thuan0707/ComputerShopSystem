package com.example.computershopsystemadmin.DAO;

import android.content.Context;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.computershopsystemadmin.Controller.LVCustomerAdapter;
import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CustomerFirebaseHelper {
    DatabaseReference db;
    Boolean saved = null;
    ArrayList<Customer> list = new ArrayList<>();
    ListView listView;
    LVCustomerAdapter lvCustomerAdapter;
    Context context;

    public CustomerFirebaseHelper(DatabaseReference db, ListView gridView, Context context) {
        this.listView = gridView;
        this.context = context;
        this.db = db;
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
            ArrayList<Customer> listCustomer = new ArrayList<>();
            if (snapshot.exists()) {
                for (DataSnapshot shot : snapshot.getChildren()) {
                    Customer customer = shot.getValue(Customer.class);

                    listCustomer.add(customer);
                }
            }
            lvCustomerAdapter = new LVCustomerAdapter(context, R.layout.customer_item, listCustomer);
            listView.setAdapter(lvCustomerAdapter);
        }

        @Override
        public void onCancelled(@NonNull @NotNull DatabaseError error) {

        }
    };





    //RETRIEVE
    public ArrayList<Customer> retrieve() {
        db.addValueEventListener(valueEventListener);
        return list;
    }

//    public ArrayList<Product> retrieveByName(String s) {
//        Query query = db.orderByChild("name");
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                ArrayList<Product> listProduct = new ArrayList<>();
//                if (snapshot.exists()) {
//                    for (DataSnapshot shot : snapshot.getChildren()) {
//                        if(shot.getValue(Product.class).getName().toLowerCase().contains(s.toLowerCase())){
//                            Product product = shot.getValue(Product.class);
//                            listProduct.add(product);
//                        }
//                    }
//                }
//                gridProductAdapter = new GridProductAdapter(context, listProduct);
//                gridView.setAdapter(gridProductAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//        return list;
//    }

    public ArrayList<Customer>  retrieveByBrand(String s) {
        Query query = db.orderByChild("brand/name").equalTo(s);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }
    public ArrayList<Customer> retrieveByLowPrice() {
        Query query = db.orderByChild("sellPrice").endAt(500).startAt(200);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }
    public ArrayList<Customer>  retrieveByMediumPrice() {
        Query query = db.orderByChild("sellPrice").endAt(1000).startAt(500);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }

    public ArrayList<Customer> retrieveByHighPrice() {
        Query query = db.orderByChild("sellPrice").startAt(1000);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }

    public ArrayList<Customer> retrieveByRam(int ram) {
        Query query = db.orderByChild("ram/capacity").equalTo(ram);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }

    public ArrayList<Customer>  retrieveByRom(String rom) {
        Query query = db.orderByChild("rom/capacity").equalTo(rom);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }

    public ArrayList<Customer>  retrieveByScreenSize(String size) {
        Query query = db.orderByChild("screen/size").equalTo(size);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }

    public ArrayList<Customer>  retrieveBySPU(String num) {
        Query query = db.orderByChild("cpu/series").equalTo("i"+num);
        query.addListenerForSingleValueEvent(valueEventListener);
        return list;
    }
    public ArrayList<Customer> getList() {
        return list;
    }

    public void setList(ArrayList<Customer>  list) {
        this.list = list;
    }
}
