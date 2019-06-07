package com.coen499.glamup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.coen499.glamup.models.Product;
import com.coen499.glamup.models.Review;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDetails extends AppCompatActivity {

    Product product;
    private List<Review> reviewList;

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

        fetchReviews();

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

    private void fetchReviews() {

        reviewList = new ArrayList<>();
        FirebaseFirestore.getInstance()
            .collection("products").document(product.getId())
            .collection("reviews")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {

                        Review review;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Fetching reviews of a product", document.getId() + " => " + document.getData());
                            review = document.toObject(Review.class);
                            reviewList.add(review);
                        }
                    } else {
                        Log.d("Fetching reviews of a product", "Error getting documents: ", task.getException());
                    }

                    fillInReviews();
                }
            });
    }

    private void fillInReviews() {

        Float avgRating = 0F;
        List<HashMap<String, String>> reviews = new ArrayList<>();
        HashMap<String, String> hm ;
        for(Review review : reviewList) {

            avgRating += review.getRating();
            hm = new HashMap<>();
            //hm.put("image", "https://lh5.googleusercontent.com/-lFs7FwtmXYo/AAAAAAAAAAI/AAAAAAAACRM/FjeONTj-wAE/s96-c/photo.jpg");
            hm.put("user_name", review.getUserName());
            hm.put("rating", String.valueOf(review.getRating()) + "stars");
            hm.put("review", review.getReview());
            Log.d("499review", review.toString());
            reviews.add(hm);
        }
        avgRating = avgRating / reviewList.size();
        ((RatingBar)findViewById(R.id.avgRatingBar)).setRating(avgRating);

        String[] from = {"user_name", "rating", "review"};
        int[] to = {R.id.listview_user_name, R.id.listview_item_rating, R.id.listview_item_review};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getBaseContext(), reviews, R.layout.activity_review, from, to);
        ListView androidListView = (ListView) findViewById(R.id.reviews_list_view);
        androidListView.setAdapter(simpleAdapter);
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

        ConstraintLayout constraintLayout = findViewById(R.id.productConstraintLayout);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.title_user_reviews, ConstraintSet.TOP, R.id.description, ConstraintSet.BOTTOM,24);
        constraintSet.applyTo(constraintLayout);
    }

    public void collapseDescription(View view) {

        TextView more = findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);

        TextView description = findViewById(R.id.description);
        description.setVisibility(View.GONE);

        TextView less = findViewById(R.id.less);
        less.setVisibility(View.GONE);

        ConstraintLayout constraintLayout = findViewById(R.id.productConstraintLayout);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.title_user_reviews, ConstraintSet.TOP, R.id.more, ConstraintSet.BOTTOM,24);
        constraintSet.applyTo(constraintLayout);
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
}
