package com.coen499.glamup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.coen499.glamup.models.Product;
import com.coen499.glamup.models.Review;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class AddReview extends AppCompatActivity {

    private FirebaseFirestore firestoreRef;
    private FirebaseUser firebaseUser;
    private Product product;
    final Activity self = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        firestoreRef = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser== null){
            Intent intent = new Intent(AddReview.this, Login.class);
            startActivity(intent);
        }

        Toolbar toolbar = findViewById(R.id.add_review_toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_left_24px));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSupportNavigateUp();
            }
        });

        Intent intent = getIntent();
        product = (Product)intent.getSerializableExtra("selectedProduct");
        Log.d("Product details", product.toString());

        TextView title = findViewById(R.id.add_review_product_title);
        title.setText(product.getProductOwner() + "'s " + product.getProductName() + " " + product.getShadeName());
    }

    public void addReview(View view) {

        Log.d("add review", firebaseUser.getDisplayName() + "," + firebaseUser.getEmail());
        CollectionReference reviewsRef = firestoreRef
                .collection("products").document(product.getId())
                .collection("reviews");

        Review review = new Review();
        review.setReview(((EditText)findViewById(R.id.add_review_review)).getText().toString());
        review.setRating(((RatingBar)findViewById(R.id.add_review_ratingBar)).getRating());
        review.setUserEmail(firebaseUser.getEmail());
        review.setUserName(firebaseUser.getDisplayName());

        reviewsRef.add(review).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("Add Review", "DocumentSnapshot added with ID: " + documentReference.getId());
                Intent intent = new Intent(self, ProductDetails.class);
                intent.putExtra("Product", (Serializable) product);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Add Review", "Error adding document", e);
            }
        });
    }
}
