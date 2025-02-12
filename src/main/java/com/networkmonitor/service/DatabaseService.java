package com.networkmonitor.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.networkmonitor.utils.ConfigManager;
import com.networkmonitor.utils.LogginUtils;
public class DatabaseService {
    private static final String DB_URL = ConfigManager.getProperty("db.url");
    private static final String USER = ConfigManager.getProperty("db.user");
    private static final String PASS = ConfigManager.getProperty("db.password");
    
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // Example method to test connection
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return true;
        } catch (SQLException e) {
            LogginUtils.logError("Database connection failed", e);
            return false;
        }
    }
}