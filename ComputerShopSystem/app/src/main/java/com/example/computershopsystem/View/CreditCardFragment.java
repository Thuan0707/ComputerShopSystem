package com.example.computershopsystem.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.computershopsystem.Addapter.LVCreditCardAdapter;
import com.example.computershopsystem.Addapter.LVProductInCartAdapter;
import com.example.computershopsystem.Model.CartProduct;
import com.example.computershopsystem.Model.CreditCard;
import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.R;

import com.example.computershopsystem.databinding.CartFragmentBinding;
import com.example.computershopsystem.databinding.CreditCardFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class CreditCardFragment extends Fragment {
    CreditCardFragmentBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ListView listView;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = CreditCardFragmentBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        listView = binding.lvCard;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sharedpreferences = getActivity().getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        List<CreditCard> productList = getList();
        LVCreditCardAdapter LVCreditCardAdapter = new LVCreditCardAdapter(getActivity(), R.layout.card_item, productList);
        listView.setAdapter(LVCreditCardAdapter);
        binding.btnGotoAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputCreditCardFragment fragment = new InputCreditCardFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
                fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                        android.R.animator.fade_out);
                fragTransaction.addToBackStack(null);
                fragTransaction.replace(R.id.fl_wrapper, fragment);
                fragTransaction.commit();
            }
        });
        return view;
    }

    public List<CreditCard> getList() {
        List<CreditCard> listProduct = new ArrayList<>();
        String serializedObject = sharedpreferences.getString("cart", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<CreditCard>>() {
            }.getType();
            listProduct = gson.fromJson(serializedObject, type);
        }
        return listProduct;
    }
}