package com.example.computershopsystemadmin.Addapter;

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

import com.example.computershopsystemadmin.Model.CreditCard;
import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.Model.OrderProduct;
import com.example.computershopsystemadmin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LVCustomerAdapter  extends ArrayAdapter<Customer> {
    private Context context;
    private int resource;
    private List<Customer> objects;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public LVCustomerAdapter(@NonNull Context context, int resource, @NonNull List<Customer> objects) {
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
        ImageView image = convertView.findViewById(R.id.image);
        TextView name = convertView.findViewById(R.id.txtName);
        TextView phone = convertView.findViewById(R.id.txtPhone);
        TextView status = convertView.findViewById(R.id.txtStatus);
        name.setText(getItem(position).getFullName());
        phone.setText(getItem(position).getCustomerAccount().getPhone());
        if (getItem(position).getImage()!=null){
            Picasso.get().load(getItem(position).getImage()).into(image);
        }
        if (getItem(position).getDeleteAt()!=null){
            status.setText("Disable");
        }else{
            status.setText("Enable");
        }

        return convertView;
    }

}
