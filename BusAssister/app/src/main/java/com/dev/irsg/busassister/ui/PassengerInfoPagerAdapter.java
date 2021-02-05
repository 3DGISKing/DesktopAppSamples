package com.dev.irsg.busassister.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.irsg.busassister.PassengerData;
import com.dev.irsg.busassister.PassengerInfo;

import com.dev.irsg.busassister.R;

/**
 * Created by jinming on 6/20/2017.
 * custom page adapter
 */

class PassengerInfoPagerAdapter extends PagerAdapter {
    private PassengerData       mPassengerData;
    private Context             mContext;
    private Integer [] images = { R.drawable.passengerinfo_mapview_normal_background,R.drawable.backnull };

    PassengerInfoPagerAdapter(Context context, PassengerData data) {
        this.mContext = context;
        this.mPassengerData = data;
    }

    PassengerData getPassengerData() { return mPassengerData; }
    PassengerInfo getPassengerInfo() { return mPassengerData.getInfo(); }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position){
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ViewGroup nullParent = null;

        View view = layoutInflater.inflate(R.layout.passengernfo_mapview_layout, nullParent);

        final PassengerInfo info = mPassengerData.getInfo();

        TextView name = (TextView) view.findViewById(R.id.passengerinfo_name_textview);
        name.setText(info.getName());

        TextView phone = (TextView) view.findViewById(R.id.passengerinfo_phone_textview);
        phone.setText(info.getPhoneNumber());

        TextView point = (TextView) view.findViewById(R.id.passengerinfo_boardingpointname_textview);
        point.setText(info.getBoardingPointName());

        TextView seatno = (TextView) view.findViewById(R.id.passengerinfo_seatno_textview);
        seatno.setText(String.valueOf(info.getSeatNo()));

        Button button = (Button) view.findViewById(R.id.passengerinfo_call_button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //call to passenger

                String phone = info.getPhoneNumber();

                ((JourneyActivity) mContext).callPhone(phone);
            }
        });

        if(position != 0)
            button.setVisibility(View.INVISIBLE);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        ViewPager vp = (ViewPager) container;

        vp.addView(view,0);
        return view;
    }
}
