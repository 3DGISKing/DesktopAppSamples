package com.dev.irsg.busassister;

import java.util.ArrayList;

/**
 * Created by Ugi on 6/23/2017.
 *
 */

//order is consistent
public class PassengerDataGroup {
    private String                   mBoardingPointName;
    private String                   mBoardingTime;
    private ArrayList<PassengerData> mPassengerDataList;
    private int                      mOrder;

    public PassengerDataGroup(String name, String time, int order)
    {
        this.mBoardingPointName = name;
        this.mBoardingTime = time;
        this.mOrder = order;

        mPassengerDataList = new ArrayList<>();
    }

    public int getOrder() { return  this.mOrder; }

    public void add(PassengerData data) {
        mPassengerDataList.add(data);
    }
    public void remove(PassengerData data) { mPassengerDataList.remove(data);}

    public int size() { return mPassengerDataList.size(); }
    public PassengerData get(int index) { return mPassengerDataList.get(index); }

    public String getmBoardingPointName() { return mBoardingPointName; }
    public ArrayList<PassengerData> getPassengerDataList()  { return mPassengerDataList;}
    public String getBoardingTime() { return mBoardingTime; }

    public PassengerData getPassengerDataFromPhoneNumber( String passengerPhoneNumber) {
        for ( int i = 0; i < mPassengerDataList.size(); i++) {
            PassengerData data = mPassengerDataList.get(i);

            if ( data.getInfo().getPhoneNumber().equals( passengerPhoneNumber )) {
                return data;
            }
        }

        return null;
    }

    public void setOnBoarded(String passengerPhoneNumber) {
        PassengerData data = getPassengerDataFromPhoneNumber(passengerPhoneNumber);

        if ( data == null)
            return;

        data.setState(PassengerData.PassengerState.ON_BOARDED);
    }

    public void setFailed(String passengerPhoneNumber) {
        PassengerData data = getPassengerDataFromPhoneNumber(passengerPhoneNumber);

        if ( data == null)
            return;

        data.setState(PassengerData.PassengerState.FAILED_ON_BOARDED);
    }

    public void setCancelled(String passengerPhoneNumber) {
        PassengerData data = getPassengerDataFromPhoneNumber(passengerPhoneNumber);

        if ( data == null)
            return;

        data.setState(PassengerData.PassengerState.CANCELLED_TICKET);
    }

    public void setChanged(String passengerPhoneNumber) {
        PassengerData data = getPassengerDataFromPhoneNumber(passengerPhoneNumber);

        if ( data == null)
            return;

        data.setState(PassengerData.PassengerState.CHANGED_PICK_POINT);
    }
}
