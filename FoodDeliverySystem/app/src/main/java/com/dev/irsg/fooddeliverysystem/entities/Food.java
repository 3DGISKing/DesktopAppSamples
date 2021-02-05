package com.dev.irsg.fooddeliverysystem.entities;

/**
 * Created by Ugi on 6/13/2017.
 */

import android.support.v4.view.ViewCompat;

import java.util.ArrayList;


public class Food {
    String category;
    String name;
    String imageURL;
    String description;
    float  price;

    ArrayList<FoodOption> options;

    public Food() {}

    public Food(String category, String name, String imageURL, String description, float price) {
        this.category = category;
        this.name = name;
        this.imageURL = imageURL;
        this.description = description;
        this.price = price;
    }

    public Food newInstance(boolean op1, boolean op2)
    {
        Food newfood = new Food(this.category, this.name, this.imageURL, this.description, this.price);

        if(op1 || op2) {
            ArrayList<FoodOption> newoptions = new ArrayList<FoodOption>();
            newfood.setOptions(newoptions);

            if(op1) {
                FoodOption option1 = new FoodOption(this.getOptions().get(0).getDescription(), this.getOptions().get(0).getPrice(), true);
                newoptions.add(option1);
            }

            if(op2) {
                FoodOption option2 = new FoodOption(this.getOptions().get(1).getDescription(), this.getOptions().get(1).getPrice(), true);
                newoptions.add(option2);
            }
        }

        return newfood;
    }

    public String getCategory() { return category; }
    public String getName() { return  name;}
    public String getImageURL() { return  imageURL;}
    public String getDescription() { return  description;}
    public float  getPrice()
    {
        if (options == null)
            return  price;

        float totalprice = price;

        for (int i = 0; i < options.size(); i++)
            if ( options.get(i).getEnabled() == true)
                totalprice = totalprice + options.get(i).getPrice();

        return totalprice;
    }

    public String getOptionName()
    {
        if (options == null)
            return "";

        if(options.size() == 1 && options.get(0).getEnabled() == true) {
            return "Option1";
        }
        else if ( options.size() ==2) {
            if (options.get(0).getEnabled() == true && options.get(1).getEnabled() == false)
                return "Option1";
            else if ( options.get(0).getEnabled() == false && options.get(1).getEnabled() == true)
                return "Option2";
            else if ( options.get(0).getEnabled() == true && options.get(1).getEnabled() == true)
                return "Option12";
        }

        return "";
    }

    public ArrayList<FoodOption> getOptions() { return  options; }

    public void setCategory(String category) { this.category = category; }
    public void setName(String name) { this.name = name; }
    public void setImageURL(String imageURL) { this.imageURL = imageURL; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(float price) { this.price = price; }
    public void setOptions(ArrayList<FoodOption> options) { this.options = options; }
}
