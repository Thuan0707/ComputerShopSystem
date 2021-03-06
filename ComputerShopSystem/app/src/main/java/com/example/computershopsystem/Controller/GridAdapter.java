package com.example.computershopsystem.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class GridAdapter extends BaseAdapter {

    Context context;
    ArrayList<Product> listProduct;

    LayoutInflater layoutInflater;

    public GridAdapter(Context context, ArrayList<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;


    }

    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int position) {
        Object product = listProduct.get(position);
        return product;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.grid_item, null);
        ImageView imageView = convertView.findViewById(R.id.grid_image);
        TextView name = convertView.findViewById(R.id.name_product);
        TextView brand = convertView.findViewById(R.id.brand_product);
        TextView price = convertView.findViewById(R.id.price_product);
        name.setText(listProduct.get(position).getName());
        brand.setText(listProduct.get(position).getBrand().getName());
        price.setText("$"+checkInt(listProduct.get(position).getSellPrice()));
        Picasso.get().load(listProduct.get(position).getImage()).resize(300,300).into(imageView);
        notifyDataSetChanged();
        return convertView;
    }

    String checkInt(double num) {
        if ((int) num == num) return Integer.toString((int) num); //for you, StackOverflowException
        DecimalFormat df = new DecimalFormat("###.####");
        return df.format(num); //and for you, Christian Kuetbach
    }

}
