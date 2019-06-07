package com.coen499.glamup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        TextView name = findViewById(R.id.profile_name);
        name.setText(firebaseUser.getDisplayName());

        TextView email = findViewById(R.id.profile_email);
        email.setText(firebaseUser.getEmail());

        TextView phone = findViewById(R.id.profile_phone);
        phone.setText(firebaseUser.getPhoneNumber());
    }
}
