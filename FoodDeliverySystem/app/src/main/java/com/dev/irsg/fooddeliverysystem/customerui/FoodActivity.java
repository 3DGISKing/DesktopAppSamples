package com.dev.irsg.fooddeliverysystem.customerui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dev.irsg.fooddeliverysystem.AppLogic;
import com.dev.irsg.fooddeliverysystem.R;
import com.dev.irsg.fooddeliverysystem.entities.Food;
import com.dev.irsg.fooddeliverysystem.entities.FoodMenu;
import com.dev.irsg.fooddeliverysystem.entities.FoodOption;
import com.dev.irsg.fooddeliverysystem.entities.FoodSubMenu;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {
    TextView option1Textview;
    TextView option2Textview;
    Switch   option1Switch;
    Switch   option2Switch;
    TextView imageTextview;
    TextView food_option_price1;
    TextView food_option_price2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeActionContentDescription("Back");
        //actionBar.setHomeAsUpIndicator();

        Intent intent = getIntent();

        String category = intent.getStringExtra("category");
        int foodnumber = intent.getIntExtra("foodnumber", 0);

        ImageView food_imageView = (ImageView) findViewById(R.id.food_imageview);
        TextView  food_description_textView = (TextView) findViewById(R.id.food_description_textView);

        option1Textview = (TextView) findViewById(R.id.option1_description);
        option2Textview = (TextView) findViewById(R.id.option2_description);
        option1Switch = (Switch) findViewById(R.id.food_option_switch1);
        option2Switch = (Switch) findViewById(R.id.food_option_switch2);
        food_option_price1 = (TextView) findViewById(R.id.food_option_price1);
        food_option_price2 = (TextView) findViewById(R.id.food_option_price2);
        imageTextview = (TextView) findViewById(R.id.price);



        FoodSubMenu subMenu = FoodMenu.getInstance().getSubMenu(category);

        final Food food = subMenu.getFood(foodnumber);

        food_description_textView.setText(food.getDescription());

        String imagePrice = String.valueOf(food.getPrice());

        imageTextview.setText(imagePrice);


        String name = food.getName();

        actionBar.setTitle(name);

        String path = "Images/food/" + food.getImageURL();

        StorageReference ref = FirebaseStorage.getInstance().getReference().child(path);

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(ref)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .override(512, 512)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(food_imageView);

        ArrayList<FoodOption> foodOptions = food.getOptions();

        if(foodOptions == null) {
            //This food have not any option. So we hide all option interface.
            LinearLayout optionslayout = (LinearLayout) findViewById(R.id.options_layout);

            optionslayout.setVisibility(View.INVISIBLE);
         } else {
            if (foodOptions.size() == 1) {
                option1Textview.setText(foodOptions.get(0).getDescription());
                food_option_price1.setText(String.valueOf(foodOptions.get(0).getPrice()));

                //hide second option interface
                LinearLayout option2layout = (LinearLayout) findViewById(R.id.option2_layout);

                option2layout.setVisibility(View.INVISIBLE);
            }

            if (foodOptions.size() == 2) {
                option1Textview.setText(foodOptions.get(0).getDescription());
                option2Textview.setText(foodOptions.get(1).getDescription());
                food_option_price1.setText(String.valueOf(foodOptions.get(0).getPrice()));
                food_option_price2.setText(String.valueOf(foodOptions.get(1).getPrice()));
            }
        }

        Button addtobasketbutton = (Button) findViewById(R.id.basket_button);

        addtobasketbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean option1, option2;

               option1 = FoodActivity.this.option1Switch.isChecked();
               option2 =  FoodActivity.this.option2Switch.isChecked();

               AppLogic.getInstance().getCurrentCustomer().addToBascket(food, option1, option2);
               Toast.makeText(FoodActivity.this, "Added to basket!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
