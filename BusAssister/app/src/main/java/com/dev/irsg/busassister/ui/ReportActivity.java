package com.dev.irsg.busassister.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.irsg.busassister.AppLogic;
import com.dev.irsg.busassister.R;

import static com.dev.irsg.busassister.R.id.on_board_passengers_no;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        LinearLayout passenger_onboarded_button = (LinearLayout) findViewById(R.id.passengers_on_boarded);
        final LinearLayout passenger_onboarded_bg = (LinearLayout) findViewById(R.id.passengers_on_boarded_bg);

        final TextView tv = (TextView)findViewById(R.id.on_board_passengers_no);

        passenger_onboarded_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    passenger_onboarded_bg.setBackgroundColor(Color.parseColor("#304FFE"));

                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    passenger_onboarded_bg.setBackgroundColor(Color.parseColor("#92d050"));

                    if(event.getAction() != MotionEvent.ACTION_OUTSIDE){
                        Intent intent = new Intent(getApplicationContext(),ReportInfoActivity.class);
                        intent.putExtra("ReportKind","ON_BOARDED");
                        startActivity(intent);
                    }}

                return true;
            }
        });

        ImageButton report_passenger_changed = (ImageButton) findViewById(R.id.passenger_changed_button);
        report_passenger_changed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReportInfoActivity.class);
                intent.putExtra("ReportKind","CHANGED_PICK_POINT");
                startActivity(intent);

            }
        });

        ImageButton report_passenger_no_onboard = (ImageButton) findViewById(R.id.no_onboard_button);
        report_passenger_no_onboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReportInfoActivity.class);
                intent.putExtra("ReportKind","FAILED_ON_BOARDED");
                startActivity(intent);

            }
        });

        ImageButton report_passenger_canncelled = (ImageButton) findViewById(R.id.passenger_cannelled_button);
        report_passenger_canncelled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReportInfoActivity.class);
                intent.putExtra("ReportKind","CANCELLED_TICKET");
                startActivity(intent);

            }
        });


        Button homebutton = (Button) findViewById(R.id.home_button);

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectServiceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);

                finish();
            }
        });

        TextView onboarded_passengers_no_textview = (TextView) findViewById(on_board_passengers_no);
        TextView failedon_onboarded_passengers_no_textview = (TextView) findViewById(R.id.failed_on_board_passengers_no);
        TextView changed_passengers_no_textview = (TextView) findViewById(R.id.changed_on_board_passengers_no);
        TextView cancelled_passengers_no_textview = (TextView) findViewById(R.id.cancelled_passengers_no);

        int onboardno = AppLogic.getInstance().calculateOnboardedPassengerCount();
        int changeddno = AppLogic.getInstance().calculateChangedPassengerCount();
        int failedno = AppLogic.getInstance().calculateFailedOnboardPassengerCount();
        int canceldno = AppLogic.getInstance ().calculateCanceledPassengerCount();

        onboarded_passengers_no_textview.setText(String.valueOf(onboardno));
        changed_passengers_no_textview.setText(String.valueOf(changeddno));
        failedon_onboarded_passengers_no_textview.setText(String.valueOf(failedno));
        cancelled_passengers_no_textview.setText(String.valueOf(canceldno));
    }
}
