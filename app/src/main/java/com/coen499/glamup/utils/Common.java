package com.coen499.glamup.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Common {

    private static Common instance;

    private Common() {}

    public static Common getInstance() {

        if(instance == null){
            instance = new Common();
        }
        return instance;
    }

    public Bitmap getPhotoByProductId(String imageURL) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(imageURL);
        Log.d("Common - img", "in getPhotoByProductId" + storageRef);
        final long ONE_MEGABYTE = 1024 * 1024;
        final Bitmap[] imageBitmap = {null};
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Log.d("Common - getting img", String.valueOf(bytes.length));
                imageBitmap[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        Log.d("Product details", imageBitmap.toString());
        return imageBitmap[0];
    }
}
