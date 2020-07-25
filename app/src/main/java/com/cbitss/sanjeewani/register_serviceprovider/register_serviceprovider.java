package com.cbitss.sanjeewani.register_serviceprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cbitss.sanjeewani.R;
import com.cbitss.sanjeewani.register;
import com.cbitss.sanjeewani.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class register_serviceprovider extends AppCompatActivity {//implements DatePickerDialog.OnDateSetListener {
    public static final String TAG = "TAG";
    EditText name,email,phone,dob,password;
    Button register;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    ProgressBar progress;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_serviceprovider);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        dob = findViewById(R.id.dob);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register_s);
        progress = findViewById(R.id.progressBar2);

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check=0;
                final String Name = name.getText().toString();
                final String Email = email.getText().toString().trim();
                final String Phone = phone.getText().toString().trim();
                final String Dob = dob.getText().toString();
                String Password = password.getText().toString().trim();

                if (TextUtils.isEmpty(Email)) {
                    email.setError("Email is required");
                    check=1;
                }
                if (TextUtils.isEmpty(Password)) {
                    password.setError("Password is required");
                    check=1;
                }
                if (password.length() < 6) {
                    password.setError("Password should be atleast 6 characters");
                    check =1;
                }

                if(TextUtils.isEmpty(Name)) {
                    name.setError("Name is required");
                    check = 1;
                }
                if(TextUtils.isEmpty(Phone))
                {
                    phone.setError("Phone Number is required");
                    check=1;
                }
                if(TextUtils.isEmpty(Dob))//To be checked
                {
                    dob.setError("Date  of Birth is required");
                    check = 1;
                }

               else if(check==0){
                    progress.setVisibility(View.VISIBLE);
//                    register the user

                    fauth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser fuser = fauth.getCurrentUser();
                                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(register_serviceprovider.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                    }
                                });

                                Toast.makeText(register_serviceprovider.this, "User Created.", Toast.LENGTH_SHORT).show();
                                userID = fauth.getCurrentUser().getUid();
                                DocumentReference documentReference = fstore.collection("users").document(userID);//document("user_service").collection(userID).document();
                                Map<String,Object> user = new HashMap<>();
                                user.put("fName",Name);
                                user.put("email",Email);
                                user.put("phone",Phone);
                                user.put("dob",Dob);
                                user.put("subscription","free");
                                user.put("type","service_provider");
                                user.put("registered","false");
                                user.put("verified","false");
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                            }else {
                                Toast.makeText(register_serviceprovider.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progress.setVisibility(View.GONE);
                            }
                        }
                    });}
            }
        });
//        dob.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment datePicker = new dob_picker();
//                datePicker.show(getSupportFragmentManager(), "date picker");
//            }
//        });


    }

//    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.YEAR, year);
//        c.set(Calendar.MONTH, month);
//        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
//        TextView textView = (TextView) findViewById(R.id.textView);
//        dob.setText(currentDateString);
//    }
}