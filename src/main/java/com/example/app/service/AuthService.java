package com.example.app.service;

import com.example.app.model.Role;
import com.example.app.model.User;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class AuthService {

    private static final String DB_URL = "jdbc:sqlite:./data/lms.db";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // ================= LOGIN =================
    public User login(String username, String password) {

        String sql = """
            SELECT id, username, password, role, difficulty
            FROM users
            WHERE username = ? AND password = ?
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")),
                        rs.getInt("difficulty")
                );
            }

        } catch (SQLException e) {
            System.err.println("Login failed: " + e.getMessage());
        }

        return null;
    }

    // ================= REGISTER =================
    public boolean register(String username, String password, Role role) {

        String sql = """
            INSERT INTO users(username, password, role, difficulty)
            VALUES (?, ?, ?, ?)
        """;

        int defaultDifficulty = 1; // safe default

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role.name());
            ps.setInt(4, defaultDifficulty);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Register failed: " + e.getMessage());
            return false;
        }
    }

    // ================= FIND STUDENT BY USERNAME =================
    public User findStudentByUsername(String studentUsername) {

        if (studentUsername == null || studentUsername.isBlank()) {
            return null;
        }

        String sql = """
            SELECT id, username, password, role, difficulty
            FROM users
            WHERE username = ? AND role = 'STUDENT'
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentUsername);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")),
                        rs.getInt("difficulty")
                );
            }

        } catch (SQLException e) {
            System.err.println("Student lookup failed: " + e.getMessage());
        }

        return null;
    }
}
