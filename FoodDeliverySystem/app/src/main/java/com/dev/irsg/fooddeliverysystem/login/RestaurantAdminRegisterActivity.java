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
 * Restaurant Register Activity.
 * @author Yu Qiu
 */
public class RestaurantAdminRegisterActivity extends AppCompatActivity implements Transition{
    ProgressBar progressBar;
    EditText registerRestaurantEmailText;
    EditText registerRestaurantPhoneText;
    EditText registerRestaurantPasswordText;
    EditText registerRestaurantConfirmPasswordText;
    EditText registerRestaurantCardText;
    EditText registerRestaurantAddressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_admin_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create admin account");

        progressBar = (ProgressBar) findViewById(R.id.restaurant_register_progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        Button register = (Button) findViewById(R.id.register_restaurant_submit_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               registerRestaurantEmailText = (EditText)findViewById(R.id.register_restaurant_email_text);
                registerRestaurantPhoneText = (EditText)findViewById(R.id.register_restaurant_phone_text);
                registerRestaurantPasswordText = (EditText)findViewById(R.id.register_restaurant_password_text);
                registerRestaurantConfirmPasswordText = (EditText)findViewById(R.id.register_restaurant_confirm_password_text);
                registerRestaurantCardText = (EditText)findViewById(R.id.register_restaurant_card_text);
                registerRestaurantAddressText = (EditText)findViewById(R.id.register_restaurant_address_text);

                if (!Patterns.EMAIL_ADDRESS.matcher(registerRestaurantEmailText.getText()).matches()) {
                    setToast("Please input a valid email address.");
                } else if (!registerRestaurantPasswordText.getText().toString().trim().
                        equals(registerRestaurantConfirmPasswordText.getText().toString().trim())) {
                    setToast("Confirmed password is not consistent.");
                } else {
                    String email = registerRestaurantEmailText.getText().toString();
                    String phone = registerRestaurantPhoneText.getText().toString();
                    String password = registerRestaurantPasswordText.getText().toString();
                    String card = registerRestaurantCardText.getText().toString();
                    String address = registerRestaurantAddressText.getText().toString();

                    User signupadmin = new User(email, phone, password, card, address);

                    progressBar.setVisibility(View.VISIBLE);
                    signUp(signupadmin);
                }
            }
        });
    }

    private void signUp(final User signupadmin)
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(signupadmin.getEmail(), signupadmin.getPassword())
                .addOnCompleteListener(RestaurantAdminRegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RestaurantAdminRegisterActivity.this, "Authentication failed." + task.getException(),  Toast.LENGTH_LONG).show();
                        } else {
                            FirebaseHelper.getInstance().setTransition(RestaurantAdminRegisterActivity.this);
                            FirebaseHelper.getInstance().writeAdminProfile(signupadmin);
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
