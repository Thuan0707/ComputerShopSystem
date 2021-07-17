package com.example.computershopsystemadmin.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.computershopsystemadmin.Addapter.LVProductInCheckOutAdapter;
import com.example.computershopsystemadmin.Model.Order;
import com.example.computershopsystemadmin.Model.OrderProduct;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.OrderHistoryDetailFragmentBinding;

import java.text.DecimalFormat;
import java.util.List;

public class OrderHistoryDetailFragment extends Fragment {
    OrderHistoryDetailFragmentBinding binding;
    Order order;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = OrderHistoryDetailFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            order = (Order) bundle.getSerializable("order");
        }
        binding.tvNameAndPhoneOrder.setText(order.getName() + " - " + order.getNumberPhone());
        binding.tvAddressCheckout.setText(order.getAddesss());
        binding.tvVoucherOrder.setText(order.getVoucher().getCode());
        binding.tvDiscountOrder.setText("-$" + checkInt(order.getVoucher().getDiscount()));
        binding.tvNoteOrder.setText(order.getNote());
        binding.tvPaymentOrder.setText(order.getCreditCard().getCardNumber());
        LVProductInCheckOutAdapter lvProductInCheckOutAdapter = new LVProductInCheckOutAdapter(getActivity(), R.layout.check_out_item, order.getOrderProductList());
        binding.lvProductOrder.setAdapter(lvProductInCheckOutAdapter);
        binding.tvNumberOfProduct.setText("Price (" + String.valueOf(quantityItemInList(order.getOrderProductList())) + " Products)");
        binding.tvPriceOrder.setText("$" + checkInt(sumPriceInList(order.getOrderProductList())));
        binding.tvTotalOrder.setText("$" + checkInt(sumPriceInList(order.getOrderProductList()) + 20 - (order.getVoucher() != null ? order.getVoucher().getDiscount() : 0)));
        return view;
    }


    public double sumPriceInList(List<OrderProduct> productList) {
        double sum = 0;
        for (OrderProduct item : productList) {
            sum += item.getProduct().getSellPrice() * item.getQuantityInCart();
        }
        return sum;
    }

    public int quantityItemInList(List<OrderProduct> productList) {
        int quantity = 0;
        for (OrderProduct item : productList) {
            quantity += item.getQuantityInCart();
        }
        return quantity;
    }

    String checkInt(double num) {
        if ((int) num == num) return Integer.toString((int) num); //for you, StackOverflowException
        DecimalFormat df = new DecimalFormat("###.####");
        return df.format(num); //and for you, Christian Kuetbach
    }
}