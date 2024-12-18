package com.example.ltapp;

import java.io.Serializable;

public class Sport implements Serializable {
    private String S_ID;
    private String  S_TYPE;
    private String S_NAME;
    private String  S_LOCATION;
    private String S_DISTRICT;
    private String S_RATING;
    private String S_PRICE;
    private String S_DESCRIPTION;
    private String S_SERVICE;
    public Sport(String s_ID, String s_TYPE, String s_NAME, String s_LOCATION, String s_DISTRICT, String s_RATING, String s_PRICE, String s_DESCRIPTION, String s_SERVICE) {
        S_ID = s_ID;
        S_TYPE = s_TYPE;
        S_NAME = s_NAME;
        S_LOCATION = s_LOCATION;
        S_DISTRICT = s_DISTRICT;
        S_RATING = s_RATING;
        S_PRICE = s_PRICE;
        S_DESCRIPTION = s_DESCRIPTION;
        S_SERVICE = s_SERVICE;
    }

    public String getS_ID() {
        return S_ID;
    }

    public void setS_ID(String s_ID) {
        S_ID = s_ID;
    }

    public String getS_TYPE() {
        return S_TYPE;
    }

    public void setS_TYPE(String s_TYPE) {
        S_TYPE = s_TYPE;
    }

    public String getS_NAME() {
        return S_NAME;
    }

    public void setS_NAME(String s_NAME) {
        S_NAME = s_NAME;
    }

    public String getS_LOCATION() {
        return S_LOCATION;
    }

    public void setS_LOCATION(String s_LOCATION) {
        S_LOCATION = s_LOCATION;
    }

    public String getS_RATING() {
        return S_RATING;
    }

    public void setS_RATING(String s_RATING) {
        S_RATING = s_RATING;
    }

    public String getS_PRICE() {
        return S_PRICE;
    }

    public void setS_PRICE(String s_PRICE) {
        S_PRICE = s_PRICE;
    }

    public String getS_DESCRIPTION() {
        return S_DESCRIPTION;
    }

    public void setS_DESCRIPTION(String s_DESCRIPTION) {
        S_DESCRIPTION = s_DESCRIPTION;
    }

    public String getS_DISTRICT() {
        return S_DISTRICT;
    }

    public void setS_DISTRICT(String s_DISTRICT) {
        S_DISTRICT = s_DISTRICT;
    }
    public String getS_SERVICE() {
        return S_SERVICE;
    }
    public void setS_SERVICE(String s_SERVICE) {
        S_SERVICE = s_SERVICE;
    }
}
