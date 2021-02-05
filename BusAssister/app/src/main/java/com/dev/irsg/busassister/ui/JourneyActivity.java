package com.dev.irsg.busassister.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dev.irsg.busassister.AppLogic;
import com.dev.irsg.busassister.PassengerData;
import com.dev.irsg.busassister.PassengerDataGroup;
import com.dev.irsg.busassister.R;
import com.google.android.gms.maps.model.LatLng;

public class JourneyActivity extends FragmentActivity {
    private final static int PERMISSIONS_REQUEST_CALL_PHONE = 999;

    private MapFragment                      mMapFragment;
    private PassengerListFragment            mPassengerListFragment;

    //Now bus is running to Onboarding Point of which Order is equal to mCurrentBoardingPointOrder;
    private int                              mCurrentBoardingPointOrder;
    OnBoardServiceSimulator                  mSimulator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey);

        mMapFragment = new MapFragment();
        mPassengerListFragment = new PassengerListFragment();

        JourneyPagerAdapter  pagerAdapter = new JourneyPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

        createTabIcons();

        Button homeButton = (Button) findViewById(R.id.home_button);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectServiceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                                Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    private void createTabIcons() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        final ViewGroup nullParent = null;
        View mapFragmentTabIconView =  LayoutInflater.from(this).inflate(R.layout.mapview_tab_icon, nullParent);

        TabLayout.Tab mapFragmentTab = tabLayout.getTabAt(0);

        if( mapFragmentTab != null )
            mapFragmentTab.setCustomView(mapFragmentTabIconView);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#71CDF5"));

        View passengerListFragmentTabIconView =  LayoutInflater.from(this).inflate(R.layout.listview_tab_icon, nullParent);

        TabLayout.Tab passengerListFragmentTab = tabLayout.getTabAt(1);

        if( passengerListFragmentTab != null )
          passengerListFragmentTab.setCustomView(passengerListFragmentTabIconView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mMapFragment.resetMapHeight();
    }

   PassengerListFragment getPassengerListViewFragment() { return mPassengerListFragment; }

   private class JourneyPagerAdapter extends FragmentPagerAdapter {

        JourneyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0 )
                return JourneyActivity.this.mMapFragment;
            else
                return JourneyActivity.this.mPassengerListFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if ( position == 0)
                return "Map View";
            else
                return "List View";
        }
    }

    public void startJourneySimulation() {
        //boarding point order start from 1
        mCurrentBoardingPointOrder = 1;

        mSimulator = new OnBoardServiceSimulator(this);

        mSimulator.start();

        mMapFragment.notifyWillBeOnBoaredPassengers(mCurrentBoardingPointOrder);

        PassengerDataGroup group = AppLogic.getInstance().getPassengerDataGroupFromPickPointOrder(mCurrentBoardingPointOrder);

        Toast.makeText(this, "Bus arrived at " + group.getmBoardingPointName() , Toast.LENGTH_LONG).show();

        LatLng initialLatLng = AppLogic.getInstance().getOnBoardingPointLatLng(mCurrentBoardingPointOrder);

        mMapFragment.panMap(initialLatLng);
    }

    private void notifyChangedOnboardPassenger(String passengerName, String pickPointName) {
        PassengerData passengerData = AppLogic.getInstance().getPassengerDataFromPassengerName(passengerName);

        if (passengerData == null )
            return;

        AppLogic.getInstance().setChangedPassengerState(passengerData.getInfo().getPhoneNumber(), pickPointName);

        mMapFragment.notifyChangedOnboardPassenger(passengerData.getInfo().getPhoneNumber(), pickPointName);
        mPassengerListFragment.notifyDataSetChanged();

        Toast.makeText(this, "Passenger: " + passengerName + " 's pick point changed from "+ passengerData.getInfo().getBoardingPointName() + " to " + pickPointName, Toast.LENGTH_SHORT ).show();
    }

    private void notifyCancelledPassenger(String passengerName) {
        PassengerData passengerData = AppLogic.getInstance().getPassengerDataFromPassengerName(passengerName);

        if (passengerData == null )
            return;

        AppLogic.getInstance().setPassengerState(passengerData.getInfo().getPhoneNumber(), PassengerData.PassengerState.CANCELLED_TICKET);

        mMapFragment.notifyCancelldPassenger(passengerData.getInfo().getPhoneNumber());
        mPassengerListFragment.notifyDataSetChanged();

        Toast.makeText(this, "Passenger: " + passengerData.getInfo().getName() + " canceled!", Toast.LENGTH_SHORT ).show();
    }

    private void notifyFailedOnboardPassengers(int pickPointOrder) {
        PassengerDataGroup passengerDataGroup = AppLogic.getInstance().getPassengerDataGroupFromPickPointOrder(pickPointOrder);

        if (passengerDataGroup == null) {
            System.out.print("Error exists in passenger data!");
            return;
        }

        for ( int i = 0; i < passengerDataGroup.size(); i++) {
            PassengerData passengerData = passengerDataGroup.get(i);

            if ( passengerData.getState() == PassengerData.PassengerState.UNKNOWN)
                passengerData.setState(PassengerData.PassengerState.FAILED_ON_BOARDED);

            if ( passengerData.getState() == PassengerData.PassengerState.CANCELLED_TICKET)
                passengerData.setState(PassengerData.PassengerState.FAILED_ON_BOARDED);

            mMapFragment.notifyFailedOnboardPassenger(passengerData.getInfo().getPhoneNumber());
        }

        mPassengerListFragment.notifyDataSetChanged();
    }

    public void advanceNextOnBoardPoint() {
        mCurrentBoardingPointOrder = mCurrentBoardingPointOrder + 1;

        LatLng latLng = AppLogic.getInstance().getOnBoardingPointLatLng(mCurrentBoardingPointOrder);
        mMapFragment.panMap(latLng);

        notifyFailedOnboardPassengers(mCurrentBoardingPointOrder - 1);
        mPassengerListFragment.notifyDataSetChanged();

        if (mCurrentBoardingPointOrder == AppLogic.getInstance().getBoardingPointCount() ) {
            Toast.makeText(this, "Journey ended!", Toast.LENGTH_LONG).show();
            endJourney();
            return;
        }

        PassengerDataGroup group = AppLogic.getInstance().getPassengerDataGroupFromPickPointOrder(mCurrentBoardingPointOrder);

        if( group == null )
            return;

        Toast.makeText(this, "Bus arrived at " + group.getmBoardingPointName() , Toast.LENGTH_LONG).show();

        mMapFragment.notifyWillBeOnBoaredPassengers(mCurrentBoardingPointOrder);

        if ( mCurrentBoardingPointOrder == 4)
            notifyChangedOnboardPassenger("Nayeem", "Moosapet");

        if( mCurrentBoardingPointOrder == 6 )
            notifyCancelledPassenger("Sairam");
        }

    public void endJourney() {
        stopJourneySimulation();

        //change interface
        LinearLayout topLayout = (LinearLayout) findViewById(R.id.activity_journey_top_layout);

        LinearLayout mainContent = (LinearLayout) findViewById(R.id.activity_journey_main_content);

        topLayout.removeView(mainContent);

        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ViewGroup nullParent = null;
        LinearLayout complete_on_board_layout = (LinearLayout) inflater.inflate(R.layout.complete_on_boarding , nullParent);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;

        complete_on_board_layout.setLayoutParams(params);

        Button homeButton = (Button) findViewById(R.id.home_button);

        topLayout.removeView(homeButton);

        topLayout.addView(complete_on_board_layout);
        topLayout.addView(homeButton);

        //event

        Button complete_on_board_button = (Button) findViewById(R.id.complete_on_boarding_button);

        complete_on_board_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectServiceActivity.class);

                JourneyActivity.this.startActivity(intent);
                JourneyActivity.this.finish();
            }
        } );
    }

    public void callPhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_CALL_PHONE);
        }
        else {
            try {
                this.startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the phone call
                    System.out.println("permission granted");
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    System.out.println("permission denied");
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopJourneySimulation();
    }

    public void stopJourneySimulation() {
       mSimulator.stop();
    }

    LatLng getCurrentBoardingPointLatLng() {
        return AppLogic.getInstance().getOnBoardingPointLatLng(mCurrentBoardingPointOrder);
    }

    LatLng getNextBoardingPointLatLng() {
        return AppLogic.getInstance().getOnBoardingPointLatLng(mCurrentBoardingPointOrder + 1);
    }

    public int getCurrentBoardingPointOrder() {
        return mCurrentBoardingPointOrder;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    public void onBusPositionChanged(LatLng position) {
        System.out.println(position.toString());

        mMapFragment.onBusPositionChanged(position);
    }

    public boolean removePassengerInfoPager(String passengerPhoneNumer) {
        return mMapFragment.removePassengerPager(passengerPhoneNumer);
    }
}