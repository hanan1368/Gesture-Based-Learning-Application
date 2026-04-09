package com.example.app.model;

import java.time.LocalDateTime;

public class ProgressRecord {

    private int id;
    private int userId;
    private String subject;   // ✅ ADD THIS
    private int score;
    private LocalDateTime createdAt;

    public ProgressRecord() {}

    public ProgressRecord(int id, int userId, String subject, int score, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.subject = subject;
        this.score = score;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getSubject() {          // ✅ ADD THIS
        return subject;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
