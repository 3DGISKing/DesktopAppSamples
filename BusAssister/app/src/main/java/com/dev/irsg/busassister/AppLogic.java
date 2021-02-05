package com.dev.irsg.busassister;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Ugi on 6/23/2017.
 * Application logic
 */

public class AppLogic {
    public static final boolean SIMPLE = false;
    private static AppLogic instance;
    private String mCurrentService;

    public static AppLogic getInstance() {
        if (instance == null)
            instance = new AppLogic();

        return instance;
    }

    private GeoJsonLayer                  mOnBoardingPointLayer;
    private ArrayList<PassengerDataGroup> mPassengerDataGroupList;

    private AppLogic() {
        mPassengerDataGroupList = new ArrayList<>();
        mCurrentService = "";
    }

    public int getPassengerDataGroupCount() {
        return mPassengerDataGroupList.size();
    }

    public PassengerDataGroup getPassengerDataGroup(int index) {
        return mPassengerDataGroupList.get(index);
    }

    public boolean setPassengerState(String passengerPhoneNumber, PassengerData.PassengerState state) {
        for (int i = 0; i < mPassengerDataGroupList.size(); i++) {
            PassengerDataGroup group = mPassengerDataGroupList.get(i);

            PassengerData data = group.getPassengerDataFromPhoneNumber(passengerPhoneNumber);

            if (data != null) {
                data.setState(state);
                return true;
            }
        }

        return false;
    }

    public PassengerData getPassengerDataFromPassengerName(String passengerName){
        for (int i = 0; i < mPassengerDataGroupList.size(); i++) {
            for (int j = 0; j < mPassengerDataGroupList.get(i).size(); j++) {
                PassengerData passengerData = mPassengerDataGroupList.get(i).get(j);

                if (passengerData.getInfo().getName().equals(passengerName))
                    return passengerData;
            }
        }

        return null;
    }

    public PassengerDataGroup getPassengerDataGroupFromPassengerPhoneNumber(String phoneNumber) {
        for (int i = 0; i < mPassengerDataGroupList.size(); i++) {
            PassengerDataGroup group = mPassengerDataGroupList.get(i);

            PassengerData data = group.getPassengerDataFromPhoneNumber(phoneNumber);

            if (data != null) {
                return group;
            }
        }

        return null;
    }

    public PassengerDataGroup getPassengerDataGroupFromPickPointName(String pickPointName) {
        for (int i = 0; i < mPassengerDataGroupList.size(); i++) {
            PassengerDataGroup group = mPassengerDataGroupList.get(i);

            if ( group.getmBoardingPointName().equals(pickPointName))
                return group;

        }

        return null;
    }

    public PassengerDataGroup getPassengerDataGroupFromPickPointOrder(int order ) {
        for (int i = 0; i < mPassengerDataGroupList.size(); i++) {
            if (mPassengerDataGroupList.get(i).getOrder() == order) {
                return mPassengerDataGroupList.get(i);
            }
        }

        return null;
    }

    public boolean setChangedPassengerState(String passengerPhoneNumber, String changedPickPointName) {
        PassengerDataGroup orig = getPassengerDataGroupFromPassengerPhoneNumber(passengerPhoneNumber);

        if (orig == null)
            return false;

        PassengerDataGroup dest = getPassengerDataGroupFromPickPointName(changedPickPointName);

        if (dest == null)
            return false;

        PassengerData data = orig.getPassengerDataFromPhoneNumber(passengerPhoneNumber);

        if (data == null)
            return false;

        orig.remove(data);

        data.getInfo().setBoardPointName(changedPickPointName);
        data.setState(PassengerData.PassengerState.CHANGED_PICK_POINT);
        dest.add(data);

        return true;
    }

    public ArrayList<PassengerDataGroup> getCurrentPassengerGroupList() {
        return mPassengerDataGroupList;
    }

    public void load(GeoJsonLayer passengerlayer) {
        mPassengerDataGroupList.clear();

        ArrayList<PassengerInfo> arr = new ArrayList<>();

        for (GeoJsonFeature feature : passengerlayer.getFeatures()) {
            PassengerInfo info = new PassengerInfo(feature.getProperty("Passenger Name").trim(),
                    feature.getProperty("Phone Number").trim(),
                    Integer.parseInt(feature.getProperty("Seat No").trim()),
                    Integer.parseInt(feature.getProperty("Boarding Point Order").trim()),
                    feature.getProperty("On Boarding Point").trim(),
                    feature.getProperty("On-Boarding Time").trim(),
                    Double.parseDouble(feature.getProperty("Latitude").trim()),
                    Double.parseDouble(feature.getProperty("Longitude").trim()));

            arr.add(info);
        }

        //sort

        Collections.sort(arr, new Comparator<PassengerInfo>(){
            public int compare(PassengerInfo s1, PassengerInfo s2) {
                return s1.getOrder() - s2.getOrder();
            }
        });

        int order = 0;

        PassengerDataGroup group = null;

        for (int i = 0; i < arr.size(); i++) {
            if (order == arr.get(i).getOrder()) {
                if (group != null) {
                    PassengerData data = new PassengerData(arr.get(i));
                    group.add(data);
                }
            } else {
                order = arr.get(i).getOrder();

                group = new PassengerDataGroup(arr.get(i).getBoardingPointName(), arr.get(i).getBoardingTime(), order);

                PassengerData data = new PassengerData(arr.get(i));
                group.add(data);

                mPassengerDataGroupList.add(group);
            }
        }
    }

    public int getBoardingPointCount() {

        int count = 0;

        for (GeoJsonFeature feature : mOnBoardingPointLayer.getFeatures()) {
           count++;
        }

        return count;
    }

    public LatLng getOnBoardingPointLatLng(int order) {
        for (GeoJsonFeature feature : mOnBoardingPointLayer.getFeatures()) {
            String strorder = feature.getProperty("Order");

            if(Integer.parseInt(strorder) == order) {
                GeoJsonPoint point= (GeoJsonPoint) feature.getGeometry();

                return point.getCoordinates();
            }
        }

        return null;
    }

    public void setOnBoardingPointLayer(GeoJsonLayer layer ) {
        this.mOnBoardingPointLayer = layer;
    }

    public String getCurrentService() { return mCurrentService; }
    public void   setCurrentService(String service) { this.mCurrentService = service; }

    private int calculatePassengerCountForState(PassengerData.PassengerState state) {
        int count = 0;

        for (int i = 0; i < mPassengerDataGroupList.size(); i++) {
            for (int j = 0; j < mPassengerDataGroupList.get(i).size(); j++) {
                PassengerData passengerData = mPassengerDataGroupList.get(i).get(j);

                if (passengerData.getState() == state)
                    count++;
            }
        }

        return count;
    }

    public int calculateOnboardedPassengerCount() {
        return calculatePassengerCountForState(PassengerData.PassengerState.ON_BOARDED);
    }

    public int calculateFailedOnboardPassengerCount() {
        return calculatePassengerCountForState(PassengerData.PassengerState.FAILED_ON_BOARDED);
    }

    public int calculateCanceledPassengerCount() {
        return calculatePassengerCountForState(PassengerData.PassengerState.CANCELLED_TICKET);
    }

    public int calculateChangedPassengerCount() {
        return calculatePassengerCountForState(PassengerData.PassengerState.CHANGED_PICK_POINT);
    }
}
