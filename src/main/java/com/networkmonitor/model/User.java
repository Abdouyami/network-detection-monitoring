package com.networkmonitor.model;

public class User {
    private final String username;
    private final String password;
    private final String email;
    private final String role;

    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    
    public User(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = ""; // Default value for password, or null if you prefer
    }
    // Getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}