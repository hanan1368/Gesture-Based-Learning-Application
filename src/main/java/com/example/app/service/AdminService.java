package com.example.app.service;

import com.example.app.model.User;
import com.example.app.model.Role;
import com.example.app.model.Announcement;
import com.example.app.model.ParentFeedback;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private static final String DB_URL = "jdbc:sqlite:./data/lms.db";

    public AdminService() {
        createAnnouncementTable();
        createFeedbackTable();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // ================= CREATE ANNOUNCEMENT TABLE =================
    private void createAnnouncementTable() {

        String sql = """
        CREATE TABLE IF NOT EXISTS announcements(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        title TEXT,
        message TEXT,
        created_at TEXT DEFAULT CURRENT_TIMESTAMP
        )
        """;

        try(Connection conn = getConnection();
            Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // ================= CREATE FEEDBACK TABLE =================
    private void createFeedbackTable(){

        String sql = """
        CREATE TABLE IF NOT EXISTS parent_feedback(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            parent_id INTEGER,
            parent_name TEXT,
            message TEXT,
            created_at TEXT DEFAULT CURRENT_TIMESTAMP
        )
        """;

        try(Connection conn=getConnection();
            Statement stmt=conn.createStatement()){

            stmt.execute(sql);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // ================= SAVE FEEDBACK =================
    public void saveParentFeedback(int parentId,String parentName,String message){

        String sql="INSERT INTO parent_feedback(parent_id,parent_name,message) VALUES(?,?,?)";

        try(Connection conn=getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){

            ps.setInt(1,parentId);
            ps.setString(2,parentName);
            ps.setString(3,message);

            ps.executeUpdate();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // ================= GET ALL FEEDBACK =================
    public List<ParentFeedback> getAllFeedback(){

        List<ParentFeedback> list=new ArrayList<>();

        String sql="SELECT * FROM parent_feedback ORDER BY created_at DESC";

        try(Connection conn=getConnection();
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){

            while(rs.next()){

                list.add(new ParentFeedback(
                        rs.getInt("id"),
                        rs.getInt("parent_id"),
                        rs.getString("parent_name"),
                        rs.getString("message"),
                        rs.getString("created_at")
                ));
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return list;
    }

    // ================= USERS =================
    public List<User> getAllUsers(){

        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";

        try(Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")),
                        rs.getInt("difficulty")
                ));
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return users;
    }

    public void updateUser(int id,String username,String password,String role){

        String sql="UPDATE users SET username=?,password=?,role=? WHERE id=?";

        try(Connection conn=getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){

            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,role);
            ps.setInt(4,id);

            ps.executeUpdate();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteUser(int id){

        String sql="DELETE FROM users WHERE id=?";

        try(Connection conn=getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){

            ps.setInt(1,id);
            ps.executeUpdate();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // ================= ANNOUNCEMENTS =================
    public void postAnnouncement(String title,String message){

        String sql="INSERT INTO announcements(title,message) VALUES(?,?)";

        try(Connection conn=getConnection();
            PreparedStatement ps=conn.prepareStatement(sql)){

            ps.setString(1,title);
            ps.setString(2,message);

            ps.executeUpdate();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public List<Announcement> getAnnouncements(){

        List<Announcement> list=new ArrayList<>();

        String sql="SELECT * FROM announcements ORDER BY created_at DESC";

        try(Connection conn=getConnection();
            PreparedStatement ps=conn.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()){

            while(rs.next()){

                list.add(new Announcement(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("message"),
                        rs.getString("created_at")
                ));
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return list;
    }
}