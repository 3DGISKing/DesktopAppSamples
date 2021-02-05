package com.dev.irsg.busassister.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.graphics.Color;
import android.widget.TextView;

import com.dev.irsg.busassister.AppLogic;
import com.dev.irsg.busassister.R;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

public class LoginActivity extends AppCompatActivity {
    boolean passwordVisibleCheck = true;
    boolean usernameTyped = false;
    boolean passwordTyped = false;
    Button submitButton;
    EditText passwordEditText;
    EditText userNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        passwordEditText = (EditText) findViewById(R.id.password);
        userNameEditText  = (EditText) findViewById(R.id.username);

        submitButton = (Button) findViewById(R.id.submit);
        submitButton.setEnabled(false);
        submitButton.setTextColor(Color.parseColor("#c6c6c6"));
        submitButton.setBackgroundResource(R.drawable.roundshapebtndisable);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAndNotifyErrorMessage())
                    goToStartActivity();
            }
        });

        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Drawable img_left = getResources().getDrawable( R.drawable.ic_password);
                        if(passwordVisibleCheck) {
                            passwordEditText.setInputType(TYPE_CLASS_TEXT|TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            Drawable img = getResources().getDrawable( R.drawable.ic_password_visible );
                            passwordEditText.setCompoundDrawablesWithIntrinsicBounds( img_left, null, img, null);

                        }
                        else {
                            passwordEditText.setInputType(TYPE_CLASS_TEXT|TYPE_TEXT_VARIATION_PASSWORD);
                            Drawable img = getResources().getDrawable( R.drawable.ic_password_invisible);
                            passwordEditText.setCompoundDrawablesWithIntrinsicBounds( img_left, null, img, null);
                        }

                        passwordVisibleCheck = !passwordVisibleCheck;

                        return true;
                    }
                }
                return false;
            }
        });

        userNameEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    usernameTyped = true;
                else
                    usernameTyped = false;

                if(usernameTyped && passwordTyped)
                    enableSubmitButton();
                else
                    disableSubmitButton();
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               if(s.length() != 0)
                    passwordTyped = true;
                else
                    passwordTyped = false;

                if(usernameTyped  && passwordTyped)
                    enableSubmitButton();
                else
                    disableSubmitButton();
            }
        });

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    if (checkAndNotifyErrorMessage())
                        goToStartActivity();

                    handled = true;
                }
                return handled;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (AppLogic.SIMPLE)
            goToStartActivity();
    }

    private boolean checkAndNotifyErrorMessage() {
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (userName.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Please input user name!");

            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();

            alert.setCancelable(false);
            alert.show();

            return false;
        }

        if (password.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Please input password!");

            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();

            alert.setCancelable(false);
            alert.show();

            return false;
        }


        if ( !userName.equals("Admin") ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Unregistered user!");

            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();

            alert.setCancelable(false);
            alert.show();

            return false;
        }

        if ( !password.equals("Admin123") ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Invalid password");

            builder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();

            alert.setCancelable(false);
            alert.show();
            return false;
        }

        return true;
    }

    private void enableSubmitButton() {
        submitButton.setBackgroundResource(R.drawable.roundshapebtn);
        submitButton.setEnabled(true);
        submitButton.setTextColor(Color.parseColor("#FBE300"));
    }

    private void disableSubmitButton() {
        submitButton.setBackgroundResource(R.drawable.roundshapebtndisable);
        submitButton.setTextColor(Color.parseColor("#ffffff"));
        submitButton.setEnabled(false);
    }

    private void goToStartActivity() {
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
    }
}
