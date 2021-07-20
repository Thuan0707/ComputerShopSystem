package com.example.computershopsystemadmin.DAO;

import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.computershopsystemadmin.Controller.LVCustomerAdapter;
import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.Model.Order;
import com.example.computershopsystemadmin.Model.OrderProduct;
import com.example.computershopsystemadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerFirebaseHelper {
    DatabaseReference db;
    Boolean saved = null;
    ArrayList<Customer> list = new ArrayList<>();
    ListView listView;
    LVCustomerAdapter lvCustomerAdapter;
    Context context;

    //Constructor
    public CustomerFirebaseHelper(DatabaseReference db, ListView gridView, Context context) {
        this.listView = gridView;
        this.context = context;
        this.db = db;
    }
    //Constructor
    public CustomerFirebaseHelper(DatabaseReference db, Context context) {

        this.context = context;
        this.db = db;
    }
    //get all product in DB and show to screen
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

      //Calculte profit
    public void profit(TextView textView) {

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Customer> listCustomer = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot shot : dataSnapshot.getChildren()) {
                        Customer customer = shot.getValue(Customer.class);

                        listCustomer.add(customer);
                    }
                    textView.setText(caculateProfit(listCustomer));
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    /**
     * setText total Customer by get size of list
     * @param textView
     */
    public void totalCustomer(TextView textView) {

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Customer> listCustomer = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot shot : dataSnapshot.getChildren()) {
                        Customer customer = shot.getValue(Customer.class);

                        listCustomer.add(customer);
                    }
                textView.setText(String.valueOf(listCustomer.size()));
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    public void totalSoldProduct(TextView textView) {

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Customer> listCustomer = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot shot : dataSnapshot.getChildren()) {
                        Customer customer = shot.getValue(Customer.class);

                        listCustomer.add(customer);
                    }
                    textView.setText(TotalProduct(listCustomer));
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    /**
     * calculate total profit of CPSS
     * @param customerList
     * @return
     */
    String caculateProfit(List<Customer> customerList) {
        double profit = 0;
        for (Customer customer : customerList) {
            HashMap<String, Order> orderList = customer.getOrderList();
            if (orderList != null) {
                for (Order order : orderList.values()) {
                    List<OrderProduct> orderProductList = order.getOrderProductList();
                    for (OrderProduct orderProduct: orderProductList) {
                        profit+=(orderProduct.getProduct().getSellPrice()-orderProduct.getProduct().getBuyPrice())*orderProduct.getQuantity();
                    }
                }
            }
        }
        return "$"+String.valueOf(profit);
    }

    /**
     * calculate total product
     * @param customerList
     * @return
     */
    String TotalProduct(List<Customer> customerList) {
        int total = 0;
        for (Customer customer : customerList) {
            HashMap<String, Order> orderList = customer.getOrderList();
            if (orderList != null) {
                for (Order order : orderList.values()) {
                    List<OrderProduct> orderProductList = order.getOrderProductList();
                    for (OrderProduct orderProduct: orderProductList) {
                        total+=orderProduct.getQuantity();
                    }
                }
            }
        }
        return String.valueOf(total);
    }
    //RETRIEVE
    public ArrayList<Customer> retrieve() {
        db.addValueEventListener(valueEventListener);
        return list;
    }

    /**
     * Get list customer
     * @return
     */
    public ArrayList<Customer> getList() {
        return list;
    }

    public void setList(ArrayList<Customer> list) {
        this.list = list;
    }
}
