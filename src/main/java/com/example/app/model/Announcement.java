package com.example.app.model;

public class Announcement {

    private int id;
    private String title;
    private String message;
    private String createdAt;

    public Announcement(int id, String title, String message, String createdAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getMessage() { return message; }

    public String getCreatedAt() { return createdAt; }
}