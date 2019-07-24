package com.alnadal.nada.swu;

import android.content.DialogInterface;
import android.content.Intent;
import android.preference.DialogPreference;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alnadal.nada.swu.Model.Cart;
import com.alnadal.nada.swu.Prevalent.Prevalent;
import com.alnadal.nada.swu.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Cartctivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button nextButton;
    private TextView textTotalAmount,txt_msg_1;

    private int overTotalPrice = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartctivity);

        mRecyclerView =(RecyclerView)findViewById(R.id.cart_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager =new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        textTotalAmount=(TextView)findViewById(R.id.total_price);
        txt_msg_1=(TextView)findViewById(R.id.msg_1);
        nextButton = (Button)findViewById(R.id.next_btn);



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent intent =new Intent(Cartctivity.this,ConfirmFinalOrderActivity.class);
                intent.putExtra("Total Price",String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        CheckOrderState();

        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products"),Cart.class)
                .build();


        FirebaseRecyclerAdapter<Cart,CartViewHolder> adapter =new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model)
            {
                holder.txtProductQuantity.setText("Quantity ="+model.getQuantity());
                holder.txtProductName.setText(model.getPname());
                holder.txtProductPrice.setText("Price "+model.getPrice()+"$");

                //for calcult total price

                int oneTipeProductPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());

                overTotalPrice = overTotalPrice +oneTipeProductPrice;
                textTotalAmount.setText("Total Price ="+String.valueOf(overTotalPrice)+"$");



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        CharSequence options[] =new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"
                                };

                        AlertDialog.Builder builder= new AlertDialog.Builder(Cartctivity.this);
                        builder.setTitle("Cart Options");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if (i==0)
                                {
                                    Intent intent=new Intent(Cartctivity.this,ProductDetailsActivity.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);

                                }
                                if (i==1)
                                {
                                   cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products")
                                           .child(model.getPid())
                                           .removeValue()
                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                   if (task.isSuccessful())
                                                   {
                                                       Toast.makeText(Cartctivity.this, "Item removed successfully.", Toast.LENGTH_SHORT).show();
                                                       Intent intent=new Intent(Cartctivity.this,HomeActivity.class);
                                                       startActivity(intent);
                                                   }
                                               }
                                           });

                                }

                            }
                        });

                        builder.show();


                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder =new CartViewHolder(view);
                return holder;

            }
        };

        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private  void CheckOrderState()
    {
        DatabaseReference orderRef;
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String shippingState = dataSnapshot.child("state").getValue().toString();
                    String username = dataSnapshot.child("name").getValue().toString();

                    if (shippingState.equals("shipped"))
                    {
                        textTotalAmount.setText("Dear "+ username + "\n order is shipped successfully.");
                        mRecyclerView.setVisibility(View.GONE);
                        txt_msg_1.setVisibility(View.VISIBLE);
                        txt_msg_1.setText("your final order hes been shipped successfully. soon you will received order at your door step.");
                        nextButton.setVisibility(View.GONE);
                    }
                    else if (shippingState.equals("not shipped"
                    ))
                    {
                        textTotalAmount.setText("Shipping Stat = Not Shipped");
                        mRecyclerView.setVisibility(View.GONE);
                        txt_msg_1.setVisibility(View.VISIBLE);
                        nextButton.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
