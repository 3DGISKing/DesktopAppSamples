package com.dev.irsg.busassister.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.dev.irsg.busassister.AppLogic;
import com.dev.irsg.busassister.R;

public class SelectServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);

        ImageButton onboardServiceButton = (ImageButton) findViewById(R.id.onboardservice_button);

        onboardServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), JourneyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                startActivity(intent);
                finish();
            }
        });

        ImageButton serviceReportButton = (ImageButton) findViewById(R.id.reports_service_button);

        serviceReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);

                startActivity(intent);
            }
        });
    }

}
