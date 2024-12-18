package com.example.ltapp;

public class favorite {
    private String  user;
    private String Sport_name;
    private String Sport_price;
    private String Sport_district;

    public favorite(String user, String Sport_name, String Sport_price, String Sport_district) {
        this.user = user;
        this.Sport_name = Sport_name;
        this.Sport_price = Sport_price;
        this.Sport_district = Sport_district;

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSport_name() {
        return Sport_name;
    }

    public void setSport_name(String sport_name) {
        Sport_name = sport_name;
    }

    public String getSport_price() {
        return Sport_price;
    }

    public void setSport_price(String sport_price) {
        Sport_price = sport_price;
    }

    public String getSport_district() {
        return Sport_district;
    }

    public void setSport_district(String sport_district) {
        Sport_district = sport_district;
    }
}
