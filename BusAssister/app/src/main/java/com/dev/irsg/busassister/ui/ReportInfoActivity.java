package com.dev.irsg.busassister.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dev.irsg.busassister.AppLogic;
import com.dev.irsg.busassister.PassengerData;
import com.dev.irsg.busassister.PassengerDataGroup;
import com.dev.irsg.busassister.R;

import java.util.ArrayList;

import static com.dev.irsg.busassister.PassengerData.PassengerState.ON_BOARDED;
import static com.dev.irsg.busassister.PassengerData.PassengerState.CHANGED_PICK_POINT;
import static com.dev.irsg.busassister.PassengerData.PassengerState.FAILED_ON_BOARDED;
import static com.dev.irsg.busassister.PassengerData.PassengerState.CANCELLED_TICKET;

public class ReportInfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_info_main);

        Intent intent = getIntent();

        String key = null;
        if(intent != null){
            key = intent.getStringExtra("ReportKind");
        }

        ReportAdapter adapter = new ReportAdapter();

        ArrayList<PassengerDataGroup> passengerDataGroupList = AppLogic.getInstance().getCurrentPassengerGroupList();

        switch( key ) {
            case "ON_BOARDED":
                for (int i = 0; i < passengerDataGroupList.size(); i++) {
                    for (int j = 0; j < passengerDataGroupList.get(i).size(); j++) {
                        PassengerData data = passengerDataGroupList.get(i).get(j);

                        if( data.getState() == ON_BOARDED){
                            adapter.additem( new ReportItem(data.getInfo().getName(), String.valueOf(data.getInfo().getSeatNo()), data.getInfo().getBoardingPointName()));
                        }
                    }
                }

                break;
            case "CHANGED_PICK_POINT":
                for (int i = 0; i < passengerDataGroupList.size(); i++) {
                    for (int j = 0; j < passengerDataGroupList.get(i).size(); j++) {
                        PassengerData data = passengerDataGroupList.get(i).get(j);

                        if( data.getState() == CHANGED_PICK_POINT){
                            adapter.additem( new ReportItem(data.getInfo().getName(), String.valueOf(data.getInfo().getSeatNo()), data.getInfo().getBoardingPointName()));
                        }
                    }
                }

                break;
            case "FAILED_ON_BOARDED":
                for (int i = 0; i < passengerDataGroupList.size(); i++) {
                    for (int j = 0; j < passengerDataGroupList.get(i).size(); j++) {
                        PassengerData data = passengerDataGroupList.get(i).get(j);

                        if( data.getState() == FAILED_ON_BOARDED){
                            adapter.additem( new ReportItem(data.getInfo().getName(), String.valueOf(data.getInfo().getSeatNo()), data.getInfo().getBoardingPointName()));
                        }
                    }
                }

                break;
            case "CANCELLED_TICKET":
                for (int i = 0; i < passengerDataGroupList.size(); i++) {
                    for (int j = 0; j < passengerDataGroupList.get(i).size(); j++) {
                        PassengerData data = passengerDataGroupList.get(i).get(j);

                        if( data.getState() == CANCELLED_TICKET){
                            adapter.additem( new ReportItem(data.getInfo().getName(), String.valueOf(data.getInfo().getSeatNo()), data.getInfo().getBoardingPointName()));
                        }
                    }
                }

                break;

            default:
                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
        }

        ListView listView = (ListView) findViewById(R.id.reportInfo_listview);
        listView.setAdapter(adapter);
    }

    private class ReportAdapter extends BaseAdapter {

       ArrayList<ReportItem> item = new ArrayList<ReportItem>();

        public void additem(ReportItem itm){
            item.add(itm);
        }

        @Override
        public int getCount() {
            return item.size();
        }

        @Override
        public Object getItem(int position) {
            return item.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ReportView view = new ReportView(getApplicationContext());
            ReportItem im = item.get(position);

            view.setName(im.getPassengerName());
            view.setSeatNo(im.getPassengerSeatNo());
            view.setPickPointName(im.getPickPointName());

            return view;
        }
    }
}
