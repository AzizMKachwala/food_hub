package com.example;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodhub.PhoneRegistrationActivity;
import com.example.foodhub.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText etvFullName, etvEmail, etvPassword;
    TextView txtAlreadyLogin;
    ImageView imgPasswordCloseEye;
    Button btnSignUp;
    CardView cvFacebook, cvGoogle;
    String password = "Hide";

    CallbackManager callbackManager;
    FirebaseAuth auth;
    FirebaseDatabase database;
    GoogleSignInClient googleSignInClient;
    int Sign_In = 20;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etvFullName = findViewById(R.id.etvFullNameSU);
        etvEmail = findViewById(R.id.etvEmailSU);
        etvPassword = findViewById(R.id.etvPasswordSU);
        txtAlreadyLogin = findViewById(R.id.txtAlreadyLogin);
        imgPasswordCloseEye =findViewById(R.id.imgPasswordCloseEye);
        btnSignUp = findViewById(R.id.btnSignUp);
        cvFacebook = findViewById(R.id.cvFacebook);
        cvGoogle = findViewById(R.id.cvGoogle);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        imgPasswordCloseEye.setOnClickListener(v -> {
            if (password.equals("Hide")) {
                password = "Show";
                etvPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                etvPassword.setSelection(etvPassword.length());
                imgPasswordCloseEye.setImageResource(R.drawable.ceye);
            } else {
                password = "Hide";
                etvPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                etvPassword.setSelection(etvPassword.length());
                imgPasswordCloseEye.setImageResource(R.drawable.baseline_eye_24);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etvFullName.getText().toString().trim().isEmpty()){
                    etvFullName.setError("Enter Full Name");
                    etvFullName.requestFocus();
                }
                else if (!Tools.isValidEmail(etvEmail.getText().toString().trim())) {
                    etvEmail.setError("Email Address must contain @ and .com in it");
                    etvEmail.requestFocus();
                }
                else if (!isValidPassword(etvPassword.getText().toString().trim())) {
                    etvPassword.setError("Password Must Consist Of Minimum length of 7 with At-least 1 UpperCase," +
                            "1 LowerCase, 1 Number & 1 Special Character");
                    etvPassword.requestFocus();
                }
                else {
//                    Toast.makeText(SignUpActivity.this, "ADD USER", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, PhoneRegistrationActivity.class));
                }
            }

            private boolean isValidPassword(String password) {
                return password.length() >= 7 && password.matches(".*[A-Z].*") && password.matches(".*[a-z].*")
                        && password.matches(".*\\d.*") && password.matches(".*[@#$%^&+=].*");
            }
        });

        txtAlreadyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });

        cvFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SignUpActivity.this, "FACEBOOK LOGIN", Toast.LENGTH_SHORT).show();
                facebookSignIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        cvGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SignUpActivity.this, "GOOGLE LOGIN", Toast.LENGTH_SHORT).show();
                if(auth.getCurrentUser() != null){
                    auth.signOut();
                }
                clearGoogle();
            }
        });

        if(auth.getCurrentUser() != null){

            FirebaseUser user = auth.getCurrentUser();
            Intent intent = new Intent(SignUpActivity.this, HomeScreenActivity.class);
            intent.putExtra("user_name", user.getDisplayName());
            intent.putExtra("user_email", user.getEmail());
            intent.putExtra("user_photo", user.getPhotoUrl().toString());
            startActivity(intent);
            finish();
        }

    }

    private void clearGoogle() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            googleSignInClient.revokeAccess()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            googleSignIn();
                        }
                    });
        }
        else {
            googleSignIn();
        }
    }

    private void facebookSignIn() {
        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(@NonNull FacebookException e) {

            }
        });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();

                            HashMap<String, Object> map = new HashMap<>();
                            map.put("id", user.getUid());
                            map.put("email", user.getEmail());
                            map.put("name", user.getDisplayName());
                            map.put("profile", user.getPhotoUrl().toString());

                            database.getReference().child("facebook_users").child(user.getUid()).setValue(map)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                // Redirect to the HomeScreenActivity upon successful authentication and database update

                                                Intent intent = new Intent(SignUpActivity.this, HomeScreenActivity.class);
                                                intent.putExtra("user_name", user.getDisplayName());
                                                intent.putExtra("user_email", user.getEmail());
                                                intent.putExtra("user_photo", user.getPhotoUrl().toString());
                                                startActivity(intent);
                                                finish();

//                                                startActivity(new Intent(SignUpActivity.this, HomeScreenActivity.class));
                                            }
                                            else {
                                                // Display a Toast message if writing to the database fails
                                                Toast.makeText(SignUpActivity.this, "Failed to write user information to the database", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void googleSignIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, Sign_In);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Sign_In){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{
                GoogleSignInAccount account = task.getResult(ApiException.class);
                handleGoogleAuth(account.getIdToken());
            }
            catch (Exception e){
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleGoogleAuth(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser user = auth.getCurrentUser();

                            HashMap<String,Object> map = new HashMap<>();
                            map.put("id",user.getUid());
                            map.put("email",user.getEmail());
                            map.put("name",user.getDisplayName());
                            map.put("profile",user.getPhotoUrl().toString());

                            database.getReference().child("google_users").child(user.getUid()).setValue(map)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> databaseTask) {
                                            if (databaseTask.isSuccessful()) {
                                                // Redirect to the HomeScreenActivity upon successful authentication and database update

                                                Intent intent = new Intent(SignUpActivity.this, HomeScreenActivity.class);
                                                intent.putExtra("user_name",user.getDisplayName());
                                                intent.putExtra("user_email",user.getEmail());
                                                intent.putExtra("user_photo",user.getPhotoUrl().toString());
                                                startActivity(intent);
                                                finish();

//                                                startActivity(new Intent(SignUpActivity.this, HomeScreenActivity.class));
                                            } else {
                                                // Display a Toast message if writing to the database fails
                                                Toast.makeText(SignUpActivity.this, "Failed to write user information to the database", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else {
                            Toast.makeText(SignUpActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}