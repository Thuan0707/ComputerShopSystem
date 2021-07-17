package com.example.computershopsystem.Addapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.computershopsystem.Model.Order;
import com.example.computershopsystem.Model.OrderProduct;
import com.example.computershopsystem.R;

import java.text.DecimalFormat;
import java.util.List;

public class LVOrderHistoryAdapter extends ArrayAdapter<Order> {

    private Context context;
    private int resource;
    private List<Order> objects;

    public LVOrderHistoryAdapter(@NonNull Context context, int resource, @NonNull List<Order> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        TextView code = convertView.findViewById(R.id.tvCodeOrder);
        TextView price = convertView.findViewById(R.id.tvPriceOrder);
        TextView quantity = convertView.findViewById(R.id.tvQuantityOrder);
        TextView date = convertView.findViewById(R.id.tvDateOrder);
        code.setText(getItem(position).getId());
        price.setText("$"+checkInt(Total(getItem(position))));
        quantity.setText(NumOfProduct(getItem(position)) + " Items");
        date.setText(getItem(position).getOrderDate());
        return convertView;
    }

    String checkInt(double num) {
        if ((int) num == num) return Integer.toString((int) num); //for you, StackOverflowException
        DecimalFormat df = new DecimalFormat("###.####");
        return df.format(num); //and for you, Christian Kuetbach
    }

    String NumOfProduct(Order order) {
        int num = 0;
        for (OrderProduct orderProduct : order.getOrderProductList()) {
            num += orderProduct.getQuantityInCart();
        }
        return String.valueOf(num);
    }

    double Total(Order order) {
        double total = 0;
        for (OrderProduct orderProduct : order.getOrderProductList()) {
            total += orderProduct.getQuantityInCart() * orderProduct.getProduct().getSellPrice();
        }
        return total;
    }
}
