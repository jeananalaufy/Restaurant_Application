package com.example.assignment1_190177;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private String restaurantname;
    private String cuisine;
    private String rating;
    private String location;
    private String delivery;
    private String restaurantImageName;
    private double deliveryfee;
    private String deliveryfeelabel;
    private String RestaurantDescription;
    private double longitude;
    private double latitude;

    public Restaurant() {
    }

    public Restaurant(String restaurantname, String cuisine, String rating, String location, String delivery, String restaurantImageName, double deliveryfee, String deliveryfeelabel, String restaurantDescription, double longitude, double latitude) {
        this.restaurantname = restaurantname;
        this.cuisine = cuisine;
        this.rating = rating;
        this.location = location;
        this.delivery = delivery;
        this.restaurantImageName = restaurantImageName;
        this.deliveryfee = deliveryfee;
        this.deliveryfeelabel = deliveryfeelabel;
        RestaurantDescription = restaurantDescription;
        this.longitude = longitude;
        this.latitude = latitude;

    }

    public String getRestaurantname() {
        return restaurantname;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getRating() {
        return rating;
    }

    public String getLocation() {
        return location;
    }

    public String getDelivery() {
        return delivery;
    }

    public String getRestaurantImageName() {
        return restaurantImageName;
    }

    public double getDeliveryfee() {
        return deliveryfee;
    }

    public String getDeliveryfeelabel() {
        return deliveryfeelabel;
    }

    public String getRestaurantDescription() {
        return RestaurantDescription;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
