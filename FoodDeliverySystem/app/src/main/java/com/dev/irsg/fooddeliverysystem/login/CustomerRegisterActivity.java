package com.dev.irsg.fooddeliverysystem.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dev.irsg.fooddeliverysystem.R;
import com.dev.irsg.fooddeliverysystem.entities.User;
import com.dev.irsg.fooddeliverysystem.util.FirebaseHelper;
import com.dev.irsg.fooddeliverysystem.util.Transition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Ugi on 6/15/2017.
 */

public class CustomerRegisterActivity extends AppCompatActivity implements Transition {
    ProgressBar progressBar;
    EditText registerEmailText;
    EditText registerPasswordText;
    EditText registerConfirmPasswordText;
    EditText registerCardText;
    EditText registerAddressText;
    EditText registerPhoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        progressBar = (ProgressBar) findViewById(R.id.customer_register_progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        //I do n't know why occurs error when this code snippet is not explained.
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create customer account");
        Button register = (Button) findViewById(R.id.register_submit_customer_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerEmailText = (EditText)findViewById(R.id.register_email_text);
                registerPasswordText = (EditText)findViewById(R.id.register_password_text);
                registerConfirmPasswordText = (EditText)findViewById(R.id.register_confirm_password_text);
                registerCardText = (EditText)findViewById(R.id.register_card_text);
                registerAddressText = (EditText)findViewById(R.id.register_address_text);
                registerPhoneText = (EditText)findViewById(R.id.register_phone_text);

                if (!Patterns.EMAIL_ADDRESS.matcher(registerEmailText.getText()).matches()) {
                    setToast("Please input a valid email address.");
                } else if (!registerPasswordText.getText().toString().trim().
                        equals(registerConfirmPasswordText.getText().toString().trim())) {
                    setToast("Confirmed password is not consistent.");
                }
                else {
                    String email = registerEmailText.getText().toString();
                    String phone = registerPhoneText.getText().toString();
                    String password = registerPasswordText.getText().toString();
                    String card = registerCardText.getText().toString();
                    String address = registerAddressText.getText().toString();

                    User signupcustomer = new User(email, phone, password, card, address);

                    progressBar.setVisibility(View.VISIBLE);
                    signUp(signupcustomer);
                }
            }
        });
    }

    private void signUp(final User signupcustomer)
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(signupcustomer.getEmail(), signupcustomer.getPassword())
                .addOnCompleteListener(CustomerRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(CustomerRegisterActivity.this, "Authentication failed." + task.getException(),  Toast.LENGTH_LONG).show();
                        } else {
                            FirebaseHelper.getInstance().setTransition(CustomerRegisterActivity.this);
                            FirebaseHelper.getInstance().writeCustomerProfile(signupcustomer);
                        }
                    }
                });
    }

    public void transit() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void setToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}
