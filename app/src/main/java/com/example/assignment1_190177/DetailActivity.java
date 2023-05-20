package com.example.assignment1_190177;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    public static final String RESTAURANT_OBJECT_MAPS = "RestaurantObjectMaps";
    private Restaurant restaurant;
    private int noOfVouchers;
    private double subTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail3);


        restaurant = (Restaurant) getIntent().getSerializableExtra(MainActivity.RESTAURANT_OBJECT);
        if (restaurant == null) {
            Toast.makeText(this, "Data is not available", Toast.LENGTH_SHORT).show();
            return;
        }


        setInData(restaurant);

        processSpinner();

    }

    private void processSpinner() {
        Spinner spinner = findViewById(R.id.spNoOfGiftVouchersDetail);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.nums_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                noOfVouchers = Integer.parseInt(adapterView.getItemAtPosition(i) + "");
                subTotal = noOfVouchers * restaurant.getDeliveryfee();

                TextView tvsubTotal = findViewById(R.id.tvRestaurantSubTotalValueDetail);

                tvsubTotal.setText(subTotal + "  OMR");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setInData(Restaurant restaurant) {
        TextView tvMovieNameDetail = findViewById(R.id.tvRestaurantNameDetail);
        TextView tvRestaurantCuisineDetail = findViewById(R.id.tvRestaurantCuisineDetail);
        TextView tvRatingDetail = findViewById(R.id.tvRatingDetail);
        TextView tvRestaurantDescDetail = findViewById(R.id.tvRestaurantDescDetail);
        TextView tvDeliveryFeeLabel = findViewById(R.id.tvDeliveryFeeLabel);
        TextView tvDeliveryFee = findViewById(R.id.tvDeliveryFee);
        ImageView ivRestaurantImageDetail = findViewById(R.id.ivRestaurantImageDetail);


        setTitle(restaurant.getRestaurantname());

        tvMovieNameDetail.setText(restaurant.getRestaurantname());
        tvRestaurantCuisineDetail.setText(restaurant.getCuisine());
        tvRatingDetail.setText(restaurant.getRating() + "");
        tvRestaurantDescDetail.setText(restaurant.getRestaurantDescription());
        tvDeliveryFee.setText(restaurant.getDeliveryfee() + " OMR");
        tvDeliveryFeeLabel.setText(restaurant.getDeliveryfeelabel() + "");

        int resID = getResources().getIdentifier(restaurant.getRestaurantImageName(), "drawable", getPackageName());
        ivRestaurantImageDetail.setImageResource(resID);
    }

    public void buyVoucher(View view) {
        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("1", "RestaurantChannel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Restaurant Channel Desc");
        }

        NotificationManager notificationManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notificationManager = getSystemService(NotificationManager.class);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_launcher_restaurant_foreground)
                .setContentTitle(restaurant.getRestaurantname())
                .setContentText("Thank you for purchasing " + noOfVouchers + " vouchers from " + restaurant.getRestaurantname()+ " for " + subTotal + " OMR!")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Thank you for purchasing " + noOfVouchers + " vouchers from " + restaurant.getRestaurantname()+ " for " + subTotal + " OMR"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify(100, builder.build());

    }

    public void showOnMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(RESTAURANT_OBJECT_MAPS, restaurant);
        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);


    }
}





