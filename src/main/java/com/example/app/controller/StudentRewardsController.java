package com.example.app.controller;

import com.example.app.model.ProgressRecord;
import com.example.app.service.ProgressService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentRewardsController {

    private final ProgressService progressService;

    public StudentRewardsController(ProgressService progressService) {
        this.progressService = progressService;
    }

    public int calculatePoints(int studentId) {

        List<ProgressRecord> records =
                progressService.getProgressForUser(studentId);

        int totalPoints = 0;

        for (ProgressRecord r : records) {
            totalPoints += r.getScore();
        }

        return totalPoints;
    }
}