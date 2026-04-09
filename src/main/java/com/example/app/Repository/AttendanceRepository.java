package com.example.app.Repository;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AttendanceRepository {

    private final JdbcTemplate jdbcTemplate;

    public AttendanceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* ================= CREATE TABLE ================= */

    @PostConstruct
    public void createTableIfNotExists() {

        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS attendance (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "user_id INTEGER," +
                        "day TEXT," +
                        "minutes_logged INTEGER," +
                        "questions_completed INTEGER," +
                        "lesson_completed INTEGER," +
                        "created_at TEXT DEFAULT CURRENT_TIMESTAMP" +
                        ")"
        );
    }

    /* ================= SAVE ATTENDANCE ================= */

    public void save(int userId,
                     String day,
                     int minutes,
                     int questions,
                     boolean lessonCompleted) {

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM attendance WHERE user_id=? AND day=?",
                Integer.class,
                userId,
                day
        );

        if (count == null || count == 0) {

            jdbcTemplate.update(
                    "INSERT INTO attendance(user_id,day,minutes_logged,questions_completed,lesson_completed) VALUES (?,?,?,?,?)",
                    userId,
                    day,
                    minutes,
                    questions,
                    lessonCompleted ? 1 : 0
            );
        }
    }

    /* ================= ATTENDED DAYS ================= */

    public List<Integer> findAttendedDays(int userId) {

        return jdbcTemplate.queryForList(
                "SELECT CAST(SUBSTR(day,5) AS INTEGER) FROM attendance WHERE user_id=?",
                Integer.class,
                userId
        );
    }

    /* ================= POPUP DETAILS ================= */

    public Map<String, Object> findDetails(int userId, String day) {

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "SELECT minutes_logged,questions_completed,lesson_completed,created_at FROM attendance WHERE user_id=? AND day=?",
                userId,
                day
        );

        Map<String, Object> result = new HashMap<>();

        if (rows.isEmpty()) {

            result.put("date", "Not recorded");
            result.put("minutes", 0);
            result.put("questions", 0);
            result.put("lessonCompleted", false);

        } else {

            Map<String, Object> row = rows.get(0);

            result.put("date", row.get("created_at"));
            result.put("minutes", row.get("minutes_logged"));
            result.put("questions", row.get("questions_completed"));
            result.put("lessonCompleted",
                    ((Number) row.get("lesson_completed")).intValue() == 1);
        }

        return result;
    }
}