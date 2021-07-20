package com.example.computershopsystem.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.computershopsystem.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class AccountLoginSuccessFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    private GoogleSignInClient googleSignInClient;
    FirebaseUser firebaseUser;
    TextView account;

    Button btnLogOut;
    Button btnPayment;
    Button btnProfile;
    Button btnOrder;
    Button btnAddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.account_login_success_fragment, container, false);
        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser= firebaseAuth.getCurrentUser();
        String name = firebaseUser.getDisplayName();
        account = v.findViewById(R.id.tvAccount2);
        account.setText(name);
        btnLogOut = v.findViewById(R.id.btnLogout);
        btnPayment=v.findViewById(R.id.btnPayment);
        btnProfile=v.findViewById(R.id.btnProfile);
        btnOrder=v.findViewById(R.id.btnOrder);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment fragment = new ProfileFragment();
                switchFragment(fragment);
            }
        });

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GoogleMapsApi.class));
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                signOut();
                CusHomeFragment fragment = new CusHomeFragment();
                switchFragment(fragment);
            }
        });
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditCardFragment fragment = new CreditCardFragment();
                switchFragment(fragment);
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderHistoryFragment fragment=new OrderHistoryFragment();
                switchFragment(fragment);
            }
        });

        return v;
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

    private void signOut() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getActivity(), options);
        googleSignInClient.signOut();
    }


}
