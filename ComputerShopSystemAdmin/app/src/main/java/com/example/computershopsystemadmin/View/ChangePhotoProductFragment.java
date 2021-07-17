package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.computershopsystemadmin.Model.Product;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.ChangePhotoProductFragmentBinding;
import com.example.computershopsystemadmin.databinding.ChangeScreenProductFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ChangePhotoProductFragment extends Fragment {
    ChangePhotoProductFragmentBinding binding;
    Product product;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private static final int PICK_IMAGE_REQUEST = 2;
    private Uri imageURI;
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = ChangePhotoProductFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        storageReference = FirebaseStorage.getInstance().getReference("Product");
        Bundle bundle = this.getArguments();
        if (bundle.getSerializable("newProduct") != null) {
            product = (Product) bundle.getSerializable("newProduct");
        }

        binding.btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        binding.btnAddNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            imageURI = data.getData();
            Picasso.get().load(imageURI).resize(500,500).into(binding.ivProduct);
        }
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
                                    product.setImage(fileLink);
                                    databaseReference = FirebaseDatabase.getInstance().getReference("Product");
                                    databaseReference.push().setValue(product);
                                    ProductManagementFragment fragment=new ProductManagementFragment();
                                    switchFragment(fragment);
                                    Toast.makeText(getContext(), "Add product success fully", Toast.LENGTH_SHORT).show();

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
    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cr.getType(uri));
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