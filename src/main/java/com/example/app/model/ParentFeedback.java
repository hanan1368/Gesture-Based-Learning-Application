package com.example.app.model;

public class ParentFeedback {

    private int id;
    private int parentId;
    private String parentName;
    private String message;
    private String createdAt;

    public ParentFeedback(int id, int parentId, String parentName, String message, String createdAt) {
        this.id = id;
        this.parentId = parentId;
        this.parentName = parentName;
        this.message = message;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public String getMessage() {
        return message;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}