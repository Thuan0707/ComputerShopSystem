package com.example.computershopsystemadmin.DAO;

import android.content.Context;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.computershopsystemadmin.Addapter.LVVoucherAdapter;
import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.Model.Voucher;
import com.example.computershopsystemadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VoucherFirebaseHelper {
    DatabaseReference db;
    Context context;
    Customer customer;

    public VoucherFirebaseHelper(DatabaseReference db, Context context) {
        this.context = context;
        this.db = db;
    }

    public void getList(ListView lv) {
        ArrayList<Voucher> voucherList = new ArrayList<>();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot shot : dataSnapshot.getChildren()) {
                        Voucher voucher = shot.getValue(Voucher.class);
                        voucherList.add(voucher);
                    }
                }


                 LVVoucherAdapter lvVoucherAdapter = new LVVoucherAdapter(context, R.layout.voucher_item, voucherList);
                lv.setAdapter(lvVoucherAdapter);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    }

