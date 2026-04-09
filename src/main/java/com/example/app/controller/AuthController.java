package com.example.app.controller;

import com.example.app.model.Role;
import com.example.app.model.User;
import com.example.app.service.AuthService;
import com.example.app.service.ParentStudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;
    private final ParentStudentService psService;

    public AuthController(AuthService authService,
                          ParentStudentService psService) {
        this.authService = authService;
        this.psService = psService;
    }

    // Login Page
    @GetMapping("/login")
    public String showLogin(@RequestParam(required = false) String role,
                            Model model) {
        model.addAttribute("role",
                role == null ? "STUDENT" : role.toUpperCase());
        return "login";
    }

    // Login Processing
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam(required = false) String role,
                        HttpSession session,
                        Model model) {

        User user = authService.login(username, password);

        if (user == null) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }

        String requestedRole = role == null ? "STUDENT" : role.toUpperCase();
        String userRole = user.getRole().name();

        if (!userRole.equals(requestedRole)) {
            String errorMessage = String.format(
                    "This is a %s account. Please use the %s login page.",
                    userRole.toLowerCase(),
                    userRole.toLowerCase()
            );
            model.addAttribute("error", errorMessage);
            return "login";
        }

        // Store user in session
        session.setAttribute("loggedInUser", user);

        // Redirect based on role
        return switch (user.getRole()) {
            case ADMIN -> "redirect:/admin/dashboard";
            case PARENT -> "redirect:/parent/dashboard";
            default -> "redirect:/student/dashboard";
        };
    }

    // Register Page
    @GetMapping("/register")
    public String showRegister(@RequestParam(required = false) String role,
                               Model model) {
        model.addAttribute("role",
                role == null ? "STUDENT" : role.toUpperCase());
        return "register";
    }

    // Register Processing
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String role,
                           @RequestParam(required = false) String studentUsername,
                           Model model) {

        Role r = Role.valueOf(role.toUpperCase());
        User linkedStudent = null;

        if (r == Role.PARENT) {
            linkedStudent = authService.findStudentByUsername(studentUsername);

            if (linkedStudent == null) {
                model.addAttribute("error",
                        "Invalid Student Username. Student must register first.");
                model.addAttribute("role", "PARENT");
                return "register";
            }
        }

        boolean ok = authService.register(username, password, r);

        if (!ok) {
            model.addAttribute("error", "Username already exists");
            model.addAttribute("role", role);
            return "register";
        }

        if (r == Role.PARENT) {
            User parent = authService.login(username, password);
            psService.linkParentToStudent(parent.getId(), linkedStudent.getId());
        }

        return "redirect:/login?role=" + role.toLowerCase();
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}