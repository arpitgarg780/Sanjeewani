package com.cbitss.sanjeewani.register_serviceprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cbitss.sanjeewani.R;
import com.cbitss.sanjeewani.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register_serviceprovider_2 extends AppCompatActivity {

    ImageView image;
    EditText type,description;
    Button next;
    int flag = 0;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_serviceprovider_2);

        image = findViewById(R.id.image);
        type = findViewById(R.id.type);
        description = findViewById(R.id.description);
        next = findViewById(R.id.next2);

        image.setImageResource(R.mipmap.ic_launcher);//TODO: use image selector

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag =0;
                String Type = type.getText().toString();
                String Description = description.getText().toString();

                if(TextUtils.isEmpty(Type)){
                    type.setError("Type is required");
                    flag=1;
                }
                if(TextUtils.isEmpty(Description)){
                    description.setError("Description is required");
                    flag = 1;
                }
                else if(flag==0) {


//                fstore.collection("")
                    //TODO: data to be added to database
                    //got city data in intent
                    Bundle bundle=getIntent().getExtras();
                    String userid = bundle.getString("uid");

                    DocumentReference documentReference = fStore.collection("users_s").document(userid);
                    Map<String,Object> user = new HashMap<>();
                    user.put("type",Type);
                    user.put("description",Description);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(register_serviceprovider_2.this, register_serviceprovider_4.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(register_serviceprovider_2.this, "Some problem occurred. Try again later", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}