package com.coen499.glamup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coen499.glamup.models.Product;
import com.coen499.glamup.utils.Common;

public class ProductDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Product product = (Product)intent.getSerializableExtra("Product");
        Log.d("Product details", product.toString());

        ImageView productImage = findViewById(R.id.productImage);
        Bitmap imageBitmap = Common.getInstance().getPhotoByProductId(product.getProductImage());
        productImage.setImageBitmap(imageBitmap);

        TextView productName = findViewById(R.id.productName);
        productName.setText(product.getProductName());

        TextView productOwner = findViewById(R.id.productOwner);
        productOwner.setText(" by " + product.getProductOwner());

        TextView productSubtitle = findViewById(R.id.productSubtitle);
        productSubtitle.setText(product.getProductSubtitle());
        productSubtitle.setVisibility(product.getProductSubtitle().isEmpty() ? View.GONE : View.VISIBLE);

        TextView shadeName = findViewById(R.id.shadeName);
        shadeName.setText(product.getShadeName());
        shadeName.setVisibility(product.getShadeName().isEmpty() ? View.GONE : View.VISIBLE);

        TextView price = findViewById(R.id.price);
        price.setText("$" + product.getPrice());

        TextView shadeSubtitle = findViewById(R.id.shadeSubtitle);
        shadeSubtitle.setText(product.getShadeSubtitle());
        shadeSubtitle.setVisibility(product.getShadeSubtitle().isEmpty() ? View.GONE : View.VISIBLE);

        TextView description = findViewById(R.id.description);
        description.setText(product.getDescription());
        description.setVisibility(product.getDescription().isEmpty() ? View.GONE : View.VISIBLE);
    }
}
