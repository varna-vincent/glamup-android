package com.coen499.glamup;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.coen499.glamup.models.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

public class Login extends AppCompatActivity {

//    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore rootRef;
    private FirebaseAuth.AuthStateListener authStateListener;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try { this.getSupportActionBar().hide(); }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        rootRef = FirebaseFirestore.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser!= null){
                    Intent intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        firebaseAuth = FirebaseAuth.getInstance();

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d("TAG", "Error Connecting!");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();
        Button signin = findViewById(R.id.sign_in_button);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Login", "clicking signin btn");
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, 123);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        updateUI(currentUser);

        firebaseAuth.addAuthStateListener(authStateListener);
    }

//    public void login(View view) {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Log.w("Login", "Google sign in failed", e);
//                // ...
//            }
//        }

        super.onActivityResult(requestCode,resultCode,data);
        Log.d("login499","in onActivityResult, requestCode" + requestCode + ", resultCode" + resultCode);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(googleSignInResult.isSuccess()){
                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
                Log.d("login499","in activityresult" + googleSignInAccount);
                firebaseSignInWithGoogle(googleSignInAccount);
            } else{
                Log.d("Tag","Error logging in!");
            }
        }
    }

    private void firebaseSignInWithGoogle(final GoogleSignInAccount googleSignInAccount){
        AuthCredential authCredential= GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String userEmail = googleSignInAccount.getEmail();
                    String userName = googleSignInAccount.getDisplayName();
                    String tokenId = FirebaseInstanceId.getInstance().getToken();
                    User user = new User(tokenId, userName, userEmail);

                    rootRef.collection("users").document(userEmail).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("TAG","User Created");
                        }
                    });
                }else{
                    Log.d("TAG", "failed with:"+task.getException());
                }
            }
        });
    }

//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d("Login", "firebaseAuthWithGoogle:" + acct.getId());
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("Login", "signInWithCredential:success");
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                            System.out.println("In firebaseAuthWithGoogle - " + user);
////                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.d("Login", "failed with:" + task.getException());
//                        }
//
//                        // ...
//                    }
//                });
//    }
}
