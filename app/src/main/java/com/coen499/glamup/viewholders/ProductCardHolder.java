package com.coen499.glamup.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coen499.glamup.R;

public class ProductCardHolder extends RecyclerView.ViewHolder {

    LinearLayout parentLayout;
    ImageView productCardPhoto;
    TextView productCardCaption;

    public ProductCardHolder(@NonNull View itemView) {
        super(itemView);
        parentLayout = (LinearLayout) itemView.findViewById(R.id.parentLayout);
        productCardPhoto = (ImageView) itemView.findViewById(R.id.productCardPhoto);
        productCardCaption = (TextView) itemView.findViewById(R.id.productCardCaption);
    }

    public LinearLayout getParentLayout() {
        return parentLayout;
    }

    public void setParentLayout(LinearLayout parentLayout) {
        this.parentLayout = parentLayout;
    }

    public ImageView getProductCardPhoto() {
        return productCardPhoto;
    }

    public void setProductCardPhoto(ImageView productCardPhoto) {
        this.productCardPhoto = productCardPhoto;
    }

    public TextView getProductCardCaption() {
        return productCardCaption;
    }

    public void setProductCardCaption(TextView productCardCaption) {
        this.productCardCaption = productCardCaption;
    }
}
