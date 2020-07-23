package com.cbitss.sanjeewani.register_serviceprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.cbitss.sanjeewani.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;

public class register_serviceprovider extends AppCompatActivity {//implements DatePickerDialog.OnDateSetListener {
    EditText name,email,phone,dob,password;
    Button register;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
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

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String Phone = phone.getText().toString().trim();
            }
        });
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new dob_picker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


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