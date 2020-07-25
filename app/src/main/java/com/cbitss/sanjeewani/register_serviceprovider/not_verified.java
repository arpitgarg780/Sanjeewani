package com.cbitss.sanjeewani.register_serviceprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cbitss.sanjeewani.R;
import com.cbitss.sanjeewani.register;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class not_verified extends AppCompatActivity {
    TextView email,pending;
    Button resend;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_verified);
        email = findViewById(R.id.email);
        pending = findViewById(R.id.pending);
        resend = findViewById(R.id.resend);

        fauth = FirebaseAuth.getInstance();
        final String TAG = "TAG";

        if(fauth.getCurrentUser().isEmailVerified()){
            email.setText("Email Id verified");
            resend.setVisibility(View.GONE);
        }

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser fuser = fauth.getCurrentUser();
                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(not_verified.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(not_verified.this, "Some problem occurred. Email not sent", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                    }
                });
            }
        });

    }
}