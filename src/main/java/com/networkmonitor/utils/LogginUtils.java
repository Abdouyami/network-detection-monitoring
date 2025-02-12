package com.networkmonitor.utils;

import java.sql.SQLException;

public class LogginUtils {
    public static void logError(String database_connection_failed, SQLException e) {
        System.err.println(database_connection_failed + e);
    }    
}
