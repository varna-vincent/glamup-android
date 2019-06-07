package com.coen499.glamup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(getBaseContext(), Home.class));
                    return true;
                case R.id.navigation_search:
                    startActivity(new Intent(getBaseContext(), Search.class));
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
        setContentView(R.layout.activity_profile);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().findItem(R.id.navigation_me).setChecked(true);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        TextView name = findViewById(R.id.profile_name);
        name.setText(firebaseUser.getDisplayName());

        TextView email = findViewById(R.id.profile_email);
        email.setText(firebaseUser.getEmail());

        TextView phone = findViewById(R.id.profile_phone);
        phone.setText(firebaseUser.getPhoneNumber());
    }
}
