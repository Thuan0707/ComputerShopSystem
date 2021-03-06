package com.example.computershopsystem.View;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.computershopsystem.Model.OrderProduct;
import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.Utilities.Utils;
import com.example.computershopsystem.Utilities.Variable;
import com.example.computershopsystem.databinding.ProductDetailsFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ProductDetailsFragment extends Fragment {

    private static String TAG = "testing";

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    public Product product;
    ProductDetailsFragmentBinding binding;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProductDetailsFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        Bundle bundle = getArguments();
        String productJsonString = bundle.getString(Variable.DETAIL_KEY);
        product = Utils.getGsonParser().fromJson(productJsonString, Product.class);

        Picasso.get().load(product.getImage()).resize(300,300).into(binding.ivProduct);
        binding.tvPriceProduct.setText("$"+Utils.checkInt(product.getSellPrice()));
        binding.txtNameProduct.setText(product.getName());
        binding.txtRAM.setText(product.getRam().getCapacity()+ "GB " + product.getRam().getDescription());
        binding.txtScreen.setText(product.getScreen().getSize() + " " + product.getScreen().getDescription());

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser == null) {
                    startActivity(new Intent(getActivity(), LoginActiveActivity.class));
                    getActivity().finish();
                }else{
                    Toast.makeText(getContext(), "This Product is added to cart" , Toast.LENGTH_SHORT).show();
                    sharedpreferences = getActivity().getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
                    editor = sharedpreferences.edit();
                    List<OrderProduct> listProductInCart = getList();
                    IncreaseQuantityCartProduct(listProductInCart,product);
                    setList("cart", listProductInCart);
                }

            }
        });

        return view;
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
    public List<OrderProduct> getList(){
        List<OrderProduct> listProduct=new ArrayList<>();
        String serializedObject = sharedpreferences.getString("cart", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<OrderProduct>>(){}.getType();
            listProduct = gson.fromJson(serializedObject, type);
        }
        return  listProduct;
    }
    public void IncreaseQuantityCartProduct(List<OrderProduct> listProductInCart, Product product) {
        for (OrderProduct item : listProductInCart) {
            if (item.getProduct().getId().equalsIgnoreCase(product.getId())) {
              item.setQuantity(item.getQuantity()+1);
              return;
            }
        }
        listProductInCart.add(new OrderProduct(1,product));
    }

//    private String moneyFormat(double money) {
//
//        String[] splitStr = money.split("\\a");
//        String result = "";
//        int n = 0;
//        for (int i = splitStr.length; i >=0; i--){
//            n++;
//            if (n%3 == 0) {
//                result = "." + splitStr[i] + result;
//            } else {
//                result = splitStr[i] + result;
//            }
//        }
//        return result;
//    }


}