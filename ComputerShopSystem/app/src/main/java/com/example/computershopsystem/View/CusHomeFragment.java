package com.example.computershopsystem.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystem.DAO.ProductFirebaseHelper;
import com.example.computershopsystem.Model.CreditCard;
import com.example.computershopsystem.Model.Product;
import com.example.computershopsystem.Model.Voucher;
import com.example.computershopsystem.R;
import com.example.computershopsystem.Utilities.Utils;
import com.example.computershopsystem.Utilities.Variable;
import com.example.computershopsystem.databinding.CusHomeFragmentBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class CusHomeFragment extends Fragment {
    CusHomeFragmentBinding binding;
    ProductFirebaseHelper helper;
    DatabaseReference databaseReference;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private final String TAG = "testing";
    private final String PRODUCT_KEY = "product";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CusHomeFragmentBinding.inflate(getLayoutInflater());
        databaseReference = FirebaseDatabase.getInstance().getReference("Product");
        helper = new ProductFirebaseHelper(databaseReference, binding.gridProduct, getActivity());
        Bundle bundle = this.getArguments();


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            if(firebaseUser.getPhoneNumber()==""){
                firebaseAuth.signOut();
                signOut();
            }
            sharedpreferences = getActivity().getSharedPreferences(firebaseUser.getUid(), MODE_PRIVATE);
            editor = sharedpreferences.edit();
            if (!sharedpreferences.contains("cart")) {
                List<Product> listProductInCart = new ArrayList<>();
                setList("cart", listProductInCart);
            }
            if (!sharedpreferences.contains("voucher")) {
                Voucher voucher=new Voucher();
                Gson gson = new Gson();
                String json = gson.toJson(voucher);
                editor.putString("voucher", json);
                editor.commit();
            }
            if (!sharedpreferences.contains("note")) {
                editor.putString("note", "");
                editor.commit();
            }
            if (!sharedpreferences.contains("creditCard")) {
                CreditCard creditCard=new CreditCard();
                Gson gson = new Gson();
                String json = gson.toJson(creditCard);
                editor.putString("creditCard", json);
                editor.commit();
            }else{
                CreditCard creditCard=new CreditCard();
                Gson gson = new Gson();
                String json = gson.toJson(creditCard);
                editor.putString("creditCard", json);
                editor.apply();
            }
        }

        if (bundle != null) {
            String str = bundle.getString("Filter");
            Log.e("afsd", str);
            switch (str) {
                case "HP":
                    helper.retrieveByBrand("HP");
                    break;
                case "Dell":
                    helper.retrieveByBrand("Dell");
                    break;
                case "Acer":
                    helper.retrieveByBrand("Acer");
                    break;
                case "Lenovo":
                    helper.retrieveByBrand("Lenovo");
                    break;
                case "Asus":
                    helper.retrieveByBrand("Asus");
                    break;
                case "LowPrice":
                    helper.retrieveByLowPrice();
                    break;
                case "MediumPrice":
                    helper.retrieveByMediumPrice();
                    break;
                case "HighPrice":
                    helper.retrieveByHighPrice();
                    break;
                case "4gb":
                    helper.retrieveByRam(4);
                    break;
                case "8gb":
                    helper.retrieveByRam(8);
                    break;
                case "16gb":
                    helper.retrieveByRam(16);
                    break;
                case "512gb":
                    helper.retrieveByRom("512GB");
                    break;
                case "1tb":
                    helper.retrieveByRom("1TB");
                    break;
                case "2tb":
                    helper.retrieveByRom("2TB");
                    break;
                case "i3":
                    helper.retrieveBySPU("i3");
                    break;
                case "i5":
                    helper.retrieveBySPU("i5");
                    break;
                case "i7":
                    helper.retrieveBySPU("i7");
                    break;
                case "s14":
                    helper.retrieveByScreenSize("14");
                    break;
                case "s15.6":
                    helper.retrieveByScreenSize("15.6");
                    break;
                case "s17":
                    helper.retrieveByScreenSize("17");
                    break;
            }
        } else {
            helper.retrieve();
        }

        binding.gridProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getAdapter().getItem(position);

                Bundle bundle = new Bundle();
                String productJsonString = Utils.getGsonParser().toJson(product);
                bundle.putString(Variable.DETAIL_KEY, productJsonString);

                //Set ProductDetailsFragment Arguments
                ProductDetailsFragment fragment = new ProductDetailsFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
                fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                        android.R.animator.fade_out);
                fragTransaction.addToBackStack(null);
                fragTransaction.replace(R.id.fl_wrapper, fragment);
                fragTransaction.commit();


            }
        });


        binding.tvMoreCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreCategoryFragment fragment = new MoreCategoryFragment(binding.gridProduct);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
                fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                        android.R.animator.fade_out);
                fragTransaction.addToBackStack(null);
                fragTransaction.replace(R.id.fl_wrapper, fragment);
                fragTransaction.commit();
            }
        });
        binding.txtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (binding.txtSearch.getRight() - binding.txtSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()-20)) {
                        binding.txtSearch.setText("");
                        return true;
                    }
                }
                return false;
            }
        });



        binding.txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    helper.retrieveByName(s.toString());

                } else {
                    helper.retrieve();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        binding.btnAsus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.retrieveByBrand("Asus".trim());
            }
        });
        binding.btnHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.retrieveByBrand("HP".trim());
            }
        });
        binding.btnAcer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.retrieveByBrand("Acer".trim());
            }
        });
        binding.btnDell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.retrieveByBrand("Dell".trim());
            }
        });




        View view = binding.getRoot();
        return view;
    }




    public <T> void setList(String key, List<T> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        set(key, json);
    }

    public void set(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    private void signOut() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getActivity(), options);
        googleSignInClient.signOut();
    }

}
