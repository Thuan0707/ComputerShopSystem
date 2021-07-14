package com.example.computershopsystemadmin.Addapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.computershopsystemadmin.Model.Voucher;
import com.example.computershopsystemadmin.R;

import java.text.DecimalFormat;
import java.util.List;

public class LVVoucherAdapter  extends ArrayAdapter<Voucher> {
    private Context context;
    private int resource;

    public LVVoucherAdapter(@NonNull Context context, int resource, @NonNull List<Voucher> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
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

        return convertView;
    }
    String checkInt(double num) {
        if ((int) num == num) return Integer.toString((int) num); //for you, StackOverflowException
        DecimalFormat df = new DecimalFormat("###.####");
        return df.format(num); //and for you, Christian Kuetbach
    }
}
