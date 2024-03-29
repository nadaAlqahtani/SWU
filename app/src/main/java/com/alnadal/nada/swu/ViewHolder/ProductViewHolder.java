package com.alnadal.nada.swu.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alnadal.nada.swu.InterFace.ItemClickListener;
import com.alnadal.nada.swu.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txtProductName, txtProductDescripion,txtProductPrice;
    public ImageView mImageView;
    public ItemClickListener  listener;

    public ProductViewHolder(View itemView) {
        super(itemView);

        mImageView =(ImageView)itemView.findViewById(R.id.product_image);
        txtProductName =(TextView)itemView.findViewById(R.id.product_name);
        txtProductDescripion =(TextView)itemView.findViewById(R.id.product_description);
        txtProductPrice =(TextView)itemView.findViewById(R.id.product_price);
    }

    public  void setItemClickListener(ItemClickListener listener)
    {
        this.listener= listener;
    }

    @Override
    public void onClick(View view) {

        listener.onClick(view,getAdapterPosition(),false);
    }
}
