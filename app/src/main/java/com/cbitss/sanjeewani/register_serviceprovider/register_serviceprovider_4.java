package com.cbitss.sanjeewani.register_serviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cbitss.sanjeewani.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class register_serviceprovider_4 extends AppCompatActivity {
    Button submit;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fath = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_serviceprovider_4);

        submit = findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO: uplaod documents
                Toast.makeText(register_serviceprovider_4.this, "You have been successfully registered. Please wait for the approval.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(register_serviceprovider_4.this,not_verified.class));
                finish();

                String uid = fath.getCurrentUser().getUid();
                DocumentReference ref = db.collection("users").document(uid);
                ref.update("registered", "true").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(register_serviceprovider_4.this, "Temporary toast to check update", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}