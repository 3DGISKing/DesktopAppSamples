package com.dev.irsg.busassister.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.irsg.busassister.AppLogic;
import com.dev.irsg.busassister.R;

public class StartActivity extends AppCompatActivity {
    Button startButton;
    Button confirmButton;
    Button clearButton;
    Spinner serviceSpinner;
    TextView originationTextView;
    TextView destinationTextView;
    String[] Origination = {"Hyderabad","Ashok Nagar"};
    String[] Destination = {"Vijayawada","Nizampet"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        serviceSpinner = (Spinner) findViewById(R.id.service_spinner);

        originationTextView = (TextView) findViewById(R.id.origination_textview);
        destinationTextView = (TextView) findViewById(R.id.destination_textview);

        startButton = (Button) findViewById(R.id.start_journey_button);
        startButton.setEnabled(false);
        startButton.setTextColor(Color.parseColor("#ffffff"));
        startButton.setBackgroundResource(R.drawable.roundshapebtndisable);

        confirmButton = (Button) findViewById(R.id.conform_button);

        confirmButton.setEnabled(false);
        confirmButton.setTextColor(Color.parseColor("#ffffff"));
        confirmButton.setBackgroundResource(R.drawable.roundshapebtndisable);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service = serviceSpinner.getSelectedItem().toString();

                startButton.setEnabled(true);
                startButton.setTextColor(Color.parseColor("#FBE300"));
                startButton.setBackgroundResource(R.drawable.roundshapebtn);

                clearButton.setEnabled(true);
                clearButton.setTextColor(Color.parseColor("#FBE300"));
                clearButton.setBackgroundResource(R.drawable.roundshapebtn);

                confirmButton.setEnabled(false);
                confirmButton.setTextColor(Color.parseColor("#ffffff"));
                confirmButton.setBackgroundResource(R.drawable.roundshapebtndisable);
            }
        });

        clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setEnabled(false);
        clearButton.setTextColor(Color.parseColor("#ffffff"));
        clearButton.setBackgroundResource(R.drawable.roundshapebtndisable);

        clearButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                clear();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  AppLogic.getInstance().setOnBoardPassengerNo(0);
              goToSelectServiceActivity();
            }
        });

        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position > 0) {
                    confirmButton.setEnabled(true);
                    confirmButton.setTextColor(Color.parseColor("#FBE300"));
                    confirmButton.setBackgroundResource(R.drawable.roundshapebtn);
                    originationTextView.setText(Origination[position-1]);
                    destinationTextView.setText(Destination[position-1]);
                    originationTextView.setTextColor(Color.parseColor("#000000"));
                    destinationTextView.setTextColor(Color.parseColor("#000000"));

                    startButton.setEnabled(false);
                    startButton.setTextColor(Color.parseColor("#ffffff"));
                    startButton.setBackgroundResource(R.drawable.roundshapebtndisable);
                    clearButton.setEnabled(true);
                    clearButton.setTextColor(Color.parseColor("#FBE300"));
                    clearButton.setBackgroundResource(R.drawable.roundshapebtn);
                }
                else{
                    clear();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                Toast.makeText(getApplicationContext(), "NO:" , Toast.LENGTH_SHORT ).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(AppLogic.SIMPLE)
            goToSelectServiceActivity();
    }

    private void goToSelectServiceActivity() {
        Intent intent = new Intent(getApplicationContext(), SelectServiceActivity.class);
        startActivity(intent);
    }

    private void clear(){
        startButton.setEnabled(false);
        startButton.setTextColor(Color.parseColor("#ffffff"));
        startButton.setBackgroundResource(R.drawable.roundshapebtndisable);

        serviceSpinner.setSelection(0);
        originationTextView.setTextColor(Color.parseColor("#E0E0E0"));
        destinationTextView.setTextColor(Color.parseColor("#E0E0E0"));
        originationTextView.setText("Origination");
        destinationTextView.setText("Destination");

        clearButton.setEnabled(false);
        clearButton.setTextColor(Color.parseColor("#ffffff"));
        clearButton.setBackgroundResource(R.drawable.roundshapebtndisable);

        confirmButton.setEnabled(false);
        confirmButton.setTextColor(Color.parseColor("#ffffff"));
        confirmButton.setBackgroundResource(R.drawable.roundshapebtndisable);
    }
}
