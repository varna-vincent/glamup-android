package com.coen499.glamup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coen499.glamup.models.Product;
import com.coen499.glamup.viewholders.ProductCardHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private TextView mTextMessage;
    private FirebaseFirestore firestoreRef;

    private RecyclerView homeRecyclerView;
    private ProductRecyclerAdapter productRecyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Product> productList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_search:
                    mTextMessage.setText(R.string.title_search);
                    return true;
                case R.id.navigation_me:
                    startActivity(new Intent(getBaseContext(), Profile.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        firestoreRef = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser== null) {
            Intent intent = new Intent(Home.this, Login.class);
            startActivity(intent);
        }

        homeRecyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
        homeRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        // specify an adapter (see also next example)
        productRecyclerAdapter = new ProductRecyclerAdapter();
        homeRecyclerView.setAdapter(productRecyclerAdapter);
        fetchProductsNearMe(productRecyclerAdapter);
    }

    private void fetchProductsNearMe(final ProductRecyclerAdapter productRecyclerAdapter) {

        productList = new ArrayList<>();
        firestoreRef.collection("products")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        Product product;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("Home - fetching products", document.getId() + " => " + document.getData());
                            product = document.toObject(Product.class);
                            product.setId(document.getId());
                            productList.add(product);
                        }
                        Log.d("Home - products", productList.get(0).toString());
                    } else {
                        Log.w("Home - fetching products", "Error getting documents.", task.getException());
                    }
                    productRecyclerAdapter.notifyDataSetChanged();
                }
            });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, Login.class));
    }

    private class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductCardHolder> {


        @NonNull
        @Override
        public ProductCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
            return new ProductCardHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductCardHolder holder, int position) {

            Log.d("Home.Products", productList.get(position).getProductName());
            String imgTitle = productList.get(position).getProductName() + " by " + productList.get(position).getProductOwner();
            holder.getProductCardCaption().setText(imgTitle);
            setPhotoByProductId(holder.getProductCardPhoto(), productList.get(position).getProductImage());
            setProductItemClickListener(holder.getParentLayout(), productList.get(position));
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }
    }

    private void setPhotoByProductId(final ImageView productCardPhoto, String imageURL) {

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
                productCardPhoto.setImageBitmap(imageBitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    private void setProductItemClickListener(LinearLayout parentLayout, final Product product) {
        final Activity self = this;
        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(self, ProductDetails.class);
                intent.putExtra("Product", (Serializable) product);
                startActivity(intent);
            }
        });
    }
}
