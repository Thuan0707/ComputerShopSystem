package com.example.computershopsystem.Addapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

import com.example.computershopsystem.Model.OrderProduct;
import com.example.computershopsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LVProductInCartAdapter extends ArrayAdapter<OrderProduct> {
    private Context context;
    private int resource;
    private List<OrderProduct> objects;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public LVProductInCartAdapter(@NonNull Context context, int resource, @NonNull List<OrderProduct> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
         this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        ImageView image = convertView.findViewById(R.id.imageCartProduct);
        TextView name = convertView.findViewById(R.id.txtNameCartProduct);
        TextView price = convertView.findViewById(R.id.txtPriceCartProduct);
        EditText quantity = convertView.findViewById(R.id.txtQuantityCartProduct);
        name.setText(getItem(position).getProduct().getName());
        price.setText(checkInt(getItem(position).getProduct().getSellPrice()));
        quantity.setText(String.valueOf(getItem(position).getQuantityInCart()));
        Picasso.get().load(getItem(position).getProduct().getImage()).into(image);
        AppCompatImageButton increaseQuantity=convertView.findViewById(R.id.ibtnIncreaseQuantity);
        AppCompatImageButton decreaseQuantity=convertView.findViewById(R.id.ibtnDecreaseQuantity);
        AppCompatImageButton remove=convertView.findViewById(R.id.ibtnDeleteCartProduct);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sharedpreferences = context.getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity.setText(String.valueOf(Integer.parseInt(quantity.getText().toString())+1));
                List<OrderProduct> listProduct=getList();
                listProduct.get(position).setQuantityInCart(Integer.parseInt(quantity.getText().toString()));
                setList("cart",listProduct);
            }
        });
        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity.setText(String.valueOf(Integer.parseInt(quantity.getText().toString())-1));
                List<OrderProduct> listProduct=getList();
                listProduct.get(position).setQuantityInCart(Integer.parseInt(quantity.getText().toString()));
                setList("cart",listProduct);
            }
        });
        quantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                List<OrderProduct> listProduct=getList();
                listProduct.get(position).setQuantityInCart(Integer.parseInt(quantity.getText().toString()));
                setList("cart",listProduct);
                return true;
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<OrderProduct> listProduct=getList();
                listProduct.remove(position);
                setList("cart",listProduct);
                objects.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        update(key, json);
    }

    public void update(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }
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
    String checkInt(double num) {
        if ((int) num == num) return Integer.toString((int) num); //for you, StackOverflowException
        DecimalFormat df = new DecimalFormat("###.####");
        return df.format(num); //and for you, Christian Kuetbach
    }
}
