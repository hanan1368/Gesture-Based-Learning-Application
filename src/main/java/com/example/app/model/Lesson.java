package com.example.app.model;

public class Lesson {

    private int id;
    private String course; // ENGLISH/MATHS/CODING
    private String title;
    private String prompt;
    private boolean active;

    public Lesson(int id, String course, String title, String prompt, boolean active) {
        this.id = id;
        this.course = course;
        this.title = title;
        this.prompt = prompt;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getCourse() {
        return course;
    }

    public String getTitle() {
        return title;
    }

    public String getPrompt() {
        return prompt;
    }

    public boolean isActive() {
        return active;
    }
}
