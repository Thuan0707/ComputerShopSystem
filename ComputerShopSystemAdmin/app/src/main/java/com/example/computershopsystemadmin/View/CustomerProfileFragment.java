package com.example.computershopsystemadmin.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.computershopsystemadmin.Model.Customer;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.CustomerProfileFragmentBinding;
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

public class CustomerProfileFragment extends Fragment {
    CustomerProfileFragmentBinding binding;
    Customer customer;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    private static final int PICK_IMAGE_REQUEST = 3;
    private Uri imageURI;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = CustomerProfileFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            customer = (Customer) bundle.getSerializable("customer");
        }
        storageReference = FirebaseStorage.getInstance().getReference("Customer");
        binding.tvEmail.setText(customer.getCustomerAccount().getEmail());
        binding.tvPhone.setText(customer.getCustomerAccount().getPhone());
        binding.tvName.setText(customer.getFullName());
        if (customer.getDateOfBirth() != null) {
            binding.tvBirthday.setText(customer.getDateOfBirth());
        }
        String strGender = null;
        switch (customer.getGender()) {
            case 1:
                strGender = "Male";
                break;
            case 0:
                strGender = "Female";
                break;
            case 2:
                strGender = "Other";
                break;
        }
        binding.tvGender.setText(strGender);
        if (customer.getImage() != null) {

            Picasso.get().load(customer.getImage()).into(binding.imgAvatar);
        }

        binding.tvGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderFragment fragment = new GenderFragment();
                switchFragment(fragment);
                Bundle bundle = new Bundle();
                int gender = 0;
                if (binding.tvGender.getText() != null) {
                    switch (binding.tvGender.getText().toString()) {
                        case "Male":
                            gender = 1;
                            break;
                        case "Female":
                            gender = 0;
                            break;
                        case "Other":
                            gender = 2;
                            break;
                    }
                    bundle.putInt("gender", gender);
                    fragment.setArguments(bundle);
                }
                switchFragment(fragment);
            }
        });
        binding.tvBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                BirthdayFragment fragment = new BirthdayFragment();
                if (binding.tvPhone.getText() != null) {
                    bundle.putString("dateOfBirth", binding.tvBirthday.getText().toString());
                    fragment.setArguments(bundle);
                }
                switchFragment(fragment);
            }
        });
        binding.tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.tvEmail.getText().toString();
                if (email == "") {
                    ChangeEmailFragment fragment = new ChangeEmailFragment();

                    switchFragment(fragment);
                } else {
                    Toast.makeText(getContext(), "You can not change email", Toast.LENGTH_SHORT).show();

                }

            }
        });
        binding.tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                ChangePhoneFragment fragment = new ChangePhoneFragment();
                if (binding.tvPhone.getText() != null) {
                    bundle.putString("phone", binding.tvPhone.getText().toString());
                    fragment.setArguments(bundle);
                }

                switchFragment(fragment);
            }
        });

        binding.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ChangeNameFragment fragment = new ChangeNameFragment();
                if (binding.tvName.getText() != null) {
                    bundle.putString("name", binding.tvName.getText().toString());
                    fragment.setArguments(bundle);
                }
                switchFragment(fragment);
            }
        });
        binding.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST&&data!=null) {
            imageURI = data.getData();
            Picasso.get().load(imageURI).resize(500, 500).into(binding.imgAvatar);
            uploadFile();
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
                                    customer.setImage(fileLink);
                                    databaseReference = FirebaseDatabase.getInstance().getReference("Customer/" + customer.getId() + "/image");
                                    databaseReference.setValue(fileLink);

                                    Toast.makeText(getContext(), "Change Photo successfully", Toast.LENGTH_SHORT).show();

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
}