package com.example.assignment1_190177;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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

    private TextView tvSearchFor;
    private Spinner spSearchFor;

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


        tvSearchFor = findViewById(R.id.tvSearchFor);
        spSearchFor = findViewById(R.id.spSearchFor);

        tvSearchFor.setVisibility(View.GONE);
        spSearchFor.setVisibility(View.GONE);

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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

        String text= "Thank you for purchasing " + noOfVouchers + " vouchers from " + restaurant.getRestaurantname() +" for " + subTotal + " OMR!";

        PendingIntent smsPendingIntent = SendSMS(text);
        PendingIntent emailPendingIntent = sendEmail(text);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_launcher_restaurant_foreground)
                .setContentTitle(restaurant.getRestaurantname())
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_baseline_sms_24, getString(R.string.sendSMS),
                        smsPendingIntent)
                .addAction(R.drawable.ic_baseline_email_24, getString(R.string.sendEmail),
                        emailPendingIntent)
                .setAutoCancel(true);
                notificationManager.notify(100, builder.build());

    }

    private PendingIntent sendEmail(String text) {
        String[] to = {"jeanansabry@gmail.com"};
        String[] cc = {"jeananalaufy@gmail.com"};
        String[] bcc = {"19-0177@student.gutech.edu.om"};
        String email_subject = "Notification from " + restaurant.getRestaurantname();
        String emailBody= "Dear Customer,\n" +
                text+"\n"+ restaurant.getRestaurantDescription()
                +"\nKind regards, \n"
                +"\n"+restaurant.getRestaurantname();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO)
                .setData(Uri.parse("mailto:"))
                .setType("text/plain")
                .putExtra(Intent.EXTRA_EMAIL, to)
                .putExtra(Intent.EXTRA_CC, cc)
                .putExtra(Intent.EXTRA_BCC, bcc)
                .putExtra(Intent.EXTRA_SUBJECT, email_subject)
                .putExtra(Intent.EXTRA_TEXT, emailBody);

        TaskStackBuilder stackBuilderEmail = TaskStackBuilder.create(this);
        stackBuilderEmail.addNextIntentWithParentStack(emailIntent);
        PendingIntent emailPendingIntent= stackBuilderEmail.getPendingIntent(98, PendingIntent.FLAG_UPDATE_CURRENT);
        return emailPendingIntent;
    }
    private PendingIntent SendSMS(String text) {
        String phoneNumber= "096896797429";
        Uri uri= Uri.parse("smsto:" +phoneNumber);
        Intent SMSintent= new Intent(Intent.ACTION_SENDTO,uri);
        SMSintent.putExtra("sms_body", text);

        TaskStackBuilder stackBuilderSMS = TaskStackBuilder.create(this);
        stackBuilderSMS.addNextIntentWithParentStack(SMSintent);
        PendingIntent smsPendingIntent= stackBuilderSMS.getPendingIntent(99, PendingIntent.FLAG_UPDATE_CURRENT);
        return smsPendingIntent;
    }

    public void showOnMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(RESTAURANT_OBJECT_MAPS, restaurant);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);


    }

    public void showOnGoogleMaps(View view) {
        Uri uri = Uri.parse("geo:" + restaurant.getLatitude() + "," + restaurant.getLongitude());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    public void navigate(View view) {
        Uri uri = Uri.parse("google.navigation:q=" + restaurant.getLatitude() + "," + restaurant.getLongitude());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    //Panorama
    public void displayPanorama(View view) {
        Uri uri = Uri.parse("google.streetview:cbll=" + restaurant.getLatitude() + "," + restaurant.getLongitude());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    //Search
    public void searchCategory(View view) {
        tvSearchFor.setVisibility(View.VISIBLE);
        spSearchFor.setVisibility(View.VISIBLE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.exampleSearches_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSearchFor.setAdapter(adapter);
        spSearchFor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String searchQuery = adapterView.getItemAtPosition(i).toString();
                if(!searchQuery.contains("select")){
                    Uri uri = Uri.parse("geo:" + restaurant.getLatitude() + "," + restaurant.getLongitude()+"?q="+ searchQuery);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.google.android.apps.maps");
                    if (intent.resolveActivity(getPackageManager()) != null)
                        startActivity(intent);
                }
                }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}












