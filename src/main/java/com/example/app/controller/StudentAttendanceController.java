package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.service.AttendanceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student/attendance")
public class StudentAttendanceController {

    private final AttendanceService service;

    public StudentAttendanceController(AttendanceService service) {
        this.service = service;
    }

    /* ================= LOAD ATTENDANCE PAGE ================= */

    @GetMapping
    public String attendance(Model model, HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login?role=student";
        }

        int userId = user.getId();

        /* Ensure at least Day 1 attendance exists */
        service.markAttendance(userId, "Day 1");

        List<Integer> attendedDays = service.getAttendedDays(userId);

        model.addAttribute("attendedDays", attendedDays);

        return "student/student-attendance";
    }

    /* ================= POPUP DETAILS ================= */

    @GetMapping("/details/{day}")
    @ResponseBody
    public Map<String, Object> getDetails(
            @PathVariable String day,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            throw new RuntimeException("User not logged in");
        }

        int userId = user.getId();

        return service.getDetails(userId, day);
    }
}