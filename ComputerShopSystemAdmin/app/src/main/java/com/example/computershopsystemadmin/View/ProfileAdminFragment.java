package com.example.computershopsystemadmin.View;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.computershopsystemadmin.DAO.ProfileFirebaseHelper;
import com.example.computershopsystemadmin.R;
import com.example.computershopsystemadmin.databinding.ProfileAdminFragmentBinding;

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
import com.squareup.picasso.Picasso;

public class ProfileAdminFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    ProfileFirebaseHelper helper;
    DatabaseReference databaseReference;
    ProfileAdminFragmentBinding binding;
    StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 4;
    private Uri imageURI;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ProfileAdminFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
        storageReference = FirebaseStorage.getInstance().getReference("Admin");
        helper = new ProfileFirebaseHelper(databaseReference, getActivity());
        helper.loadAAdmin(firebaseUser.getUid(), binding.tvBirthday, binding.tvGender, binding.tvEmail, binding.tvPhone);
        binding.tvName.setText(firebaseUser.getDisplayName());
        if (firebaseUser.getPhotoUrl() != null) {
            Picasso.get().load(firebaseUser.getPhotoUrl()).into(binding.imgAvatar);
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
                Bundle bundle = new Bundle();
                ChangeEmailFragment fragment = new ChangeEmailFragment();
                bundle.putString("email", email);
                fragment.setArguments(bundle);
                switchFragment(fragment);


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
        binding.tvPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassFragment fragment = new ChangePassFragment();
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
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getActivity(),LoginActiveActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
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

                                    databaseReference = FirebaseDatabase.getInstance().getReference("Admin/" + firebaseUser.getUid() + "/image");
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