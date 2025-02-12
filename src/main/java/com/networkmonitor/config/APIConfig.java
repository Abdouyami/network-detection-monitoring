package com.networkmonitor.config;

public class APIConfig {
    // Base URL
    public  static final String BASE_URL = "http://localhost:8000/api";
    
    // Auth endpoints
    public static final String LOGIN_URL = BASE_URL + "/login/";
    
    // Device endpoints
    public static final String DEVICES_URL = BASE_URL + "/devices/";
    public static final String DEVICE_SEARCH_URL = DEVICES_URL + "%s/";  // Use with String.format()
    
    // Alert endpoints
    public static final String ALERTS_URL = BASE_URL + "/alerts/";
    public static final String ALERT_DETAIL_URL = ALERTS_URL + "%s/";
    
    // Traffic endpoints
    public static final String TRAFFIC_URL = BASE_URL + "/traffic/";
    
    // Vulnerability endpoints
    public static final String VULNERABILITIES_URL = BASE_URL + "/vulnerabilities/";
    
    // User endpoints
    public static final String USERS_URL = BASE_URL + "/users/";
    public static final String USER_DETAIL_URL = USERS_URL + "%s/";
    
    // Notification endpoints
    public static final String NOTIFICATIONS_URL = BASE_URL + "/notifications/";
    public static final String NOTIFICATION_TYPE_SEVERITY_URL = NOTIFICATIONS_URL + "alert_type=%s&alert_severity=%s";
    
    // Logs endpoints
    public static final String LOGS_URL = BASE_URL + "/logs/";
    
    // Helper method to build URLs with parameters
    public static String buildUrl(String baseUrl, String... params) {
        return String.format(baseUrl, (Object[]) params);
    }
}