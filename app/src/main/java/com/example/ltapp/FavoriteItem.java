package com.example.ltapp;

public class FavoriteItem {

    private String name;
    private String district;
    private String price;

    public FavoriteItem(String name, String district, String price) {
        this.name = name;
        this.district = district;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

