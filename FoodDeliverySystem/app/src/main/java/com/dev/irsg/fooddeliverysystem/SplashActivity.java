package com.dev.irsg.fooddeliverysystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.dev.irsg.fooddeliverysystem.entities.Food;
import com.dev.irsg.fooddeliverysystem.entities.FoodMenu;
import com.dev.irsg.fooddeliverysystem.login.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends Activity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        progressBar = (ProgressBar) findViewById(R.id.splash_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        loadMenuDataAndTransit();
    }

    //read data form firebase database.

    private void loadMenuDataAndTransit() {
         FirebaseDatabase.getInstance().getReference().child("foods")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        FoodMenu menu = FoodMenu.getInstance();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            String key = snapshot.getKey();

                            Food food = snapshot.getValue(Food.class);

                            food.setName(key);

                            menu.addFood(food);
                       }

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressBar.setVisibility(View.INVISIBLE);
                        System.out.println("The read failed: " + databaseError.getMessage());
                    }
                });
    }
 }
