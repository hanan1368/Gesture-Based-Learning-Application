package com.example.app.service;

import com.example.app.model.ProgressRecord;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardService {

    private final ProgressService progressService;

    public RewardService(ProgressService progressService) {
        this.progressService = progressService;
    }

    public Map<String, Integer> calculateRewards(int studentId) {

        List<ProgressRecord> records = progressService.getProgressForUser(studentId);

        int totalPoints = 0;

        for (ProgressRecord r : records) {
            totalPoints += r.getScore();
        }

        int level1 = Math.min(totalPoints, 100);
        int level2 = Math.max(Math.min(totalPoints - 100, 100), 0);
        int level3 = Math.max(totalPoints - 200, 0);

        Map<String, Integer> result = new HashMap<>();
        result.put("totalPoints", totalPoints);
        result.put("level1", level1);
        result.put("level2", level2);
        result.put("level3", level3);

        return result;
    }
}