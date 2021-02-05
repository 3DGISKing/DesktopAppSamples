package com.dev.irsg.busassister;

/**
 * Created by Ugi on 6/23/2017.
 *
 */

public class PassengerData {
    public enum PassengerState{
        UNKNOWN,
        ON_BOARDED,
        FAILED_ON_BOARDED,
        CANCELLED_TICKET,
        CHANGED_PICK_POINT
    }

    private PassengerState mState;
    private PassengerInfo  mInfo;

    public PassengerData(PassengerInfo info) {
        mInfo  = info;
        mState = PassengerState.UNKNOWN;
    }

    public PassengerState getState() { return mState; }
    public PassengerInfo  getInfo() { return mInfo; }

    public void setState(PassengerState state) { mState = state; }
    public void setInfo(PassengerInfo info) { mInfo = info; }
  }

