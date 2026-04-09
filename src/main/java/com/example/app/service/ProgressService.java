package com.example.app.service;

import com.example.app.model.ProgressRecord;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProgressService {

    private static final String DB_URL = "jdbc:sqlite:./data/lms.db";

    public ProgressService() {
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {

            String sql = """
                CREATE TABLE IF NOT EXISTS student_progress (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    student_id INTEGER NOT NULL,
                    subject TEXT NOT NULL,
                    progress INTEGER NOT NULL,
                    UNIQUE(student_id, subject)
                );
            """;

            st.execute(sql);

        } catch (SQLException e) {
            System.err.println("Failed to create student_progress table: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // ================= SAVE PROGRESS =================
    public void recordScore(int userId, String subject, int progress) {

        String sql = """
            INSERT INTO student_progress(student_id, subject, progress)
            VALUES(?,?,?)
            ON CONFLICT(student_id, subject)
            DO UPDATE SET progress = excluded.progress
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, subject);
            ps.setInt(3, progress);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Failed to save progress: " + e.getMessage());
        }
    }

    // ================= READ PROGRESS =================
    public List<ProgressRecord> getProgressForUser(int userId) {

        List<ProgressRecord> list = new ArrayList<>();

        String sql = """
            SELECT id, student_id, subject, progress
            FROM student_progress
            WHERE student_id = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                ProgressRecord pr = new ProgressRecord(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getString("subject"),   // ✅ IMPORTANT
                        rs.getInt("progress"),
                        LocalDateTime.now()
                );

                list.add(pr);
            }

        } catch (SQLException e) {
            System.err.println("Failed to fetch progress: " + e.getMessage());
        }

        return list;
    }
}
