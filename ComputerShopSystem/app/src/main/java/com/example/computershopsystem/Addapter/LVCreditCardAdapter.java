package com.example.computershopsystem.Addapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.computershopsystem.Model.CreditCard;
import com.example.computershopsystem.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LVCreditCardAdapter  extends ArrayAdapter<CreditCard> {
    private Context context;
    private int resource;

    public LVCreditCardAdapter(@NonNull Context context, int resource, @NonNull List<CreditCard> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);
        TextView cardNumber = convertView.findViewById(R.id.txtCardNumber);
        TextView cardHolder = convertView.findViewById(R.id.txtCardHolder);
        TextView cardExpiration = convertView.findViewById(R.id.txtCardExpiration);
        TextView cardMoney = convertView.findViewById(R.id.txtCardMoney);
        cardNumber.setText(getItem(position).getCardNumber());
        cardHolder.setText(String.valueOf(getItem(position).getCardHolder()));
        cardExpiration.setText(String.valueOf(DateFormat.format("MM/dd/yyy",getItem(position).getExpirationDate())));
        cardMoney.setText(String.valueOf(getItem(position).getMoney()));

        return convertView;
    }
}
