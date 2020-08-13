package com.cbitss.sanjeewani;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class serviceProviders extends AppCompatActivity {
    ArrayList<String> title = new ArrayList<String>();
    ArrayList<String> description = new ArrayList<String>();
    ArrayList<String> address = new ArrayList<String>();
    ArrayList<String> uid = new ArrayList<>();
    String City;
    ListView list;
    FirebaseAuth fauth = FirebaseAuth.getInstance();
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    CollectionReference refer = fstore.collection("users_s");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers);
        list = findViewById(R.id.list);
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Wait!! We are fetching best results for you.");
        progress.show();


        fstore.collection("users").document(fauth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                       City = documentSnapshot.get("city").toString();
//                        Toast.makeText(serviceProviders.this, "city of user is "+City, Toast.LENGTH_SHORT).show();
                        refer.whereEqualTo("city",City)
                                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    title.add(documentSnapshot.getString("description"));
                                    description.add(documentSnapshot.getString("type"));
                                    address.add((documentSnapshot.getString("block"))+" "+(documentSnapshot.getString("street"))+" "+(documentSnapshot.getString("city"))+" "+(documentSnapshot.getString("state")));
                                    uid.add(documentSnapshot.getString("userID"));
//                    Toast.makeText(serviceProviders.this, "Length of list: "+title.size()+" "+description.size()+" "+address.size()
//                            +" "+uid.size(), Toast.LENGTH_SHORT).show();
                                    serviceproviderlist ad = new serviceproviderlist(serviceProviders.this,title,description,address,uid);
                                    list.setAdapter(ad);
                                    progress.dismiss();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(serviceProviders.this, "Database read failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(serviceProviders.this, "Pending work(chat section)", Toast.LENGTH_SHORT).show();
            }
        });

    }
}