package com.example.computershopsystem.Addapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.computershopsystem.Model.CartProduct;
import com.example.computershopsystem.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LVProductInCartAdapter extends ArrayAdapter<CartProduct> {
    private Context context;
    private int resource;

    public LVProductInCartAdapter(@NonNull Context context, int resource, @NonNull List<CartProduct> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        ImageView image = convertView.findViewById(R.id.imageCartProduct);
        TextView name = convertView.findViewById(R.id.txtNameCartProduct);
        TextView price = convertView.findViewById(R.id.txtPriceCartProduct);
        TextView quantity = convertView.findViewById(R.id.txtQuantityCartProduct);
        name.setText(getItem(position).getProduct().getName());
        price.setText(String.valueOf(getItem(position).getProduct().getSellPrice()));
        quantity.setText(String.valueOf(getItem(position).getQuantityInCart()));
        Picasso.get().load(getItem(position).getProduct().getImage()).into(image);

        return convertView;
    }
}
