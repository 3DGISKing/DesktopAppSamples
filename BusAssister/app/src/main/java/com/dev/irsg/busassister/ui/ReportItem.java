package com.dev.irsg.busassister.ui;

/**
 * Created by jinming on 7/6/2017.
 */

public class ReportItem {
    String mPassengerName;
    String mPassengerSeatNo;
    String mPickPointName;

    public ReportItem(String passengerName, String passengerSeatNo, String pickPointName){
        this.mPassengerName = passengerName;
        this.mPassengerSeatNo = passengerSeatNo;
        this.mPickPointName = pickPointName;
    }

    public String getPassengerName() {
        return mPassengerName;
    }
    public void setPassengerName(String name) {
        this.mPassengerName = name;
    }

    public String getPassengerSeatNo() {
        return mPassengerSeatNo;
    }

    public String getPickPointName() {
        return mPickPointName;
    }
}
