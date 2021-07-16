package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computershopsystemadmin.Model.CPU;
import com.example.computershopsystemadmin.Model.Product;
import com.example.computershopsystemadmin.Model.Ram;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Utils;
import com.example.computershopsystemadmin.Utilities.Variable;
import com.example.computershopsystemadmin.databinding.ChangeNameProductFragmentBinding;
import com.example.computershopsystemadmin.databinding.ChangeRamProductFragmentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class ChangeRamProductFragment extends Fragment {
    ChangeRamProductFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChangeRamProductFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            Product product = (Product) bundle.getSerializable("product");
            binding.edRAM.setText(product.getRam().getDescription());
            int pos = 0;
            switch (product.getRam().getCapacity()) {
                case 4:
                    pos = 0;
                    break;
                case 8:
                    pos = 1;
                    break;
                case 16:
                    pos = 2;
                    break;
                case 32:
                    pos = 3;
                    break;
            }
            binding.spRAM.setSelection(pos);
            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int capacity = Integer.parseInt(binding.spRAM.getSelectedItem().toString().trim().replace("GB",""));
                    String des = binding.edRAM.getText().toString();
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + product.getId() + "/ram/capacity");
                    mDatabase.setValue(capacity);
                    product.getRam().setCapacity(capacity);
                    mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + product.getId() + "/ram/description");
                    mDatabase.setValue(des);
                    product.getRam().setDescription(des);
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