package com.example.computershopsystemadmin.DAO;

import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.computershopsystemadmin.Controller.LVOrderHistoryAdapter;
import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.Model.Order;
import com.example.computershopsystemadmin.Model.OrderProduct;
import com.example.computershopsystemadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderHistoryFirebaseHelper {

    DatabaseReference db;
    Context context;
    Customer customer;

    public OrderHistoryFirebaseHelper(DatabaseReference db, Context context) {
        this.context = context;
        this.db = db;
    }


    public void getList(ListView lv) {
        ArrayList<Order> orderList = new ArrayList<>();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot shot : dataSnapshot.getChildren()) {
                        Order order = shot.getValue(Order.class);
                        orderList.add(order);
                    }
                }


                LVOrderHistoryAdapter lvOrderHistoryAdapter = new LVOrderHistoryAdapter(context, R.layout.orderhistory_item, orderList);
                lv.setAdapter(lvOrderHistoryAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    /**
     * setText total Customer's order of CPSS
     * @param textView
     */
    public void TotalOrder(TextView textView) {

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Customer> listCustomer = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot shot : dataSnapshot.getChildren()) {
                        Customer customer = shot.getValue(Customer.class);

                        listCustomer.add(customer);
                    }
                    textView.setText(GetTotalOrder(listCustomer));
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    /**
     * calculate total order by orderList.size
     * @param customerList
     * @return
     */
    String GetTotalOrder(List<Customer> customerList) {
        int total = 0;
        for (Customer customer : customerList) {
            HashMap<String, Order> orderList = customer.getOrderList();
            if (orderList != null) {
                total += orderList.size();
            }
        }
        return String.valueOf(total);
    }
}
