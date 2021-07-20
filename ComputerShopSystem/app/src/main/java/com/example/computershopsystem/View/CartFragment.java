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

import com.example.computershopsystem.Controller.LVProductInCartAdapter;
import com.example.computershopsystem.Model.OrderProduct;
import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.R;
import com.example.computershopsystem.databinding.CartFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    public Product product;
    private ListView listView;
    CartFragmentBinding binding;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CartFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        listView = binding.listCart;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sharedpreferences = getActivity().getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        List<OrderProduct> productList = getList();
        if (productList.size()==0){
            switchFragment(new NotFoundFragment());
        }
        LVProductInCartAdapter LVProductInCartAdapter = new LVProductInCartAdapter(getActivity(), R.layout.cart_item, productList);
        listView.setAdapter(LVProductInCartAdapter);
        binding.txtItemQuantity.setText(String.valueOf(productList.size()));
        binding.txtPriceItem.setText("$"+checkInt(sumInList(productList)));
        binding.txtTotalPrice.setText("$"+checkInt(sumInList(productList) + 20));
        binding.btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckOutFragment fragment=new CheckOutFragment();
                switchFragment(fragment);
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
            sum += item.getProduct().getSellPrice() * item.getQuantity();
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
