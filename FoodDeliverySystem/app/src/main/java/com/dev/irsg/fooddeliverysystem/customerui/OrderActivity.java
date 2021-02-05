package com.dev.irsg.fooddeliverysystem.customerui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.irsg.fooddeliverysystem.AppLogic;
import com.dev.irsg.fooddeliverysystem.R;
import com.dev.irsg.fooddeliverysystem.entities.Order;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    PayPalConfiguration m_configuration;
    Intent m_service;
    final int PAYPAL_REQUEST_CODE = 999;

    Order basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Order contents");

        basket = AppLogic.getInstance().getCurrentCustomer().getBasket();

        TextView total_price_textview = (TextView) findViewById(R.id.total_price_textview);

        total_price_textview.setText("Total Price: " + String.valueOf(basket.calculateTotalPrice()));

        Button paybutton = (Button) findViewById(R.id.pay_button);
        Button cancelbutton = (Button) findViewById(R.id.cancel_button);

        m_configuration = new PayPalConfiguration().
                environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)//sandbox for test, production for real
                // .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)// real paypal:jinming
                .clientId(AppLogic.PAYPAL_CLIENT_ID);

        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);//configuration above
        startService(m_service);//paypal service, listening to calls to paypal app

        paybutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                double price = (double) basket.calculateTotalPrice();
                if(price == 0 ) return;
                OrderActivity.this.pay(price);
            }
        });

        cancelbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                double price = (double) basket.calculateTotalPrice();
                if(price == 0 ) return;
                OrderActivity.this.pay(price);
            }
        });

        ArrayList<Order.FoodAndCount>  foodAndCountArrayList = basket.getFoodAndCountList();

        ListView selected_food_listview = (ListView) findViewById(R.id.selected_food_listview);

        ListViewAdapter adapter = new ListViewAdapter(this, foodAndCountArrayList);

        selected_food_listview.setAdapter(adapter);

        //////////

        selected_food_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String food = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(OrderActivity.this, food, Toast.LENGTH_LONG).show();

            }
        });


        ///////////
    }

    private class ListViewAdapter extends BaseAdapter
    {
        Context context;
        LayoutInflater inflater;

        ArrayList<Order.FoodAndCount> foodAndCountArrayList;

        private ListViewAdapter( Context context, ArrayList<Order.FoodAndCount> foodAndCountArrayList)
        {
            this.context = context;
            this.foodAndCountArrayList = foodAndCountArrayList;
        }

        @Override
        public int getCount() {
            return foodAndCountArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if( convertView == null ) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.selected_food_listview_item, null);
            }

            Order.FoodAndCount foodAndCount = foodAndCountArrayList.get(position);

            TextView food_name_textview = (TextView) convertView.findViewById(R.id.food_name_textview);

            String option = foodAndCount.getFood().getOptionName();

            if ( option.isEmpty())
                food_name_textview.setText(foodAndCount.getFood().getName() );
            else
                food_name_textview.setText(foodAndCount.getFood().getName() + "-" + option);

            TextView food_price_textview = (TextView) convertView.findViewById(R.id.food_price_textview);
            food_price_textview.setText(String.valueOf(foodAndCount.getFood().getPrice()));

            TextView food_count_textview = (TextView) convertView.findViewById(R.id.food_count_textview);

            food_count_textview.setText(String.valueOf(foodAndCount.getCount()));

            return  convertView;
        }
    }

    private void pay( double usd) {
        BigDecimal amount = BigDecimal.valueOf(usd);

        PayPalPayment payment = new PayPalPayment(amount, "USD","OrderActivity::pay", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class); //it's not paypalpayment, it's paymentactivity
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PAYPAL_REQUEST_CODE) {
            // we have to confirm that the payment worked to avoid fraud
            PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

            if(confirmation != null)
            {
                String state = confirmation.getProofOfPayment().getState();

                if(state.equals("approved")) //if the payment worked, the state equals approved
                 //   System.out.println("approved");
                  orderRequest("010101", "rangdonggou","123456789");

                else
                    System.out.println("error");
           }
            else
                System.out.println("configuration is null");
        }
    }
////////////////////////////////
    protected  void orderRequest(String time, String location, String phone)
    {
        //Toast.makeText(OrderActivity.this, "ewqtewtwertretretert", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));//call phon
      //  if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
     //       return;
     //   }
        startActivity(intent);
    }
}
