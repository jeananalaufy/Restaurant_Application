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

    public Restaurant() {
    }

    public Restaurant(String restaurantname, String cuisine, String rating, String location, String delivery, String restaurantImageName, double deliveryfee, String deliveryfeelabel, String restaurantDescription) {
        this.restaurantname = restaurantname;
        this.cuisine = cuisine;
        this.rating = rating;
        this.location = location;
        this.delivery = delivery;
        this.restaurantImageName = restaurantImageName;
        this.deliveryfee = deliveryfee;
        this.deliveryfeelabel = deliveryfeelabel;
        RestaurantDescription = restaurantDescription;
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

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public void setRestaurantImageName(String restaurantImageName) {
        this.restaurantImageName = restaurantImageName;
    }

    public void setDeliveryfee(double deliveryfee) {
        this.deliveryfee = deliveryfee;
    }

    public void setDeliveryfeelabel(String deliveryfeelabel) {
        this.deliveryfeelabel = deliveryfeelabel;
    }

    public void setRestaurantDescription(String restaurantDescription) {
        RestaurantDescription = restaurantDescription;
    }
}
