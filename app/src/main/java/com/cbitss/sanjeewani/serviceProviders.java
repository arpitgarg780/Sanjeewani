package com.cbitss.sanjeewani;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class serviceProviders extends AppCompatActivity {
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers);
        list = findViewById(R.id.list);

        serviceproviderlist ad = new serviceproviderlist(serviceProviders.this);
        list.setAdapter(ad);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(serviceProviders.this, "Pending work", Toast.LENGTH_SHORT).show();
            }
        });
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Wait!! We are fetching best results for you.");
        progress.show();
    }
}