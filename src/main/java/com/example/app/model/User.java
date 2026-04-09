package com.example.app.model;

public class User {

    private int id;
    private String username;
    private String password;
    private Role role;
    private int difficulty;

    public User(int id, String username, String password, Role role, int difficulty) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.difficulty = difficulty;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    public int getDifficulty() { return difficulty; }
}
