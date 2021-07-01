package com.example.computershopsystem.Addapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.R;
import com.squareup.picasso.Picasso;

import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {
    public static Object filter;
    Context context;
    static ArrayList<Product> listProduct;
    static ArrayList<Product> listProductOld;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, ArrayList<Product> listProduct) {
        this.context = context;
        this.listProduct = listProduct;
        this.listProductOld = new ArrayList<>();
        listProductOld.addAll(listProduct);
    }

    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
        price.setText(String.valueOf(listProduct.get(position).getSellPrice()));
        Picasso.get().load(listProduct.get(position).getImage()).into(imageView);
        notifyDataSetChanged();
        return convertView;
    }
//
//    public static void filter(CharSequence charSequence){
//        ArrayList<Product> tempArrayList = new ArrayList<>();
//        if (!TextUtils.isEmpty(charSequence)){
//            for (Product pro: listProduct){
//                if (pro.getName().toLowerCase().contains(charSequence)){
//                    tempArrayList.add(pro);
//                }
//            }
//        }else{
//            listProduct.addAll(listProductOld);
//        }
//        listProduct.clear();
//        listProduct.addAll(tempArrayList);
//        //notifyDataSetChanged();
//        tempArrayList.clear();
//    }
}
