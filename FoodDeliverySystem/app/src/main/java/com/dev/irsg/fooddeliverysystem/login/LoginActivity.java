package com.dev.irsg.fooddeliverysystem.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dev.irsg.fooddeliverysystem.AppLogic;
import com.dev.irsg.fooddeliverysystem.R;
import com.dev.irsg.fooddeliverysystem.customerui.MenuActivity;
import com.dev.irsg.fooddeliverysystem.entities.User;
import com.dev.irsg.fooddeliverysystem.util.FirebaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final String LOG_TAG = "LoginActivity::singIn";
    private static final String LOGIN_PREFERENCE = "LoginPreference";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;

    ProgressBar progressBar;
    EditText emailTextView;
    EditText passwordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sign in");

        progressBar = (ProgressBar) findViewById(R.id.login_progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        SharedPreferences prefs = getSharedPreferences(LOGIN_PREFERENCE, MODE_PRIVATE);
        String savedEmail = prefs.getString("email", null);
        String savedPassword = prefs.getString("password", null);

        emailTextView = (EditText)findViewById(R.id.email);
        passwordTextView = (EditText)findViewById(R.id.password);

        if( savedEmail != null)
            emailTextView.setText(savedEmail);

        if( savedPassword != null)
            passwordTextView.setText(savedPassword);

        Button loginCustomerButton = (Button) findViewById(R.id.email_sign_in_customer_button);
        Button loginRestaurantAdminButton = (Button) findViewById(R.id.email_sign_in_restaurant_button);
        Button customerRegButton = (Button) findViewById(R.id.email_register_customer_button);
        Button restaurantAdminRegButton = (Button) findViewById(R.id.email_register_restaurant_button);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //login as customer
        loginCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (!validateForm())
                   return;

               String email = emailTextView.getText().toString();
               String password = passwordTextView.getText().toString();

               SharedPreferences.Editor editor = getSharedPreferences(LOGIN_PREFERENCE, MODE_PRIVATE).edit();
               editor.putString("email", email);
               editor.putString("password", password);
               editor.commit();

               signInAsCustomer(email, password);
            }
        });

        customerRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CustomerRegisterActivity.class);
                startActivity(intent);
            }
        });

        loginRestaurantAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateForm())
                    return;

                String email = emailTextView.getText().toString();
                String password = passwordTextView.getText().toString();

                SharedPreferences.Editor editor = getSharedPreferences(LOGIN_PREFERENCE, MODE_PRIVATE).edit();
                editor.putString("email", email);
                editor.putString("password", password);
                editor.commit();

                signInAsRestaurantAdmin(email, password);
            }
        });

        restaurantAdminRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RestaurantAdminRegisterActivity.class);
                startActivity(intent);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    //redirect
                    Intent intent = new Intent(getApplicationContext(), CustomerRegisterActivity.class);
                    startActivity(intent);

                    finish();

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthListener != null ) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signInAsCustomer(final String email, final String password)
    {
        progressBar.setVisibility(View.VISIBLE);

        database.getReference().child(FirebaseHelper.CUSTOMERS_ROOT)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);

                            //found user
                            if (email.equals(user.getEmail())) {
                                if (! password.equals(user.getPassword())) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(LoginActivity.this, "Wrong password.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                signInAsCustomerByFirebaseUser(user);
                                return;
                            }
                       }

                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this, "Can not find your account.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getMessage());
                    }
                });
    }

    private void signInAsCustomerByFirebaseUser(final User user)
    {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(LOG_TAG, " Verification : signIn With Email:onComplete:" + task.isSuccessful());
                        //  If sign in succeeds i.e if task.isSuccessful(); returns true then the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        // If sign in fails, display a message to the user.

                        progressBar.setVisibility(View.INVISIBLE);
                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(LoginActivity.this, "Invalid Emaild Id", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.e(LOG_TAG, e.getMessage());
                            }
                            Log.w(LOG_TAG, "signInWithEmail:failed", task.getException());

                            Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                        } else {
                            AppLogic.getInstance().createCurrentCustomer(user);

                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);

                            finish();
                        }
                    }
                });
    }

    private void signInAsRestaurantAdmin(final String email, final String password)
    {
        progressBar.setVisibility(View.VISIBLE);

        database.getReference().child(FirebaseHelper.ADMIN_ROOT)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);

                            if (email.equals(user.getEmail())) {
                                if (! password.equals(user.getPassword())) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(LoginActivity.this, "Wrong password.", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                signInAsRestaurnatByFirebaseUser(user);
                                return;
                            }
                        }

                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this, "Can not find your account.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getMessage());
                    }
                });
    }

    private void signInAsRestaurnatByFirebaseUser(final User user)
    {
        mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(LOG_TAG, " Verification : signIn With Email:onComplete:" + task.isSuccessful());
                        //  If sign in succeeds i.e if task.isSuccessful(); returns true then the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        // If sign in fails, display a message to the user.

                        progressBar.setVisibility(View.INVISIBLE);
                        if (!task.isSuccessful()) {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(LoginActivity.this, "Invalid Emaild Id", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(LoginActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.e(LOG_TAG, e.getMessage());
                            }
                            Log.w(LOG_TAG, "signInWithEmail:failed", task.getException());

                            Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                        } else {
                            AppLogic.getInstance().createRestaurantAdmin(user);

                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);

                            finish();
                        }
                    }
                });
    }

    private boolean validateForm()
     {
         String email = emailTextView.getText().toString();
         String password = passwordTextView.getText().toString();

         if ( email.isEmpty()) {
             messageBox("Enter email address!");
             return false;
         }

         if ( password.isEmpty()) {
             messageBox("Enter password!");
             return false;
         }

         return true;
     }

     private void messageBox(String message)
     {
         AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

         alertDialogBuilder.setMessage(message);
         alertDialogBuilder.setPositiveButton("Ok",
                 new DialogInterface.OnClickListener() {

                     @Override
                     public void onClick(DialogInterface arg0, int arg1) {
                     }
                 });

         AlertDialog alertDialog = alertDialogBuilder.create();
         alertDialog.show();
     }
}
