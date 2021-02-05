package com.dev.irsg.fooddeliverysystem;

/**
 * Created by Ugi on 6/15/2017.
 */
import com.dev.irsg.fooddeliverysystem.entities.RestaurantAdmin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.dev.irsg.fooddeliverysystem.entities.User;
import com.dev.irsg.fooddeliverysystem.entities.Customer;

public class AppLogic {
    public final boolean USE_FOR_CUSTOMER = true;
    public final static String PAYPAL_CLIENT_ID = "AXSfp6pEsiW877-H5MyeUHIecI7j7xet3oN8NpsjZwlUS3_MtJQ8ex709t2CctIOKYzwVEEtNh4_I_M4";

    private Customer currentCustomer;
    private RestaurantAdmin currentResAdmin;

    private static AppLogic instance;

    public static AppLogic getInstance() {
        if (instance == null)
            instance = new AppLogic();

        return instance;
    }

    private void AppLogic() {
        currentCustomer = new Customer();
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void createCurrentCustomer(User user) {
        currentResAdmin = null;

        currentCustomer = new Customer();

        currentCustomer.setEmail(user.getEmail());
        currentCustomer.setPassword(user.getPassword());
        currentCustomer.setAddress(user.getAddress());
        currentCustomer.setPhone(user.getPhone());
        currentCustomer.setCard(user.getCard());
    }

    public void createRestaurantAdmin(User user) {
        currentCustomer = null;
        currentResAdmin = new RestaurantAdmin();

        currentResAdmin.setEmail(user.getEmail());
        currentResAdmin.setPassword(user.getPassword());
        currentResAdmin.setAddress(user.getAddress());
        currentResAdmin.setPhone(user.getPhone());
        currentResAdmin.setCard(user.getCard());
    }

    public boolean isForCustomer() {
        if (currentCustomer != null)
            return true;

        return false;
    }

    public boolean isForRestaurantAdmin() {
        if (currentResAdmin != null)
            return true;

        return false;
    }

}