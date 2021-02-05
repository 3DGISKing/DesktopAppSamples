package com.dev.irsg.busassister.ui;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.irsg.busassister.AppLogic;
import com.dev.irsg.busassister.PassengerData;
import com.dev.irsg.busassister.PassengerDataGroup;
import com.dev.irsg.busassister.PassengerInfo;
import com.dev.irsg.busassister.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPoint;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GeoJsonLayer  mPassengersLayer;
    JourneyActivity       mJourneyActivity;
    LinearLayout          mPassengersInfoLayout;
    boolean               mMapViewHeightSetted;
    Marker                mBusMarker;
    private GoogleMap     mGoogleMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mPassengersInfoLayout = (LinearLayout) view.findViewById(R.id.fragment_map_passenger_info_layout);

        mMapViewHeightSetted = false;

        return view;
    }

    public void resetMapHeight() {
        if( mMapViewHeightSetted)
            return;

        mMapViewHeightSetted = true;

        View view = this.getView();

        if( view == null)
            return;

        LinearLayout fragment_map_layout = (LinearLayout) view.findViewById(R.id.fragment_map_layout);

        View mapFragmentView = view.findViewById(R.id.map);

        ViewGroup.LayoutParams params = mapFragmentView.getLayoutParams();

        params.height = fragment_map_layout.getHeight();
        mapFragmentView.setLayoutParams(params);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mJourneyActivity = ( JourneyActivity) getActivity();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            mGoogleMap = googleMap;

            GeoJsonLayer  onBoardingPointLayer;

            onBoardingPointLayer = new GeoJsonLayer(googleMap, R.raw.hyd_12_onboardpoints, mJourneyActivity);

            //initial positio is in Ashok_Nagar
            LatLng initialPosition = new LatLng(17.406283, 78.489987);
            mBusMarker = googleMap.addMarker(new MarkerOptions()
                    .position(initialPosition)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.busmarker))
                    .title("bus"));

            int padding = 50;//pixel

            LatLngBounds bound = onBoardingPointLayer.getBoundingBox();

            if (bound == null) {
                LatLngBounds.Builder builder = LatLngBounds.builder();

                for (GeoJsonFeature feature : onBoardingPointLayer.getFeatures()) {
                    GeoJsonPoint point= (GeoJsonPoint) feature.getGeometry();
                    builder.include(point.getCoordinates());
                }

                bound = builder.build();
            }

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bound, padding);
            googleMap.moveCamera(cu);

            AppLogic.getInstance().setOnBoardingPointLayer(onBoardingPointLayer);
            mPassengersLayer = new GeoJsonLayer(googleMap, R.raw.hyd_12_passengers, mJourneyActivity);

        } catch (IOException e) {
            System.out.println("IOException ");
        } catch (JSONException e) {
            System.out.println("JSONException");
        }

        AppLogic.getInstance().load(mPassengersLayer);

        mJourneyActivity.getPassengerListViewFragment().setAdapter();
        mJourneyActivity.startJourneySimulation();
    }

    public void notifyWillBeOnBoaredPassengers(int onBoardingPointOrder) {
        PassengerDataGroup group = AppLogic.getInstance().getPassengerDataGroupFromPickPointOrder(onBoardingPointOrder);

        if (group == null) {
            return;
        }

        ArrayList<PassengerData> passengerDataList = group.getPassengerDataList();

        for (int i = 0; i < passengerDataList.size(); i++) {
            final PassengerData data = passengerDataList.get(i);

            if ( !isAlreadyNotifiedWillBeOnBoardPassenger(data.getInfo().getPhoneNumber()))
                notifyWillBeOnBoaredPassenger(data);
        }
    }

    private boolean isAlreadyNotifiedWillBeOnBoardPassenger(final String passengerPhoneNumber) {
        int pageCount = mPassengersInfoLayout.getChildCount();

        for (int i = 0; i < pageCount; i++ ) {
            ViewPager pager = (ViewPager) mPassengersInfoLayout.getChildAt(i);

            PassengerInfoPagerAdapter adapter= (PassengerInfoPagerAdapter) pager.getAdapter();

            if (adapter.getPassengerInfo().getPhoneNumber().equals(passengerPhoneNumber))
                return true;
        }

        return false;
    }

    private void notifyWillBeOnBoaredPassenger(final PassengerData  data) {
        final ViewPager passengerViewPager = new ViewPager(mJourneyActivity);

        ViewPager.LayoutParams params = new ViewPager.LayoutParams();

       // screen dpi
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float screenDensity = metrics.density;
        int width = metrics.widthPixels;

        int dp = (int) (width/screenDensity);

        if(dp >= 480)
            params.height = (int) getResources().getDimension(R.dimen.passenger_info_page_height_large);

        if(dp >= 320 && dp < 480)
            params.height = (int) getResources().getDimension(R.dimen.passenger_info_page_height_normal);

        if(dp < 320 )
            params.height = (int) getResources().getDimension(R.dimen.passenger_info_page_height_small);

        passengerViewPager.setLayoutParams(params);

        final PassengerInfoPagerAdapter passengerPagerAdapter = new PassengerInfoPagerAdapter(mJourneyActivity, data);

        passengerViewPager.setAdapter(passengerPagerAdapter);

        passengerViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //swipe left
                if( position == 1) {
                    PassengerData passengerData = passengerPagerAdapter.getPassengerData();

                    if (passengerData.getState() == PassengerData.PassengerState.UNKNOWN) {
                        // considered as on_boarded
                        AppLogic.getInstance().setPassengerState(passengerData.getInfo().getPhoneNumber(), PassengerData.PassengerState.ON_BOARDED);
                        mJourneyActivity.getPassengerListViewFragment().notifyDataSetChanged();

                        Toast.makeText(mJourneyActivity, "Passenger: " + passengerData.getInfo().getName() + " on-boarded!", Toast.LENGTH_SHORT ).show();
                    }

                    if (passengerData.getState() == PassengerData.PassengerState.FAILED_ON_BOARDED) {

                    }

                    if (passengerData.getState() == PassengerData.PassengerState.CANCELLED_TICKET) {

                    }

                    if (passengerData.getState() == PassengerData.PassengerState.CHANGED_PICK_POINT) {

                    }

                    mPassengersInfoLayout.removeView(passengerViewPager);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPassengersInfoLayout.addView(passengerViewPager);

        Animation flowAnim = AnimationUtils.loadAnimation(mJourneyActivity, R.anim.flow);
        passengerViewPager.startAnimation(flowAnim);
    }

    public void notifyFailedOnboardPassenger(String passengerPhoneNumber) {
        ViewPager passengerViewPager = getPassengerViewPagerFromPassengerPhoneNumber(passengerPhoneNumber);

        if (passengerViewPager == null)
            return;

        View passengerInfoPage = passengerViewPager.getChildAt(1);

        if ( passengerInfoPage == null )
            return;

        ImageView imageView = (ImageView) passengerInfoPage.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.passengerinfo_mapview_failed_background);
    }

    private ViewPager getPassengerViewPagerFromPassengerPhoneNumber(String passengerPhoneNumber) {
        int passengerViewPagerCount = mPassengersInfoLayout.getChildCount();

        for (int i = 0; i < passengerViewPagerCount; i++) {
            ViewPager passengerViewPager = (ViewPager) mPassengersInfoLayout.getChildAt(i);

            PassengerInfoPagerAdapter adapter = (PassengerInfoPagerAdapter) passengerViewPager.getAdapter();

            PassengerData passengerData = adapter.getPassengerData();

            if( passengerData.getInfo().getPhoneNumber() == passengerPhoneNumber )
                return passengerViewPager;
        }

        return null;
    }

    public void notifyChangedOnboardPassenger(String passengerPhoneNumber, String pickPointName) {
        ViewPager passengerViewPager = getPassengerViewPagerFromPassengerPhoneNumber(passengerPhoneNumber);

        if (passengerViewPager == null)
            return;

        View passengerInfoPage = passengerViewPager.getChildAt(1);

        if (passengerInfoPage == null) {
            System.out.println("Internal error");
        }

        ImageView imageView = (ImageView) passengerInfoPage.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.passengerinfo_mapview_changed_background);

        TextView point = (TextView) passengerInfoPage.findViewById(R.id.passengerinfo_boardingpointname_textview);
        point.setText(pickPointName);
    }

    public void notifyCancelldPassenger(String passengerPhoneNumber) {
        ViewPager passengerViewPager = getPassengerViewPagerFromPassengerPhoneNumber(passengerPhoneNumber);

        if (passengerViewPager == null)
            return;

        View passengerInfoPage = passengerViewPager.getChildAt(1);

        ImageView imageView = (ImageView) passengerInfoPage.findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.passengerinfo_mapview_cancelled_background);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onBusPositionChanged(LatLng position) {
        mBusMarker.setPosition(position);

        if (! mGoogleMap.getProjection().getVisibleRegion().latLngBounds.contains(position)) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(position));
        }
    }

    public void panMap(LatLng position) {
        mBusMarker.setPosition(position);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(position));
    }

    public boolean removePassengerPager(String passengerPhoneNumber) {
        int passengerviewpagercount = mPassengersInfoLayout.getChildCount();
        for (int i = 0; i < passengerviewpagercount ; i++) {

            ViewPager passengerviewpager = (ViewPager) mPassengersInfoLayout.getChildAt(i);
            PassengerInfoPagerAdapter adapter = (PassengerInfoPagerAdapter) passengerviewpager.getAdapter();
            PassengerInfo info = adapter.getPassengerInfo();

            if ( info.getPhoneNumber() == passengerPhoneNumber) {

                mPassengersInfoLayout.removeView(passengerviewpager);

                return true;
            }
        }
        return false;
    }
}
