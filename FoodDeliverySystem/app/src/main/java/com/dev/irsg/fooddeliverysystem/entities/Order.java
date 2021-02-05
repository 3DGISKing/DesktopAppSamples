package com.dev.irsg.fooddeliverysystem.entities;

/**
 * Created by Ugi on 6/13/2017.
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    private final static String NAME_OPTION_SPLITTER = "-";
    private final static String OPTION1_MARKER = "Option1";
    private final static String OPTION2_MARKER = "Option2";
    private final static String OPTION12_MARKER = "Option12";

    double price; // Food price
    String status; // Order status

    public class FoodAndCount{
        Food food;
        int  count;

        public  FoodAndCount(Food food, int count)
        {
            this.food = food;
            this.count = count;
        }

        public int getCount() { return count; }
        public Food getFood() { return food; }
    }

    //key: food name
    //value count;

    HashMap<String, Integer> foodMap;

    public Order()
    {
        foodMap = new HashMap<String, Integer>();
    }

    public static String getKey(Food food, boolean option1, boolean option2)
    {
        String key = food.getName();

        if ( option1 == true && option2 == false)
            key = key + NAME_OPTION_SPLITTER + OPTION1_MARKER;
        else if (option1 == false && option2 == true)
            key = key + NAME_OPTION_SPLITTER + OPTION2_MARKER;
        else if (option1 == true && option2 == true)
            key = key +NAME_OPTION_SPLITTER + OPTION12_MARKER;

        return key;
    }

    public void add(Food food, boolean option1, boolean option2)
    {
       String key = getKey(food, option1, option2);

       if ( foodMap.containsKey(key)) {
           Integer count = (Integer) foodMap.get(key);

           count++;

           foodMap.put(key, count);
       } else {
           foodMap.put(key, 1);
       }
    }

    public float calculateTotalPrice()
    {
        float totalprice = 0;
        float price;
        Food food;

        for (String key : foodMap.keySet()) {
            Integer count = foodMap.get(key);

            String[] parts = key.split(NAME_OPTION_SPLITTER);

            if (parts.length == 1) {
                food = FoodMenu.getInstance().getFood(key);

                price = food.getPrice() * count;
            } else { // length = 2
                String foodname = parts[0];
                String option = parts[1];

                food = FoodMenu.getInstance().getFood(foodname);

                ArrayList<FoodOption> options = food.getOptions();

                price = food.getPrice();

                if (option.equals( OPTION1_MARKER)) {
                     price = price + options.get(0).getPrice();
                } else if (option.equals( OPTION2_MARKER )) {
                    price = price + options.get(1).getPrice();
                } else if (option.equals( OPTION12_MARKER )) {
                    price = price + options.get(0).getPrice();
                    price = price + options.get(1).getPrice();
                } else {
                    price = food.getPrice();
                    System.out.println("Unreachable code");
                }

                price = price * count;
             }

            totalprice = totalprice + price;
        }

        return totalprice;
    }

     public ArrayList<FoodAndCount> getFoodAndCountList()
    {
        ArrayList<FoodAndCount> foodAndCountList = new ArrayList<FoodAndCount>();

        Food food;

        for (String key : foodMap.keySet()) {
            Integer count = foodMap.get(key);

            String[] parts = key.split(NAME_OPTION_SPLITTER);

            if (parts.length == 1) {
                food = FoodMenu.getInstance().getFood(key);
            } else { // length = 2
                String foodname = parts[0];
                String option = parts[1];

                boolean op1 = false, op2 = false;

                if (option.equals( OPTION1_MARKER)) {
                    op1 = true;
                } else if (option.equals( OPTION2_MARKER )) {
                    op2 = true;
                } else if (option.equals( OPTION12_MARKER )) {
                    op1 = true;
                    op2 = true;
                } else {
                    System.out.println("Unreachable code");
                }

                Food samplefood = FoodMenu.getInstance().getFood(foodname);

                food = samplefood.newInstance(op1, op2);
            }

            FoodAndCount foodAndCount = new FoodAndCount(food, count.intValue());

            foodAndCountList.add(foodAndCount);
        }

        return foodAndCountList;
    }
}
