package com.dev.irsg.fooddeliverysystem.entities;

/**
 * Created by Ugi on 6/15/2017.
 */

public class FoodOption {
    String description;
    float  price;
    boolean enabled;

    public FoodOption()
    {
        this.description = "";
        this.price = 0;
        this.enabled = false;
    }

    public FoodOption(String description, float price, boolean enabled) {
        this.description = description;
        this.price = price;
        this.enabled = enabled;
    }

    public FoodOption(String description, float price) {
        this.description = description;
        this.price = price;
        this.enabled = false;
    }

    public String getDescription() { return description; }
    public float  getPrice() { return price; }
    public boolean getEnabled() { return enabled;}

    public void setDescription(String description) { this.description = description; }
    public void setPrice(float price) { this.price = price; }
    public void setEnbled(boolean enabled) { this.enabled = enabled; }
};