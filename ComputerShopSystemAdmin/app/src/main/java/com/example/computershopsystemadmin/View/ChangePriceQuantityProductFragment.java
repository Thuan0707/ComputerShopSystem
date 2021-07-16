package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computershopsystemadmin.Model.Product;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Utils;
import com.example.computershopsystemadmin.Utilities.Variable;
import com.example.computershopsystemadmin.databinding.ChangeNameProductFragmentBinding;
import com.example.computershopsystemadmin.databinding.ChangePriceQuantityProductFragmentBinding;
import com.example.computershopsystemadmin.databinding.ProductDetailsFragmentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class ChangePriceQuantityProductFragment extends Fragment {
    ChangePriceQuantityProductFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChangePriceQuantityProductFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            Product product = (Product) bundle.getSerializable("product");
            binding.edBuyPrice.setText(String.valueOf(Utils.checkInt(product.getBuyPrice())));
            binding.edSellPrice.setText(String.valueOf(Utils.checkInt(product.getSellPrice())));
            binding.edQuantity.setText(String.valueOf(product.getQuantity()));


            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    double sellPrice = Double.parseDouble(binding.edSellPrice.getText().toString().trim());
                    double buyPrice = Double.parseDouble(binding.edBuyPrice.getText().toString().trim());
                    int quantity = Integer.parseInt(binding.edQuantity.getText().toString().trim());
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + product.getId() + "/sellPrice");
                    mDatabase.setValue(sellPrice);
                    product.setSellPrice(sellPrice);
                    mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + product.getId() + "/buyPrice");
                    mDatabase.setValue(buyPrice);
                    product.setBuyPrice(buyPrice);
                    mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + product.getId() + "/quantity");
                    mDatabase.setValue(quantity);
                    product.setQuantity(quantity);
                    ProductDetailsFragment fragment = new ProductDetailsFragment();
                    Bundle bundle = new Bundle();
                    String productJsonString = Utils.getGsonParser().toJson(product);
                    bundle.putString(Variable.DETAIL_KEY, productJsonString);
                    fragment.setArguments(bundle);
                    switchFragment(fragment);
                }
            });
        }
        return view;
    }

    public void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragTransaction.addToBackStack(null);
        fragTransaction.replace(R.id.fl_wrapper, fragment);
        fragTransaction.commit();
    }
}