package com.cbitss.sanjeewani.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cbitss.sanjeewani.R;
import com.cbitss.sanjeewani.login;
import com.cbitss.sanjeewani.register;
import com.cbitss.sanjeewani.register_serviceprovider.not_verified;
import com.cbitss.sanjeewani.register_serviceprovider.register_serviceprovider_2;
import com.cbitss.sanjeewani.register_serviceprovider.register_serviceprovider_3;
import com.cbitss.sanjeewani.subscription;
import com.cbitss.sanjeewani.ui.login.LoginViewModel;
import com.cbitss.sanjeewani.ui.login.LoginViewModelFactory;
import com.cbitss.sanjeewani.user_address;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    FirebaseAuth fauth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String type,verified,registered,address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        final Button start = findViewById(R.id.button);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, register.class);
                startActivity(i);
            }
        });

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

//        Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                 ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                 ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
        if(fauth.getCurrentUser()==null)
        {
            startActivity(new Intent(LoginActivity.this,login.class));
            finish();
        }
    }

        private void updateUiWithUser (LoggedInUserView model){
            String welcome = getString(R.string.welcome) + model.getDisplayName();
            // TODO : initiate successful logged in experience
            Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        }

        private void showLoginFailed (@StringRes Integer errorString){
            Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
        }

        public void logout (View view){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(LoginActivity.this, login.class));
            finish();
        }

        public void subscription (View view){
//             db.collection("users_s").whereEqualTo(fauth.getCurrentUser().getUid(),true).get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if(task.isSuccessful()){
//                                for(QueryDocumentSnapshot document : task.getResult()){
//                                    Toast.makeText(LoginActivity.this, document.getId()+" "+document.getData(), Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                            else
//                            {
//
//                                Toast.makeText(LoginActivity.this, "error " + task.getException(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });

            String uid = fauth.getCurrentUser().getUid();
            db.collection("users").document(uid).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                type = task.getResult().getString("type");
                                registered = task.getResult().getString("registered");
                                verified = task.getResult().getString("verified");
                                if(type.equals("customer"))
                                address = task.getResult().getString("address");
                                Toast.makeText(LoginActivity.this, type, Toast.LENGTH_SHORT).show();
                                if(type.equals("service_provider")) {
                                    if (registered.equals("false")){
                                        startActivity(new Intent(LoginActivity.this, register_serviceprovider_3.class));
                                        finish();
                                    }
                                    else if((registered.equals("true")&&verified.equals("false")||!fauth.getCurrentUser().isEmailVerified())){
                                        startActivity(new Intent(LoginActivity.this, not_verified.class));
                                        finish();
                                    }
                                    else {
//                                       TODO: chat section
                                    }
                                }
                                else {
                                    if(address.equals("false")) {
                                        startActivity(new Intent(LoginActivity.this, user_address.class));
                                        finish();
                                    }
                                    else {
                                        startActivity(new Intent(LoginActivity.this, subscription.class));
                                        finish();
                                    }
                                }

                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this, "Error!! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            //Toast.makeText(this, type, Toast.LENGTH_SHORT).show();

        }

    }
