package com.example.computershopsystemadmin.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Model.Order;
import com.example.computershopsystemadmin.Model.OrderProduct;

import java.text.DecimalFormat;
import java.util.List;

public class LVOrderHistoryAdapter extends ArrayAdapter<Order> {

    private Context context;
    private int resource;
    private List<Order> objects;

    //Constructor
    public LVOrderHistoryAdapter(@NonNull Context context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    //Show data to the screen
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        TextView code = convertView.findViewById(R.id.tvCodeOrder);
        TextView price = convertView.findViewById(R.id.tvPriceOrder);
        TextView quantity = convertView.findViewById(R.id.tvQuantityOrder);
        TextView date = convertView.findViewById(R.id.tvDateOrder);

        //Set data for item
        code.setText(getItem(position).getId());
        price.setText("$"+checkInt(Total(getItem(position))));
        quantity.setText(NumOfProduct(getItem(position)) + " Items");
        date.setText(getItem(position).getOrderDate());
        return convertView;
    }


    //Check number is double or integer
    String checkInt(double num) {
        if ((int) num == num) return Integer.toString((int) num);
        DecimalFormat df = new DecimalFormat("###.####");
        return df.format(num);
    }

    //Return number of product
    String NumOfProduct(Order order) {
        int num = 0;
        for (OrderProduct orderProduct : order.getOrderProductList()) {
            num += orderProduct.getQuantity();
        }
        return String.valueOf(num);
    }

    //Calculate total money of all order
    double Total(Order order) {
        double total = 0;
        for (OrderProduct orderProduct : order.getOrderProductList()) {
            total += orderProduct.getQuantity() * orderProduct.getProduct().getSellPrice();
        }
        return total;
    }
}
