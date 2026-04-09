package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentSubjectController {

    // ---------- LEVEL PAGES ----------

    @GetMapping("/english-levels")
    public String englishLevels() {
        return "student/english-levels";
    }

    @GetMapping("/coding-levels")
    public String codingLevels() {
        return "student/coding-levels";
    }

    @GetMapping("/maths-levels")
    public String mathsLevels() {
        return "student/maths-levels";
    }

    // ---------- LESSON GRID PAGES ----------

    @GetMapping("/english-lesson")
    public String englishLesson() {
        return "student/english-lesson";
    }

    @GetMapping("/coding-lesson")
    public String codingLesson() {
        return "student/coding-lesson";
    }

    @GetMapping("/maths-lesson")
    public String mathsLesson() {
        return "student/maths-lesson";
    }

    // ---------- LESSON 1 PAGES ----------

    @GetMapping("/english-lesson1")
    public String englishLesson1() {
        return "student/english-lesson1";
    }

    @GetMapping("/coding-lesson1")
    public String codingLesson1() {
        return "student/coding-lesson1";
    }

    @GetMapping("/maths-lesson1")
    public String mathsLesson1() {
        return "student/maths-lesson1";
    }
}
