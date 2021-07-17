package com.example.computershopsystem.DAO;

import android.content.Context;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.computershopsystem.Addapter.LVOrderHistoryAdapter;
import com.example.computershopsystem.Model.Customer;
import com.example.computershopsystem.Model.Order;
import com.example.computershopsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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
}
