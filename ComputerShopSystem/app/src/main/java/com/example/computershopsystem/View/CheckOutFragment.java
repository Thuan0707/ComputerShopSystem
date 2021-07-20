package com.example.computershopsystem.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.example.computershopsystem.Controller.LVProductInCheckOutAdapter;
import com.example.computershopsystem.Model.CreditCard;
import com.example.computershopsystem.Model.Order;
import com.example.computershopsystem.Model.OrderProduct;
import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.Model.Voucher;
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
    Voucher voucher;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CheckOutFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sharedpreferences = getActivity().getSharedPreferences(firebaseUser.getUid(), Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String strVoucher = sharedpreferences.getString("voucher", null);
        voucher = gson.fromJson(strVoucher, Voucher.class);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            binding.tvNameAndPhoneCheckout.setText(bundle.getString("nameCheckOut") + " - " + bundle.getString("phoneCheckOut"));
            binding.tvAddressCheckout.setText(bundle.getString("addressCheckOut"));
        }
        if (voucher!=null){
            binding.tvVoucherCheckOut.setText(voucher.getCode());
            binding.tvDiscountCheckOut.setText("-$" + checkInt(voucher.getDiscount()));
        }


        binding.tvNoteCheckOut.setText(sharedpreferences.getString("note", null));
        Gson gsonw = new Gson();
        String strCreditCard = sharedpreferences.getString("creditCard", null);
        Log.e("card",strCreditCard);
        editor.remove("isCheckoutCreditCard");
        editor.apply();
        creditCard = gson.fromJson(strCreditCard,CreditCard.class);
        listView = binding.lvProductCheckout;
//
        if (creditCard!=null){
            binding.tvPaymentCheckOut.setText(creditCard.getCardNumber());
        }


        List<OrderProduct> productList = getList();
        LVProductInCheckOutAdapter LVProductInCartAdapter = new LVProductInCheckOutAdapter(getActivity(), R.layout.check_out_item, productList);
        listView.setAdapter(LVProductInCartAdapter);
        binding.tvNumberOfProduct.setText("Price (" + String.valueOf(quantityItemInList(productList)) + " Products)");
        binding.tvPriceCheckOut.setText("$" + checkInt(sumPriceInList(productList)));
        binding.tvTotalCheckOut.setText("$" + checkInt(sumPriceInList(productList) + 20 - (voucher != null ? voucher.getDiscount() : 0)));
        binding.tvPaymentCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditCardFragment fragment = new CreditCardFragment();
                editor.putBoolean("isCheckoutCreditCard", true);
                editor.apply();
                switchFragment(fragment);
            }
        });
        binding.tvVoucherCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoucherFragment fragment = new VoucherFragment();
                fragment.setArguments(bundle);
                switchFragment(fragment);
            }
        });
        binding.tvNoteCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputNoteCheckOutFragement fragment = new InputNoteCheckOutFragement();
                switchFragment(fragment);
            }
        });
        binding.btnOrderCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.tvPaymentCheckOut.getText().toString() == "" || !CheckMoneyInCreditCard(creditCard.getMoney(), binding.tvTotalCheckOut.getText().toString().trim().replace("$", ""))) {
                    binding.tvPaymentCheckOut.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    binding.tvPaymentCheckOut.setError("Please choose the card that has enough money");
                } else {
                    String name = binding.tvNameAndPhoneCheckout.getText().toString().split("\\ - ")[0];
                    String phone = binding.tvNameAndPhoneCheckout.getText().toString().split("\\ - ")[1];
                    String addesss = binding.tvAddressCheckout.getText().toString();
                    String shipDate = binding.tvTimeDeliveryCheckOut.getText().toString().split("\\ - ")[1] + " " + binding.tvTimeDeliveryCheckOut.getText().toString().split("\\ - ")[2];
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm mm/dd/yyyy");
                    String orderDate = dateFormat.format(new Date());
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Customer/" + firebaseUser.getUid() + "/orderList");
                    String id = mDatabase.push().getKey();
                    decreaseQuantityProduct(productList);
                    decreaseMoneyInCard(Double.parseDouble(binding.tvTotalCheckOut.getText().toString().trim().replace("$", "")), creditCard);
                    Order order = new Order(id, firebaseUser.getUid(), name, orderDate, null, shipDate, addesss, phone, productList, binding.tvNoteCheckOut.getText().toString(), creditCard, voucher);

                    mDatabase.child(id).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Order Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Order Fail", Toast.LENGTH_SHORT).show();
                            }
                            switchFragment(new CusHomeFragment());
                        }
                    });

                }
            }

        });
        binding.tvChangeInfoCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.tvNameAndPhoneCheckout.getText().toString().split("\\ - ")[0];
                String phone = binding.tvNameAndPhoneCheckout.getText().toString().split("\\ - ")[1];
                String address = binding.tvAddressCheckout.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("nameCheckOut", name);
                bundle.putString("phoneCheckOut", phone);
                bundle.putString("addressCheckOut", address);
                AddressCheckOutFragment fragment = new AddressCheckOutFragment();
                fragment.setArguments(bundle);
                switchFragment(fragment);
            }
        });
        return view;
    }

    boolean CheckMoneyInCreditCard(String money, String total) {
        if (Double.parseDouble(money) < Double.parseDouble(total)) {
            return false;
        }
        return true;
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

    public double sumPriceInList(List<OrderProduct> productList) {
        double sum = 0;
        for (OrderProduct item : productList) {
            sum += item.getProduct().getSellPrice() * item.getQuantity();
        }
        return sum;
    }

    public int quantityItemInList(List<OrderProduct> productList) {
        int quantity = 0;
        for (OrderProduct item : productList) {
            quantity += item.getQuantity();
        }
        return quantity;
    }

    void decreaseQuantityProduct(List<OrderProduct> orderProductList) {
        DatabaseReference mDatabase = null;
        for (OrderProduct orderProduct : orderProductList) {
            mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + orderProduct.getProduct().getId() + "/quantity");
            mDatabase.setValue(orderProduct.getProduct().getQuantity() - orderProduct.getQuantity());
        }
    }

    void decreaseMoneyInCard(double total, CreditCard creditCard) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Customer/" + firebaseUser.getUid() + "/cardList/" + creditCard.getId() + "/money");
        mDatabase.setValue(String.valueOf(Double.parseDouble(creditCard.getMoney()) - total));
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