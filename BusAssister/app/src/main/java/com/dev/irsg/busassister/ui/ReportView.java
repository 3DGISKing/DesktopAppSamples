package com.dev.irsg.busassister.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.irsg.busassister.R;

/**
 * Created by jinming on 7/6/2017.
 */

public class ReportView extends LinearLayout{
    TextView mPassengerNameTextView;
    TextView mPassengerSeatNoTextView;
    TextView mPickPointName;

    public ReportView(Context context) {
        super(context);
        init(context);
    }

    public ReportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.report_item,this,true);

        mPassengerNameTextView = (TextView) findViewById(R.id.name);
        mPassengerSeatNoTextView = (TextView) findViewById(R.id.seatNo);
        mPickPointName = (TextView) findViewById(R.id.onBoardPoint);
    }

    public void setName(String name){
        mPassengerNameTextView.setText(name);
    }

    public void setSeatNo(String seat){
        mPassengerSeatNoTextView.setText(seat);
    }

    public void setPickPointName(String pickPointName){
        mPickPointName.setText(pickPointName);
    }
}
