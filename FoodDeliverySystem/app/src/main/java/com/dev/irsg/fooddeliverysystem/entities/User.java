package com.dev.irsg.fooddeliverysystem.entities;

import org.w3c.dom.UserDataHandler;

import java.net.UnknownServiceException;

/**
 * Created by Ugi on 6/13/2017.
 */

public class User {
    String email; // User email
    String phone; // User phone number
    String password; // User password
    String card; // User card number
    String address; // User address

    public User() {
        email = "";
    }

    public User(String email, String phone, String password, String card, String address) {
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.card = card;
        this.address = address;
    }

    // Get e-mail
    public String getEmail() {
        return email;
    }
    // Set e-mail
    public void setEmail(String email) {
        this.email = email;
    }
    // Get phone
    public String getPhone() {
        return phone;
    }
    // Set phone
    public void setPhone(String phone) {
        this.phone = phone;
    }
    // Get password
    public String getPassword() {
        return password;
    }
    // Set password
    public void setPassword(String password) {
        this.password = password;
    }
    // Get card
    public String getCard() {
        return card;
    }
    // Set card
    public void setCard(String card) {
        this.card = card;
    }
    // Get address
    public String getAddress() {
        return address;
    }
    // Set address
    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isValid() { return  email != ""; }

    @Override
    public boolean equals(Object o) {
        if ( this == o )
            return true;

        if( o == null || getClass() != o.getClass() )
            return false;

        User user = (User) o;

        if( this.email != user.getEmail())
            return false;

        if( this.phone != user.getPhone())
            return false;

        if( this.password != user.getPassword())
            return false;

        if( this.card != user.getCard())
            return false;

        if( this.address != user.getAddress())
            return false;

        return true;
    }
 }
