package com.example.computershopsystem.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystem.Addapter.LVProductInCheckOutAdapter;
import com.example.computershopsystem.Model.CreditCard;
import com.example.computershopsystem.Model.Order;
import com.example.computershopsystem.Model.OrderProduct;
import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.CheckOutFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckOutFragment extends Fragment {
    CheckOutFragmentBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    public Product product;
    private ListView listView;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    CreditCard creditCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CheckOutFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        creditCard=new CreditCard();
        listView = binding.lvProductCheckout;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sharedpreferences = getActivity().getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        List<OrderProduct> productList = getList();
        LVProductInCheckOutAdapter LVProductInCartAdapter = new LVProductInCheckOutAdapter(getActivity(), R.layout.check_out_item, productList);
        listView.setAdapter(LVProductInCartAdapter);
        binding.tvPriceAndQuantityItemLable.setText("Price (" + String.valueOf(productList.size()) + " Products)");
        binding.tvPriceCheckOut.setText("$" + checkInt(sumInList(productList)));
        binding.tvTotalCheckOut.setText("$" + checkInt(sumInList(productList) + 20));
        binding.tvPaymentCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditCardFragment fragment = new CreditCardFragment();
                switchFragment(fragment);
            }
        });
        binding.btnOrderCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.txtNameAndPhoneCheckout.getText().toString().split("\\ - ")[0];
                String phone = binding.txtNameAndPhoneCheckout.getText().toString().split("\\ - ")[1];
                String addesss = binding.btnAddressCheckout.getText().toString();
                String shipDate = binding.tvTimeDeliveryCheckOut.getText().toString().split("\\ - ")[1]+" " + binding.tvTimeDeliveryCheckOut.getText().toString().split("\\ - ")[2];
                DateFormat dateFormat = new SimpleDateFormat("HH:mm mm/dd/yyyy");
                String orderDate=dateFormat.format(new Date());
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Customer/" + firebaseUser.getUid() + "/orderList");
                String  id=mDatabase.push().getKey();
                Order order = new Order(id, firebaseUser.getUid(), name, orderDate, null,shipDate, addesss, phone, productList,binding.tvNoteCheckOut.getText().toString(), null);

                mDatabase.child(id).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Order Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Order Fail", Toast.LENGTH_SHORT).show();
                        }
                        switchFragment(new CusHomeFragment());
                    }
                });

            }


        });
        return view;
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

    public double sumInList(List<OrderProduct> productList) {
        double sum = 0;
        for (OrderProduct item : productList) {
            sum += item.getProduct().getSellPrice() * item.getQuantityInCart();
        }
        return sum;
    }

    String checkInt(double num) {
        if ((int) num == num) return Integer.toString((int) num); //for you, StackOverflowException
        DecimalFormat df = new DecimalFormat("###.####");
        return df.format(num); //and for you, Christian Kuetbach
    }

    void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragTransaction.addToBackStack(null);
        fragTransaction.replace(R.id.fl_wrapper, fragment);
        fragTransaction.commit();
    }
}