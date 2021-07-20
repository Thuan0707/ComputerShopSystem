package com.example.computershopsystemadmin.Controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.computershopsystemadmin.Model.Product;
import com.example.computershopsystemadmin.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class GridProductAdapter extends BaseAdapter {

    Context context;
    ArrayList<Product> listProduct;

    LayoutInflater layoutInflater;

    //Constructor
    public GridProductAdapter(Context context, ArrayList<Product> listProduct) {
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

    //Show view to screen
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.grid_item, null);
        ImageView imageView = convertView.findViewById(R.id.grid_image);
        TextView name = convertView.findViewById(R.id.name_product);
        TextView brand = convertView.findViewById(R.id.brand_product);
        TextView price = convertView.findViewById(R.id.price_product);
        //Load information
        name.setText(listProduct.get(position).getName());
        brand.setText(listProduct.get(position).getBrand().getName());
        price.setText("$" + checkInt(listProduct.get(position).getSellPrice()));
        //Load image
        Picasso.get().load(listProduct.get(position).getImage()).resize(300, 300).into(imageView);
        notifyDataSetChanged();
        return convertView;
    }

    //Check number is integer or double
    String checkInt(double num) {
        if ((int) num == num) return Integer.toString((int) num); //for you, StackOverflowException
        DecimalFormat df = new DecimalFormat("###.####");
        return df.format(num); //and for you, Christian Kuetbach
    }

}
