package com.dev.irsg.fooddeliverysystem.entities;

import java.util.ArrayList;

/**
 * Created by Ugi on 6/13/2017.
 */

public class Customer extends User {
    ArrayList<Order> orderList;
    Order basket = new Order();

    public Customer() {
        super();
    }

    public Customer(String email, String phone, String password, String card, String address) {
        super(email, phone, password, card, address);
    }

    public void addToBascket(Food food, boolean option1 , boolean option2  )
    {
        basket.add(food, option1, option2);
    }

    public Order getBasket() { return basket; }
}
