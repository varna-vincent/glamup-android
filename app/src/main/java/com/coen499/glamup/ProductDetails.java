package com.coen499.glamup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coen499.glamup.models.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

public class ProductDetails extends AppCompatActivity {

    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_left_24px));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSupportNavigateUp();
            }
        });

        Intent intent = getIntent();
        product = (Product)intent.getSerializableExtra("Product");
        Log.d("Product details", product.toString());

        ImageView productImage = findViewById(R.id.productImage);
        setPhotoByProductId(productImage, product.getProductImage());

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
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void redirectAddReview(View view) {
        Intent intent = new Intent(this, AddReview.class);
        intent.putExtra("selectedProduct", (Serializable) product);
        startActivity(intent);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.review, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_favorite) {
//            Intent intent = new Intent(this, AddReview.class);
//            intent.putExtra("selectedProduct", (Serializable) product);
//            startActivity(intent);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void setPhotoByProductId(final ImageView imageView, String imageURL) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(imageURL);
        Log.d("Home - img", "in setPhotoByProductId" + storageRef);
        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Log.d("Home - setting img", String.valueOf(bytes.length));
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(imageBitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    public void expandDescription(View view) {

        TextView more = findViewById(R.id.more);
        more.setVisibility(View.GONE);

        TextView description = findViewById(R.id.description);
        description.setVisibility(View.VISIBLE);

        TextView less = findViewById(R.id.less);
        less.setVisibility(View.VISIBLE);
    }

    public void collapseDescription(View view) {

        TextView more = findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);

        TextView description = findViewById(R.id.description);
        description.setVisibility(View.GONE);

        TextView less = findViewById(R.id.less);
        less.setVisibility(View.GONE);
    }
}
