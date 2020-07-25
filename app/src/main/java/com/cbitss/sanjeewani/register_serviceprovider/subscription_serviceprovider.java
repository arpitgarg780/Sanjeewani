package com.cbitss.sanjeewani.register_serviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cbitss.sanjeewani.R;
import com.cbitss.sanjeewani.chat_serviceprovider;
import com.cbitss.sanjeewani.serviceProviders;
import com.cbitss.sanjeewani.subscription;

public class subscription_serviceprovider extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_serviceprovider);

    }
    public void serviceroviders(View view) {
        startActivity(new Intent(subscription_serviceprovider.this, chat_serviceprovider.class));
        finish();
    }

    public void paymentpage(View view) {
        Toast.makeText(this, "Currently everything is free. Enjoy!!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(subscription_serviceprovider.this,chat_serviceprovider.class));
        finish();
    }
}