package com.dev.irsg.fooddeliverysystem.customerui;

/**
 * Created by Ugi on 6/14/2017.
 */

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.dev.irsg.fooddeliverysystem.R;

import com.dev.irsg.fooddeliverysystem.entities.Food;
import com.dev.irsg.fooddeliverysystem.entities.FoodSubMenu;


public class FoodListAdapter extends BaseAdapter{

    Context         context;
    LayoutInflater  inflater;
    FoodSubMenu     foodSubMenu;

    public FoodListAdapter(Context context, FoodSubMenu subMenu) {
        super();

        this.context = context;
        this.foodSubMenu = subMenu;
    }

    @Override
    public int getCount() {
        return foodSubMenu.getFoodCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView == null ) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.food_layout, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.food_imageview);
        TextView textView = (TextView) convertView.findViewById(R.id.food_name_price_textview);
        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.food_image_loading_progressbar);

        progressBar.setVisibility(View.INVISIBLE);

        Food food = foodSubMenu.getFood(position);

        String path = "Images/food/" + food.getImageURL();

        StorageReference ref = FirebaseStorage.getInstance().getReference().child(path);

        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(ref)
                /*.listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })*/
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .override(512, 512)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imageView);


        textView.setText(food.getName() + " (" + String.valueOf(food.getPrice()) + ")");
        return  convertView;
    }
}
