package com.example.computershopsystem.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.computershopsystem.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.jetbrains.annotations.NotNull;

public class LoginActiveActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;

    private static final String TAG = "GOOGLE_SIGN_IN_TAG";

    private GoogleSignInClient googleSignInClient;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    TextView tvRegister;
    Button btGoogle;
    LoginButton btFacebook;

    private CallbackManager mCallbackManager;
    private final static String TAGFACE = "FacebookAuthentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_active);

        btGoogle = findViewById(R.id.btnGoogle);

        btFacebook = findViewById(R.id.btnFace);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        btFacebook.setReadPermissions("email", "public_profile");
        mCallbackManager = CallbackManager.Factory.create();
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        btGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick: begin Google Login");
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });
//        btFacebook.setReadPermissions("email","public_profile");
//        mCallbackManager = CallbackManager.Factory.create();
        btFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAGFACE, "OnSuccess" + loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAGFACE, "OnCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAGFACE, "OnError" + error);
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull @NotNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {

                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    firebaseAuth.signOut();
                }
            }
        };

        tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActiveActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            startActivity(new Intent(this, AccountLoginSuccess.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "onActivityResult: Google Signin intent result");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d(TAG, "account task" + accountTask.toString());
            try {
                Log.d(TAG, "I'm here");
                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                Log.d(TAG, "account: " + account);
                firebaseAuthWithGoogleAccount(account);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "onActivity result:" + e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogleAccount: begin");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        user.reauthenticate(credential)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<Void> task) {
//                        Log.d(TAG, "reauthenticate");
//                    }
//                });
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "onSuccess: Log In");

                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                        String uid = firebaseUser.getUid();
                        String email = firebaseUser.getEmail();
                        String phoneNumber = firebaseUser.getPhoneNumber();


                        Log.d(TAG, "uID: " + uid);
                        Log.d(TAG, "Email: " + email);
                        Log.d(TAG, "NumberPhone: " + phoneNumber);

                        if (authResult.getAdditionalUserInfo().isNewUser()) {
                            Log.d(TAG, "onSuccess: Account Created");
                            Toast.makeText(LoginActiveActivity.this, "Account created for " + email, Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "onSuccess: Existing User: " + email);
                            Toast.makeText(LoginActiveActivity.this, "Welcome back " + email, Toast.LENGTH_SHORT).show();
                        }


                        startActivity(new Intent(LoginActiveActivity.this, MainActivity.class));
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Log.d(TAG, "onFailure: Log In failed");
                    }
                });

    }

    private void handleFacebookToken(AccessToken token) {
        Log.d(TAGFACE, "handleFacebookToken" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAGFACE, "sign in with credential: successful");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}