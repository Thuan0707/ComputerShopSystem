package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computershopsystemadmin.Model.Brand;
import com.example.computershopsystemadmin.Model.CPU;
import com.example.computershopsystemadmin.Model.Product;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Utils;
import com.example.computershopsystemadmin.Utilities.Variable;
import com.example.computershopsystemadmin.databinding.ChangeCpuProductFragmentBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ChangeCPUProductFragment extends Fragment {

    ChangeCpuProductFragmentBinding binding;
Product pro;
Product product;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ChangeCpuProductFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.getSerializable("newProduct") != null) {
                product = (Product) bundle.getSerializable("newProduct");
                binding.tvTitle.setText("ADD NEW PRODUCT");
                binding.btnSave.setText("Next");
            } else {
                pro = (Product) bundle.getSerializable("product");
                binding.edDesCPU.setText(pro.getCpu().getDescription());
                int pos = 0;
                switch (pro.getCpu().getSeries()) {
                    case "i3":
                        pos = 0;
                        break;
                    case "i5":
                        pos = 1;
                        break;
                    case "i7":
                        pos = 2;
                        break;
                    case "i9":
                        pos = 3;
                        break;
                }
                binding.spSeriesCPU.setSelection(pos);
            }
            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String series = binding.spSeriesCPU.getSelectedItem().toString();
                    String des = binding.edDesCPU.getText().toString();
                    if (bundle.getSerializable("newProduct") != null) {
                        Bundle bundle = new Bundle();
                     CPU cpu=new CPU();
                     cpu.setSeries(series);
                     cpu.setDescription(des);
                        product.setCpu(cpu);
                        ChangeRamProductFragment fragment = new ChangeRamProductFragment();
                        bundle.putSerializable("newProduct", product);
                        fragment.setArguments(bundle);
                        switchFragment(fragment);
                    } else {

                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + pro.getId() + "/cpu/series");
                        mDatabase.setValue(series);
                        pro.getCpu().setSeries(series);
                        mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + pro.getId() + "/cpu/description");
                        mDatabase.setValue(des);
                        pro.getCpu().setDescription(des);
                        ProductDetailsFragment fragment = new ProductDetailsFragment();
                        Bundle bundle = new Bundle();
                        String productJsonString = Utils.getGsonParser().toJson(pro);
                        bundle.putString(Variable.DETAIL_KEY, productJsonString);
                        fragment.setArguments(bundle);
                        switchFragment(fragment);
                    }
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