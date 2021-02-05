package com.dev.irsg.busassister.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dev.irsg.busassister.AppLogic;
import com.dev.irsg.busassister.PassengerData;
import com.dev.irsg.busassister.R;

import java.util.ArrayList;

public class PassengerListFragment extends Fragment {
    PassengerDataListAdapter mAdapter;
    ListView                 mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_passenger_list, container, false);

        mListView = (ListView) view.findViewById(R.id.passengerinfo_listview);

        return view;
    }

    public void setAdapter() {
        mAdapter = new PassengerDataListAdapter(getActivity());

        mListView.setAdapter(mAdapter);
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }
}
