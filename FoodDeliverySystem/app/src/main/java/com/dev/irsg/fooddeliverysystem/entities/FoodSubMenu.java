package com.dev.irsg.fooddeliverysystem.entities;

/**
 * Created by Ugi on 6/13/2017.
 */

import java.util.ArrayList;

public class FoodSubMenu {
    String category;
    ArrayList<Food> foodList = new ArrayList<Food>();

    public String getCategory() { return category; }

    public FoodSubMenu(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o )
            return true;

        if( o == null || getClass() != o.getClass() )
            return false;

        FoodSubMenu submenu = (FoodSubMenu) o;

        return category.equals(submenu.getCategory() );
    }

    public void addFood(Food food) {
        foodList.add(food);
    }
    public int  getFoodCount() { return foodList.size(); }

    public Food getFood(int index) { return foodList.get(index); }
    public Food getFood(String foodname)
    {
        for (int i = 0; i < foodList.size() ;i++ ) {
            if( foodList.get(i).getName().equals(foodname))
                return foodList.get(i);
        }

        return null;
    }
}
