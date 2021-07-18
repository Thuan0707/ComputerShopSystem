package com.example.computershopsystemadmin.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystemadmin.Model.Voucher;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.View.CreditCardFragment;
import com.example.computershopsystemadmin.View.InputVoucherFragment;
import com.example.computershopsystemadmin.View.VoucherFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.List;

public class LVVoucherAdapter  extends ArrayAdapter<Voucher> {
    private Context context;
    private int resource;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    public LVVoucherAdapter(@NonNull Context context, int resource, @NonNull List<Voucher> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        sharedpreferences = getContext().getSharedPreferences(FirebaseAuth.getInstance().getCurrentUser().getUid(), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        TextView code = convertView.findViewById(R.id.tvVoucherCode);
        TextView des = convertView.findViewById(R.id.tvVoucherDescription);
        TextView discount = convertView.findViewById(R.id.tvVoucherDiscount);
        TextView name = convertView.findViewById(R.id.tvVoucherName);
        name.setText(getItem(position).getName());
        code.setText(String.valueOf(getItem(position).getCode()));
        discount.setText("$"+checkInt(getItem(position).getDiscount()));
        des.setText(String.valueOf(getItem(position).getDescription()));

        ImageButton deleteCVoucher = convertView.findViewById(R.id.ibtnDeleteVoucher);

        deleteCVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Voucher").child(getItem(position).getId());
                databaseReference.removeValue();
                VoucherFragment fragment = new VoucherFragment();
                switchFragment(fragment);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Voucher voucher=(Voucher)getItem(position);

                Gson gson = new Gson();
                String json = gson.toJson(voucher);
                editor.putString("voucher", json);
                editor.apply();
                InputVoucherFragment fragment = new InputVoucherFragment();
                switchFragment(fragment);
            }
        });
        return convertView;
    }
    String checkInt(double num) {
        if ((int) num == num) return Integer.toString((int) num); //for you, StackOverflowException
        DecimalFormat df = new DecimalFormat("###.####");
        return df.format(num); //and for you, Christian Kuetbach
    }

    void switchFragment(Fragment fragment) {
        FragmentActivity fgActivity = (FragmentActivity) context;
        FragmentManager fragmentManager = fgActivity.getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragTransaction.addToBackStack(null);
        fragTransaction.replace(R.id.fl_wrapper, fragment);
        fragTransaction.commit();
    }
}
