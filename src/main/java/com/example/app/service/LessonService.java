package com.example.app.service;
import org.springframework.stereotype.Service;

@Service
public class LessonService {

    public void listLessons() {
        System.out.println("Listing lessons (placeholder)");
    }

    public void addLesson(String course, String title, String prompt) {
        System.out.println("Lesson added (placeholder): " + course + " - " + title);
    }

    public void deleteLesson(int lessonId) {
        System.out.println("Lesson deleted (placeholder): ID " + lessonId);
    }
}
