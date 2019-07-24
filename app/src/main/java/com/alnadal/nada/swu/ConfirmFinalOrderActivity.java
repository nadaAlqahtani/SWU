package com.alnadal.nada.swu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alnadal.nada.swu.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private Button confirmButton;
    private EditText shippmentName,shippmentPhoneNumber,shippmentAdress,shippmentCity;

    private String totalAmount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount =getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price ="+totalAmount+"$", Toast.LENGTH_SHORT).show();

        shippmentAdress =(EditText)findViewById(R.id.shippment_address);
        shippmentName =(EditText)findViewById(R.id.shippment_name);
        shippmentCity =(EditText)findViewById(R.id.shippment_city);
        shippmentPhoneNumber =(EditText)findViewById(R.id.shippment_phone_number);
        confirmButton=(Button)findViewById(R.id.confirm_final_order_btn);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Check();
            }
        });

    }

    private void Check()
    {
        if (TextUtils.isEmpty(shippmentName.getText().toString()))
        {
            Toast.makeText(this, "Please Provide your full Name", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(shippmentPhoneNumber.getText().toString()))
        {
            Toast.makeText(this, "Please Provide your Phone Number", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(shippmentAdress.getText().toString()))
        {
            Toast.makeText(this, "Please Provide your Address", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(shippmentCity.getText().toString()))
        {
            Toast.makeText(this, "Please Provide your City", Toast.LENGTH_LONG).show();
        }
        else
        {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder()
    {
        final String saveCurrentDate,saveCurrentTime;
        Calendar calForDate= Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate =currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTimae= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime =currentDate.format(calForDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String,Object> ordersMap=new HashMap<>();
        ordersMap.put("totalAmount",totalAmount);
        ordersMap.put("name",shippmentName.getText().toString());
        ordersMap.put("phone",shippmentPhoneNumber.getText().toString());
        ordersMap.put("address",shippmentAdress.getText().toString());
        ordersMap.put("city",shippmentCity.getText().toString());
        ordersMap.put("date",saveCurrentDate);
        ordersMap.put("time",saveCurrentTime);
        ordersMap.put("state","not shipped");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                  FirebaseDatabase.getInstance().getReference().child("Cart List")
                          .child("User View")
                          .child(Prevalent.currentOnlineUser.getPhone())
                          .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task)
                      {
                          if (task.isSuccessful())
                          {
                              Toast.makeText(ConfirmFinalOrderActivity.this, "your final order has been placed successfully.", Toast.LENGTH_SHORT).show();
                              Intent intent= new Intent(ConfirmFinalOrderActivity.this,HomeActivity.class);
                              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                              startActivity(intent);
                              finish();

                          }
                      }
                  });
                }
            }
        });


    }
}
