package com.example.ltapp;

public class booking {
    private String Stringdate;
    private String usernameorder;
    private String time_slots;
    private int total_price;
    private int court_number;
    private String court_name;

    public String getCourt_name() {
        return court_name;
    }

    public void setCourt_name(String court_name) {
        this.court_name = court_name;
    }

    public int getCourt_number() {
        return court_number;
    }

    public void setCourt_number(int court_number) {
        this.court_number = court_number;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getTime_slots() {
        return time_slots;
    }

    public void setTime_slots(String time_slots) {
        this.time_slots = time_slots;
    }

    public String getStringdate() {
        return Stringdate;
    }

    public void setStringdate(String stringdate) {
        Stringdate = stringdate;
    }

    public String getUsernameorder() {
        return usernameorder;
    }

    public void setUsernameorder(String usernameorder) {
        this.usernameorder = usernameorder;
    }

    public booking(String stringdate, String usernameorder, String time_slots, int total_price, int court_number, String court_name) {
        Stringdate = stringdate;
        this.usernameorder = usernameorder;
        this.time_slots = time_slots;
        this.total_price = total_price;
        this.court_number = court_number;
        this.court_name = court_name;


    }
}
