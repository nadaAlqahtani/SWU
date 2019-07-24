package com.alnadal.nada.swu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccount;
    private EditText ed_name,ed_phoneNumber,ed_password;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ed_name = (EditText)findViewById(R.id.register_username);
        ed_phoneNumber = (EditText)findViewById(R.id.register_phone_number);
        ed_password = (EditText)findViewById(R.id.register_password);
        createAccount =(Button)findViewById(R.id.register_btn);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatAcount();
            }
        });

    }

    private void CreatAcount() {

        String name = ed_name.getText().toString();
        String phone = ed_phoneNumber.getText().toString();
        String password = ed_password.getText().toString();
        mProgressDialog = new ProgressDialog(this);

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mProgressDialog.setTitle("Create Account");
            mProgressDialog.setMessage("Please wait, while we are checking the credentials.");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            ValidatePhoneNumber(name, phone,password);

        }

    }

    private void ValidatePhoneNumber(final String name, final String phone, final String password) {

        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone",phone);
                    userdataMap.put("password",password);
                    userdataMap.put("name",name);

                    databaseReference.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Congratulation, your account has been created", Toast.LENGTH_LONG).show();

                                        mProgressDialog.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);

                                    }

                                    else
                                     {
                                        mProgressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Network Error: Please tray again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "This"+phone+"already exists", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();

                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
