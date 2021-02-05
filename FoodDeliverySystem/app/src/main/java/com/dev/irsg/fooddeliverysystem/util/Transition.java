package com.dev.irsg.fooddeliverysystem.util;

/**
 * Created by Ugi on 6/15/2017.
 */

public interface Transition {

    /**
     * Transit to another activity.
     */
    void transit();

    /**
     * Show the toast.
     * @param str string
     */
    void setToast(String str);
}

