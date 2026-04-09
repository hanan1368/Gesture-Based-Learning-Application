package com.example.app.service;

import com.example.app.Repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AttendanceService {

    private final AttendanceRepository repo;

    public AttendanceService(AttendanceRepository repo) {
        this.repo = repo;
    }

    /* ================= MARK ATTENDANCE ================= */

    public void markAttendance(int userId, String day) {

        int minutes = 30;
        int questions = 5;
        boolean lessonCompleted = true;

        repo.save(userId, day, minutes, questions, lessonCompleted);
    }

    /* ================= GET ATTENDED DAYS ================= */

    public List<Integer> getAttendedDays(int userId) {

        return repo.findAttendedDays(userId);
    }

    /* ================= GET DETAILS ================= */

    public Map<String, Object> getDetails(int userId, String day) {

        return repo.findDetails(userId, day);
    }
}