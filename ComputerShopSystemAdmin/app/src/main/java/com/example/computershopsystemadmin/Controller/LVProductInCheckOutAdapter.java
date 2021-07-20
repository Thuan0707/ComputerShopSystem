package com.example.computershopsystemadmin.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.computershopsystemadmin.Model.OrderProduct;
import com.example.computershopsystemadmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LVProductInCheckOutAdapter extends ArrayAdapter<OrderProduct> {
    private Context context;
    private int resource;
    private List<OrderProduct> objects;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    //constructor
    public LVProductInCheckOutAdapter(@NonNull Context context, int resource, @NonNull List<OrderProduct> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects=objects;
    }

    //Set data to show to the screen
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        ImageView image = convertView.findViewById(R.id.imageCartProductCheckOut);
        TextView name = convertView.findViewById(R.id.txtNameCheckOut);
        TextView price = convertView.findViewById(R.id.txtPriceCheckOut);
        TextView quantity = convertView.findViewById(R.id.txtQuantityCheckOut);
        name.setText(getItem(position).getProduct().getName());

        //Set up data for item
        price.setText(checkInt(getItem(position).getProduct().getSellPrice()*getItem(position).getQuantity()));
        quantity.setText("x"+String.valueOf(getItem(position).getQuantity()));
        Picasso.get().load(getItem(position).getProduct().getImage()).into(image);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sharedpreferences = context.getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();



        return convertView;
    }

    //Set list in shared preference
    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        update(key, json);
    }
//update list in shared preference
    public void update(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    //get list in shared preference
    public List<OrderProduct> getList() {
        List<OrderProduct> listProduct = new ArrayList<>();
        String serializedObject = sharedpreferences.getString("cart", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<OrderProduct>>() {
            }.getType();
            listProduct = gson.fromJson(serializedObject, type);
        }
        return listProduct;
    }

    //Check number is integer or double
    String checkInt(double num) {
        if ((int) num == num) return Integer.toString((int) num);
        DecimalFormat df = new DecimalFormat("###.####");
        return df.format(num);
    }
}
