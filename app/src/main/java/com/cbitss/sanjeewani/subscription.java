package com.cbitss.sanjeewani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class subscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);
    }

    public void serviceroviders(View view) {
        startActivity(new Intent(subscription.this,serviceProviders.class));
        finish();
    }

    public void paymentpage(View view) {
        Toast.makeText(this, "Currently everything is free. Enjoy!!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(subscription.this,serviceProviders.class));
        finish();
    }
}