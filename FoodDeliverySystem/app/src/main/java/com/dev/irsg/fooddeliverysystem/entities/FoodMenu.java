package com.dev.irsg.fooddeliverysystem.entities;

/**
 * Created by Ugi on 6/13/2017.
 */

import java.util.ArrayList;

public class FoodMenu {
    private static FoodMenu instance;

    ArrayList<FoodSubMenu> subMenuList = new ArrayList<FoodSubMenu>();

    private FoodMenu() {
    }

    public static FoodMenu getInstance() {
        if (instance == null)
            instance = new FoodMenu();

        return instance;
    }

    public int getSubMenuCount() {
        return subMenuList.size();
    }

    public void addFood(Food food) {
        String category = food.getCategory();

        FoodSubMenu subMenu = getSubMenu(category);

        if (subMenu == null) {
            subMenu = new FoodSubMenu(category);
            addSubMenu(subMenu);
        }

        subMenu.addFood(food);
    }

    public FoodSubMenu getSubMenu(String category) {

        for (int counter = 0; counter < subMenuList.size(); counter++) {
            FoodSubMenu subMenu = subMenuList.get(counter);

            if (subMenu.getCategory().equals(category))
                return subMenu;
        }

        return null;
    }

    public FoodSubMenu getSubMenu(int index) {
        if (index < 0 || index > subMenuList.size() - 1) {
            return null;
        }

        FoodSubMenu subMenu = subMenuList.get(index);

        return subMenu;
    }

    public void addSubMenu(FoodSubMenu subMenu) {
        subMenuList.add(subMenu);
    }

    public Food getFood(String foodname) {
        for (int counter = 0; counter < subMenuList.size(); counter++) {
            FoodSubMenu subMenu = subMenuList.get(counter);

            Food food = subMenu.getFood(foodname);

            if (food != null)
                return food;
        }

        return null;
    }
}