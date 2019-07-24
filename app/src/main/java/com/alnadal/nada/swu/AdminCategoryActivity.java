package com.alnadal.nada.swu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView tShirts, sportsTshirts, femaleDresses , sweathers,glasses, hatsCaps, walletsBagsPurse ,shoes,headphonesHandFree,Laptops,mobilePhone,watches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);


        tShirts =(ImageView)findViewById(R.id.t_shirts);
        sportsTshirts =(ImageView)findViewById(R.id.sports_t_shirts);
        femaleDresses =(ImageView)findViewById(R.id.female_dresses);
        sweathers =(ImageView)findViewById(R.id.sweather);
        glasses =(ImageView)findViewById(R.id.glasses);
        hatsCaps =(ImageView)findViewById(R.id.hats);
        walletsBagsPurse =(ImageView)findViewById(R.id.purses_bags);
        shoes =(ImageView)findViewById(R.id.shoess);
        headphonesHandFree =(ImageView)findViewById(R.id.headphoness);
        Laptops =(ImageView)findViewById(R.id.laptops);
        mobilePhone =(ImageView)findViewById(R.id.mobiles);
        watches =(ImageView)findViewById(R.id.watches);


        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","tShirts");
                startActivity(intent);


            }
        });



        sportsTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Sports TShirts");
                startActivity(intent);


            }
        });


        femaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Female Dresses");
                startActivity(intent);


            }
        });



        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Sweathers");
                startActivity(intent);


            }
        });


        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Glasses");
                startActivity(intent);


            }
        });



        hatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Hats Caps");
                startActivity(intent);


            }
        });


        walletsBagsPurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Wallets Bags Purse");
                startActivity(intent);


            }
        });


        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Shoes");
                startActivity(intent);


            }
        });


        headphonesHandFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Headphones HandFree");
                startActivity(intent);


            }
        });


        Laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Laptops");
                startActivity(intent);


            }
        });


        mobilePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Mobile Phone");
                startActivity(intent);


            }
        });



        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category","Watches");
                startActivity(intent);


            }
        });

    }
}
