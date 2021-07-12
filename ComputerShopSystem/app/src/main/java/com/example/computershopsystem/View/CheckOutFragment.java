package com.example.computershopsystem.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystem.Addapter.LVProductInCheckOutAdapter;
import com.example.computershopsystem.Model.CartProduct;
import com.example.computershopsystem.Model.CreditCard;
import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.CheckOutFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
        listView = binding.lvProductCheckout;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sharedpreferences = getActivity().getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        List<CartProduct> productList = getList();
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
        return view;
    }

    public List<CartProduct> getList() {
        List<CartProduct> listProduct = new ArrayList<>();
        String serializedObject = sharedpreferences.getString("cart", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<CartProduct>>() {
            }.getType();
            listProduct = gson.fromJson(serializedObject, type);
        }
        return listProduct;
    }

    public double sumInList(List<CartProduct> productList) {
        double sum = 0;
        for (CartProduct item : productList) {
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