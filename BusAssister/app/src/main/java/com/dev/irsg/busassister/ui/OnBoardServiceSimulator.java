package com.dev.irsg.busassister.ui;

import android.os.Handler;

import com.dev.irsg.busassister.LatLngInterpolator;
import com.google.android.gms.maps.model.LatLng;


/**
 * Created by Ugi on 6/22/2017.
 *
 */

class OnBoardServiceSimulator {
    private JourneyActivity mJourneyActivity;
    private Handler handler = new Handler();

    private final long mCountOfSimulationPositionInBoardingPoints = 100;
    private final long mBusPositionSimulationInterval = 200;
    private int   mCountOfSimulation;
    private LatLngInterpolator latLngInterpolator;

    private boolean mContinue;

    OnBoardServiceSimulator(JourneyActivity ac)
    {
        this.mJourneyActivity = ac;
        mContinue = true;
        mCountOfSimulation = 0;
        latLngInterpolator = new LatLngInterpolator.Linear();
    }

    void start() {
        handler.post(new Runnable() {

            @Override
            public void run() {
                if ( mJourneyActivity == null )
                    return;

                //Bus arrived at next boarding point
                if (mCountOfSimulation == mCountOfSimulationPositionInBoardingPoints) {
                    mJourneyActivity.advanceNextOnBoardPoint();
                    mCountOfSimulation = 0;
                }

                mCountOfSimulation++;

                //simulate bus position

                if (mContinue) {

                   /*
                    LatLng simulatedLatLng = getSimulationPosition(mCountOfSimulation);
                    mJourneyActivity.onBusPositionChanged(simulatedLatLng);

                    */

                    handler.postDelayed(this, mBusPositionSimulationInterval);
                }

            }
        });
    }

    private LatLng getSimulationPosition(int simulationIndex) {
        LatLng currentPosition = mJourneyActivity.getCurrentBoardingPointLatLng();

        LatLng nextPosition = mJourneyActivity.getNextBoardingPointLatLng();

        float fraction = (float) simulationIndex / (float) mCountOfSimulationPositionInBoardingPoints;

        return latLngInterpolator.interpolate(fraction, currentPosition, nextPosition);
    }

    void stop() {
        mContinue = false;
        mJourneyActivity = null;
        handler = null;
        latLngInterpolator = null;
    }
}
