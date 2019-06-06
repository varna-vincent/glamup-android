package com.coen499.glamup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.coen499.glamup.models.Product;

public class AddReview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        Intent intent = getIntent();
        Product product = (Product)intent.getSerializableExtra("selectedProduct");
        Log.d("Product details", product.toString());

        TextView title = findViewById(R.id.add_review_product_title);
        title.setText(product.getProductOwner() + "'s " + product.getProductName() + " " + product.getShadeName());
    }
}
