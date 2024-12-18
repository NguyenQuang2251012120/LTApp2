package com.example.ltapp;

public class Comment {
    private String id;
    private String username;
    private String content;
    private float rating;
    private String court_id;

    public Comment(String id, String username, String content, float rating, String court_id) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.rating = rating;
        this.court_id = court_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCourt_id() {
        return court_id;
    }

    public void setCourt_id(String court_id) {
        this.court_id = court_id;
    }
}