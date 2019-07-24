package com.alnadal.nada.swu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alnadal.nada.swu.Model.Users;
import com.alnadal.nada.swu.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText ed_phoneNumber,ed_password;
    private Button loginButton;
    private ProgressDialog mProgressDialog;
    private String parentDbName = "Users";
    private com.rey.material.widget.CheckBox mCheckBoxRememberMe;
    private TextView adminLink,notAdminLink;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_phoneNumber = (EditText)findViewById(R.id.login_phone_number);
        ed_password = (EditText)findViewById(R.id.login_password);
        loginButton =(Button)findViewById(R.id.login_btn);
        mCheckBoxRememberMe =(com.rey.material.widget.CheckBox)findViewById(R.id.remember_me_chbox);
        adminLink =(TextView)findViewById(R.id.admin_panel_link);
        notAdminLink =(TextView)findViewById(R.id.not_admin_panel_link);



        //use paper
        Paper.init(this);
        mProgressDialog =new ProgressDialog(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginUser();
            }
        });


        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Login Admin");
                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);

                parentDbName = "Admins";


            }
        });

        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginButton.setText("Login");
                adminLink.setVisibility(View.VISIBLE);
                notAdminLink.setVisibility(View.INVISIBLE);

                parentDbName = "Users";

            }
        });

    }

    private void LoginUser() {

        String phone = ed_phoneNumber.getText().toString();
        String password = ed_password.getText().toString();



         if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number ", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mProgressDialog.setTitle("Login Account");
            mProgressDialog.setMessage("Please wait, while we are checking the credentials.");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            AllowAccessAccount( phone,password);

        }
    }

    private void AllowAccessAccount(final String phone, final String password) {

        if (mCheckBoxRememberMe.isChecked())
        {
            //to but phone and password in memory if is checked
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);

        }

        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentDbName).child(phone).exists())
                {

                    Users usersData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                         if (parentDbName.equals("Admins"))
                         {
                             Toast.makeText(LoginActivity.this, "Admin login in Successfully..", Toast.LENGTH_LONG).show();
                             mProgressDialog.dismiss();

                             Intent intent=new Intent(LoginActivity.this,AdminCategoryActivity.class);
                             startActivity(intent);
                         }
                         else if (parentDbName.equals("Users"))
                         {
                             Toast.makeText(LoginActivity.this, "login in Successfully..", Toast.LENGTH_LONG).show();
                             mProgressDialog.dismiss();

                             Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                             Prevalent.currentOnlineUser = usersData;
                             startActivity(intent);
                         }

                        }
                        else
                         {
                            mProgressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Password not correct:Please tray again", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Account with this "+ phone+"number do not exists.", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
