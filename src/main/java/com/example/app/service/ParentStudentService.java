package com.example.app.service;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Service
public class ParentStudentService {

    private static final String DB_URL = "jdbc:sqlite:./data/lms.db";

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL);
    }

    public void linkParentToStudent(int parentId, int studentId) {

        String sql = """
            INSERT INTO parent_student(parent_id, student_id)
            VALUES (?, ?)
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);
            ps.setInt(2, studentId);
            ps.executeUpdate();

        } catch (Exception e) {
            System.err.println("Failed to link parent and student: " + e.getMessage());
        }
    }

    public Integer getStudentIdForParent(int parentId) {

        String sql = "SELECT student_id FROM parent_student WHERE parent_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("student_id");
            }

        } catch (Exception e) {
            System.err.println("Failed to fetch student for parent: " + e.getMessage());
        }

        return null;
    }
}
