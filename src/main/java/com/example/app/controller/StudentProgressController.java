package com.example.app.controller;

import com.example.app.model.ProgressRecord;
import com.example.app.model.User;
import com.example.app.service.ProgressService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentProgressController {

    private final ProgressService progressService;

    public StudentProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    // ================= STUDENT PROGRESS PAGE =================
    @GetMapping("/progress")
    public String studentProgress(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login?role=student";
        }

        model.addAttribute(
                "progressList",
                progressService.getProgressForUser(user.getId())
        );

        return "student/student-progress";
    }

    // ================= PROGRESS DATA (JSON API) =================
    @ResponseBody
    @GetMapping("/progress/data")
    public Map<String, Integer> getStudentProgressData(HttpSession session) {

        Map<String, Integer> result = new HashMap<>();

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return result; // safe, avoids whitelabel
        }

        List<ProgressRecord> records =
                progressService.getProgressForUser(user.getId());

        for (ProgressRecord r : records) {
            // SAME STRUCTURE THAT WORKED FOR CODING
            result.put(r.getSubject(), r.getScore());
        }

        return result;
    }
}
