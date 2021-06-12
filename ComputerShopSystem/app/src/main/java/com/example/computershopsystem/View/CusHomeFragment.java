package com.example.computershopsystem.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.computershopsystem.DAO.DAO;
import com.example.computershopsystem.Model.GridAdapter;
import com.example.computershopsystem.databinding.CusHomeFragmentBinding;


public class CusHomeFragment extends Fragment {
    CusHomeFragmentBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        binding = CusHomeFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        DAO dao = new DAO();
        dao.loadProduct();
        GridAdapter gridAdapter = new GridAdapter(getActivity(), dao.getListProduct());
        binding.gridProduct.setAdapter(gridAdapter);
        return view;
    }
}
