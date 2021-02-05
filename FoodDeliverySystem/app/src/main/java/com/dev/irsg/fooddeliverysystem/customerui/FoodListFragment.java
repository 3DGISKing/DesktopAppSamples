package com.dev.irsg.fooddeliverysystem.customerui;

/**
 * Created by Ugi on 6/14/2017.
 */

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dev.irsg.fooddeliverysystem.R;

import com.dev.irsg.fooddeliverysystem.entities.FoodMenu;
import com.dev.irsg.fooddeliverysystem.entities.FoodSubMenu;

public class FoodListFragment extends Fragment {
    private static final String ARG_FOOD_SUBMENU_CATEGORY = "food_submenu_category";

    public FoodListFragment() {
    }

    public static FoodListFragment newInstance(int sectionNumber) {
        FoodListFragment fragment = new FoodListFragment();
        Bundle args = new Bundle();

        FoodSubMenu subMenu = FoodMenu.getInstance().getSubMenu(sectionNumber - 1);

        args.putString(ARG_FOOD_SUBMENU_CATEGORY, subMenu.getCategory());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.food_listview);

        String category = getArguments().getString(ARG_FOOD_SUBMENU_CATEGORY);

        final FoodSubMenu foodSubMenu =  FoodMenu.getInstance().getSubMenu(category);

        FoodListAdapter foodlistAdapter = new FoodListAdapter(getActivity(), foodSubMenu);

        listView.setAdapter(foodlistAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FoodActivity.class);

                intent.putExtra("category", foodSubMenu.getCategory());
                intent.putExtra("foodnumber", position);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
