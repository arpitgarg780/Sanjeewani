package com.cbitss.sanjeewani.register_serviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cbitss.sanjeewani.R;

public class register_serviceprovider_3 extends AppCompatActivity {
    EditText block,street,pincode,city,state,country;
    Button next;

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
                String Block = block.getText().toString();
                String Street = street.getText().toString();
                String Pin = pincode.getText().toString();
                String City = city.getText().toString();
                String State = street.getText().toString();
                String Country = country.getText().toString();
            }
        });
    }
}