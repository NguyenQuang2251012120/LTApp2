package com.example.ltapp;

public class User {
    private int U_ID;

    private  String U_USERNAME;

    private  String U_PASSWORD;
    private String U_USERTYPE;

    public User(int U_ID, String U_USERNAME, String U_PASSWORD, String U_USERTYPE) {
        this.U_ID = U_ID;
        this.U_USERNAME = U_USERNAME;
        this.U_PASSWORD = U_PASSWORD;
        this.U_USERTYPE = U_USERTYPE;
    }

    public int getU_ID() {
        return U_ID;
    }

    public void setU_ID(int U_ID) {
        this.U_ID = U_ID;
    }

    public String getU_USERNAME() {
        return U_USERNAME;
    }

    public void setU_USERNAME(String U_USERNAME) {
        this.U_USERNAME = U_USERNAME;
    }

    public String getU_PASSWORD() {
        return U_PASSWORD;
    }

    public void setU_PASSWORD(String U_PASSWORD) {
        this.U_PASSWORD = U_PASSWORD;
    }

    public String getU_USERTYPE() {
        return U_USERTYPE;
    }

    public void setU_USERTYPE(String U_USERTYPE) {
        this.U_USERTYPE = U_USERTYPE;
    }
}