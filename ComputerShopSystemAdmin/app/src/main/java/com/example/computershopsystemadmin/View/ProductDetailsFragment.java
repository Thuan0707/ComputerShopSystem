package com.example.computershopsystemadmin.View;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystemadmin.Model.OrderProduct;
import com.example.computershopsystemadmin.Model.Product;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.Utilities.Utils;
import com.example.computershopsystemadmin.Utilities.Variable;
import com.example.computershopsystemadmin.databinding.ProductDetailsFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ProductDetailsFragment extends Fragment {

    private static String TAG = "testing";
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageURI;
    public Product product;
    ProductDetailsFragmentBinding binding;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProductDetailsFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        storageReference = FirebaseStorage.getInstance().getReference("Product");
        Bundle bundle = getArguments();
        String productJsonString = bundle.getString(Variable.DETAIL_KEY);
        product = Utils.getGsonParser().fromJson(productJsonString, Product.class);
        binding.txtBuyPriceProduct.setText("BUY PRICE: $" + Utils.checkInt(product.getBuyPrice()));
        binding.txtCPU.setText(product.getCpu().getSeries() + " " + product.getCpu().getDescription());
        Picasso.get().load(product.getImage()).resize(500,500).into(binding.ivProduct);
        binding.txtSellPriceProduct.setText("SELL PRICE: $" + Utils.checkInt(product.getSellPrice()));
        binding.txtNameProduct.setText(product.getName());
        binding.txtRAM.setText(product.getRam().getCapacity() + "GB " + product.getRam().getDescription());
        binding.txtScreen.setText(product.getScreen().getSize() + " " + product.getScreen().getDescription());
        binding.txtROM.setText(product.getRom().getCapacity() + " " + product.getRom().getDescription());
        binding.txtBrand.setText(product.getBrand().getName());
        binding.txtQuantity.setText(String.valueOf(product.getQuantity()));

        binding.ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        binding.txtQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ChangePriceQuantityProductFragment fragment = new ChangePriceQuantityProductFragment();
                bundle.putSerializable("product", product);
                fragment.setArguments(bundle);
                switchFragment(fragment);
            }
        });
        binding.txtBuyPriceProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ChangePriceQuantityProductFragment fragment = new ChangePriceQuantityProductFragment();
                bundle.putSerializable("product", product);
                fragment.setArguments(bundle);
                switchFragment(fragment);
            }
        });
        binding.txtSellPriceProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ChangePriceQuantityProductFragment fragment = new ChangePriceQuantityProductFragment();
                bundle.putSerializable("product", product);
                fragment.setArguments(bundle);
                switchFragment(fragment);
            }
        });
        binding.txtCPU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ChangeCPUProductFragment fragment = new ChangeCPUProductFragment();
                bundle.putSerializable("product", product);
                fragment.setArguments(bundle);
                switchFragment(fragment);
            }
        });
        binding.txtBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ChangeBrandProductFragment fragment = new ChangeBrandProductFragment();
                bundle.putSerializable("product", product);
                fragment.setArguments(bundle);
                switchFragment(fragment);
            }
        });
        binding.txtRAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ChangeRamProductFragment fragment = new ChangeRamProductFragment();
                bundle.putSerializable("product", product);
                fragment.setArguments(bundle);
                switchFragment(fragment);
            }
        });
        binding.txtROM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ChangeROMProductFragment fragment = new ChangeROMProductFragment();
                bundle.putSerializable("product", product);
                fragment.setArguments(bundle);
                switchFragment(fragment);
            }
        });
        binding.txtScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ChangeScreenProductFragment fragment = new ChangeScreenProductFragment();
                bundle.putSerializable("product", product);
                fragment.setArguments(bundle);
                switchFragment(fragment);
            }
        });
        binding.txtNameProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ChangeNameProductFragment fragment = new ChangeNameProductFragment();
                bundle.putSerializable("product", product);
                fragment.setArguments(bundle);
                switchFragment(fragment);
            }
        });
        //Disable product  in product management
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Product/" + product.getId());
                mDatabase.removeValue();
                Toast.makeText(getContext(), "Delete product is successfully", Toast.LENGTH_SHORT).show();

                ProductManagementFragment fragment=new ProductManagementFragment();
                switchFragment(fragment);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && data!=null) {
            imageURI = data.getData();
            uploadFile();

            Log.e("uri", imageURI.toString());
            Picasso.get().load(imageURI).resize(500,500).into(binding.ivProduct);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cr.getType(uri));
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

    public void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragmentManager.beginTransaction();
        fragTransaction.setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out);
        fragTransaction.addToBackStack(null);
        fragTransaction.replace(R.id.fl_wrapper, fragment);
        fragTransaction.commit();
    }

    private void uploadFile() {
        if (imageURI != null) {
            StorageReference fileReference = storageReference.child(imageURI.getLastPathSegment());
            fileReference.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                            new OnCompleteListener<Uri>() {

                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String fileLink = task.getResult().toString();
                                    databaseReference = FirebaseDatabase.getInstance().getReference("Product/" + product.getId() + "/image");
                                    databaseReference.setValue(fileLink);

                                }
                            }

                    );

                }
            });
        } else {
            Toast.makeText(getContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
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