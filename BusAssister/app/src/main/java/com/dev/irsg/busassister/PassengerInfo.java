package com.dev.irsg.busassister;

/**
 * Created by Ugi on 6/28/2017.
 *
 */

public class PassengerInfo {
    private String  mName;
    private String  mPhoneNumber;
    private int     mSeatNo;
    private int     mBoardingPointOrder;
    private String  mBoardingPointName;
    private String  mOnBoardingTime;
    private double  mLatitude;
    private double  mLongitude;

    public PassengerInfo(String name, String phonenumber, int seatno, int order, String pointname, String time, double latitude, double longitude) {
        mName = name;
        mPhoneNumber = phonenumber;
        mSeatNo = seatno;
        mBoardingPointOrder = order;
        mBoardingPointName = pointname;
        mOnBoardingTime = time;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public String getName() { return mName; }
    public String getPhoneNumber() { return mPhoneNumber; }
    public int    getSeatNo() { return mSeatNo; }
    public int    getOrder() { return mBoardingPointOrder; }
    public String getBoardingPointName() { return mBoardingPointName; }
    public String getBoardingTime() { return mOnBoardingTime; }

    public void   setBoardPointName(String name) { mBoardingPointName = name; }
}