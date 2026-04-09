package com.example.app.controller;

import com.example.app.model.User;
import com.example.app.service.AttendanceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/parent/attendance")
public class ParentAttendanceController {

    private final AttendanceService attendanceService;
    private final JdbcTemplate jdbcTemplate;

    public ParentAttendanceController(AttendanceService attendanceService,
                                      JdbcTemplate jdbcTemplate) {
        this.attendanceService = attendanceService;
        this.jdbcTemplate = jdbcTemplate;
    }

    /* ================= LOAD PAGE ================= */

    @GetMapping
    public String parentAttendancePage(HttpSession session) {

        User parent = (User) session.getAttribute("loggedInUser");

        if (parent == null) {
            return "redirect:/login?role=parent";
        }

        return "parent/parent-child-attendance";
    }

    /* ================= POPUP DATA ================= */

    @GetMapping("/details/{day}")
    @ResponseBody
    public Map<String, Object> getChildAttendance(
            @PathVariable String day,
            HttpSession session) {

        User parent = (User) session.getAttribute("loggedInUser");

        if (parent == null) {
            throw new RuntimeException("Parent not logged in");
        }

        int parentId = parent.getId();

        /* Find linked student */
        Integer studentId = jdbcTemplate.queryForObject(
                "SELECT student_id FROM parent_student WHERE parent_id=?",
                Integer.class,
                parentId
        );

        if (studentId == null) {
            throw new RuntimeException("No student linked to this parent");
        }

        /* Fetch the same attendance details used by student */
        return attendanceService.getDetails(studentId, day);
    }
}