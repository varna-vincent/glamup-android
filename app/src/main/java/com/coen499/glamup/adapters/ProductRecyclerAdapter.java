package com.coen499.glamup.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coen499.glamup.Home;
import com.coen499.glamup.R;
import com.coen499.glamup.viewholders.ProductCardHolder;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductCardHolder> {


    @NonNull
    @Override
    public ProductCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
        return new ProductCardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCardHolder holder, int position) {

//        Log.i("Home.Products", placeResponseList.get(position).getName());
//        holder.getProductCardCaption().setText(placeResponseList.get(position).getName());
//        setPhotoByPlaceId(holder.getProductCardPhoto(), placeResponseList.get(position).getId());
//        setPlaceItemClickListener(holder.getParentLayout(), placeResponseList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
