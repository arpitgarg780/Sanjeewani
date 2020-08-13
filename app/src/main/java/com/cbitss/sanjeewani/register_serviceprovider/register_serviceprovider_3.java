package com.cbitss.sanjeewani.register_serviceprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cbitss.sanjeewani.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class register_serviceprovider_3 extends AppCompatActivity {
    public static String TAG ="TAG";
    EditText block,street,pincode,city,state,country;
    Button next;
    FirebaseAuth fauth = FirebaseAuth.getInstance();
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_serviceprovider_3);
        block = findViewById(R.id.ad1);
        street = findViewById(R.id.ad2);
        pincode = findViewById(R.id.pincode);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        country = findViewById(R.id.country);
        next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int flag=0;
                String Block = block.getText().toString();
                String Street = street.getText().toString();
                String Pin = pincode.getText().toString();
                final String City = city.getText().toString();
                String State = street.getText().toString();
                String Country = country.getText().toString();

                if(TextUtils.isEmpty(Block)){
                    block.setError("Block is required");
                    flag = 1;
                }
                if(TextUtils.isEmpty(Street)){
                    street.setError("Street is required");
                    flag = 1;
                }
                if(TextUtils.isEmpty(Pin)){
                    pincode.setError("Pincode is required");
                    flag = 1;
                }
                if(TextUtils.isEmpty(City)) {
                    city.setError("City is required");
                    flag = 1;
                }
                if(TextUtils.isEmpty(State)){
                    state.setError("State is required");
                    flag = 1;
                }
                if(TextUtils.isEmpty(Country)){
                    country.setError("Country is required");
                    flag = 1;
                }
                else if(flag == 0){
                    final String userID = fauth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection("users_s").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("userID",userID);
                    user.put("block",Block);
                    user.put("street",Street);
                    user.put("pin",Pin);
                    user.put("city",City);
                    user.put("state",State);
                    user.put("country",Country);
                    //TODO: add step recorder to start where left off
                    documentReference.set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: service provider address is created for "+ userID);
                            Intent i = new Intent(register_serviceprovider_3.this,register_serviceprovider_2.class);
                            i.putExtra("uid",userID);
                            startActivity(i);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.toString());
                        }
                    });


                }
            }
        });
    }
}