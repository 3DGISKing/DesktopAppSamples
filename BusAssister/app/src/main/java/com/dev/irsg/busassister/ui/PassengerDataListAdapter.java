package com.dev.irsg.busassister.ui;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.irsg.busassister.AppLogic;
import com.dev.irsg.busassister.LatLngInterpolator;
import com.dev.irsg.busassister.PassengerData;
import com.dev.irsg.busassister.PassengerInfo;
import com.dev.irsg.busassister.PassengerDataGroup;
import com.dev.irsg.busassister.R;

/**
 * Created by Ugi on 6/23/2017.
 *
 */

class PassengerDataListAdapter extends BaseAdapter {
    private Context                       mContext;
    int b = 0;
    PassengerDataListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return AppLogic.getInstance().getPassengerDataGroupCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ViewGroup nullParent = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.passenger_info_group, nullParent);
        }

        final JourneyActivity journeyActivity = (JourneyActivity) mContext;

        final PassengerDataGroup passengerDataGroup = AppLogic.getInstance().getPassengerDataGroup(position);

        LinearLayout stationTitleLayout = (LinearLayout) convertView.findViewById(R.id.station_tittle);

        if ( passengerDataGroup.getOrder() == journeyActivity.getCurrentBoardingPointOrder())
            stationTitleLayout.setBackgroundColor(Color.parseColor("#0070C0"));
        else
            stationTitleLayout.setBackgroundColor(Color.parseColor("#EEEEEE"));

        TextView onBoardingPointNameTextView = (TextView) convertView.findViewById(R.id.boarding_point_name_textview);
        TextView onBoardingTimeTextView = (TextView) convertView.findViewById(R.id.boarding_time_textview);

        onBoardingPointNameTextView.setText(passengerDataGroup.getmBoardingPointName());
        onBoardingTimeTextView.setText(passengerDataGroup.getBoardingTime());

        LinearLayout passenger_info_group_layout = (LinearLayout) convertView.findViewById(R.id.passenger_info_group_layout);

        final ArrayList<PassengerData> passengerDataList = passengerDataGroup.getPassengerDataList();

        passenger_info_group_layout.removeAllViews();

        for (int i = 0; i < passengerDataList.size(); i++) {
            final PassengerData passengerData = passengerDataList.get(i);

            final View view = inflater.inflate(R.layout.passengerinfo_listview_layout, nullParent);

            TextView passengerNameTextView = (TextView) view.findViewById(R.id.passenger_name_textview);
            TextView passengerPhoneNumberTextView = (TextView) view.findViewById(R.id.passenger_phonenumber_textview);
            TextView passengerSeatNoNumberTextView = (TextView) view.findViewById(R.id.passenger_seatno_textview);

            passengerNameTextView.setText(passengerData.getInfo().getName());
            passengerPhoneNumberTextView.setText(passengerData.getInfo().getPhoneNumber());
            passengerSeatNoNumberTextView.setText(String.valueOf(passengerData.getInfo().getSeatNo()));

            Button callButton = (Button) view.findViewById(R.id.passenger_call_button);

            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((JourneyActivity)mContext).callPhone(passengerData.getInfo().getPhoneNumber());
                }
            });

            final ImageButton passengerStateImageButton = (ImageButton) view.findViewById(R.id.passenger_state_imageview);
            final LinearLayout passengerStateLayout = (LinearLayout) view.findViewById(R.id.passenger_state_layout);

            passengerStateLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (passengerDataGroup.getOrder() > journeyActivity.getCurrentBoardingPointOrder())
                        return false;

                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        view.setBackgroundColor(Color.parseColor("#304FFE"));
                    }
                    else if(event.getAction() == MotionEvent.ACTION_UP){
                        view.setBackgroundColor(Color.parseColor("#ffffff"));

                        if (passengerDataGroup.getOrder() == journeyActivity.getCurrentBoardingPointOrder()) {
                            if (passengerData.getState() == PassengerData.PassengerState.UNKNOWN) {
                                passengerData.setState(PassengerData.PassengerState.ON_BOARDED);
                                passengerStateImageButton.setBackground(mContext.getResources().getDrawable(R.drawable.passengerinfo_listview_circle_onboarded));
                                String phoneNumber = passengerData.getInfo().getPhoneNumber();
                                journeyActivity.removePassengerInfoPager(phoneNumber);

                                Toast.makeText(journeyActivity, "Passenger: " + passengerData.getInfo().getName() + " on-boarded!", Toast.LENGTH_SHORT ).show();
                            }
                            else if (passengerData.getState() == PassengerData.PassengerState.ON_BOARDED) {
                                passengerStateImageButton.setBackground(mContext.getResources().getDrawable(R.drawable.passengerinfo_listview_circle_failed));
                                passengerData.setState(PassengerData.PassengerState.FAILED_ON_BOARDED);

                                Toast.makeText(journeyActivity, "Passenger: " + passengerData.getInfo().getName() + " failed to on-board!", Toast.LENGTH_SHORT ).show();

                                String phoneNumber = passengerData.getInfo().getPhoneNumber();

                            }
                            else if (passengerData.getState() == PassengerData.PassengerState.FAILED_ON_BOARDED) {
                                passengerData.setState(PassengerData.PassengerState.ON_BOARDED);
                                passengerStateImageButton.setBackground(mContext.getResources().getDrawable(R.drawable.passengerinfo_listview_circle_onboarded));
                                String phoneNumber = passengerData.getInfo().getPhoneNumber();
                                journeyActivity.removePassengerInfoPager(phoneNumber);

                                Toast.makeText(journeyActivity, "Passenger: " + passengerData.getInfo().getName() + " on-boarded!", Toast.LENGTH_SHORT ).show();
                            }
                            else if (passengerData.getState() == PassengerData.PassengerState.CANCELLED_TICKET) {
                                String phoneNumber = passengerData.getInfo().getPhoneNumber();

                            }
                        }
                        else {
                               //this condition is always true
                               //passengerDataGroup.getOrder() < journeyActivity.getCurrentBoardingPointOrder()

                               String phoneNumber = passengerData.getInfo().getPhoneNumber();
                               journeyActivity.removePassengerInfoPager(phoneNumber);
                        }
                    }

                    return true;
                }
            });

            PassengerData.PassengerState state = passengerDataList.get(i).getState();

            if (state == PassengerData.PassengerState.UNKNOWN) {
                passengerStateImageButton.setBackground(mContext.getResources().getDrawable(R.drawable.passengerinfo_listview_circle_unknown));
            } else if (state == PassengerData.PassengerState.ON_BOARDED) {
                passengerStateImageButton.setBackground(mContext.getResources().getDrawable(R.drawable.passengerinfo_listview_circle_onboarded));
            }  else if (state == PassengerData.PassengerState.CANCELLED_TICKET) {
                passengerStateImageButton.setBackground(mContext.getResources().getDrawable(R.drawable.passengerinfo_listview_circle_cancelled));
            }  else if (state == PassengerData.PassengerState.FAILED_ON_BOARDED) {
                passengerStateImageButton.setBackground(mContext.getResources().getDrawable(R.drawable.passengerinfo_listview_circle_failed));
            }  else if (state == PassengerData.PassengerState.CHANGED_PICK_POINT) {
                passengerStateImageButton.setBackground(mContext.getResources().getDrawable(R.drawable.passengerinfo_listview_circle_changed));
            }

            passenger_info_group_layout.addView(view);
        }

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    private  void logic() {

    }
}
