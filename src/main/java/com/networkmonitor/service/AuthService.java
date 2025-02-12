package com.networkmonitor.service;

import com.networkmonitor.config.APIConfig;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class AuthService {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static String authToken = null;

    public static class LoginResponse {
        private final boolean success;
        private final String message;
        private final String token;
        private final JSONObject userData;

        public LoginResponse(boolean success, String message, String token, JSONObject userData) {
            this.success = success;
            this.message = message;
            this.token = token;
            this.userData = userData;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public String getToken() { return token; }
        public JSONObject getUserData() { return userData; }
    }

    public static LoginResponse login(String username, String password) throws IOException, InterruptedException {
        JSONObject credentials = new JSONObject();
        credentials.put("username", username);
        credentials.put("password", password);

        // Debug: Print the credentials being sent
        System.out.println("Sending credentials: " + credentials.toString());

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(APIConfig.LOGIN_URL))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(credentials.toString()))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            
            // Debug logging
            // System.out.println("Response status: " + response.statusCode());
            // System.out.println("Response body: " + responseBody);

            if (responseBody == null || responseBody.trim().isEmpty()) {
                return new LoginResponse(false, "Empty response from server", null, null);
            }

            JSONObject jsonResponse = new JSONObject(responseBody);
            
            if (response.statusCode() == 200 && "success".equals(jsonResponse.getString("status"))) {
                JSONObject data = jsonResponse.getJSONObject("data");
                // Extract individual fields from the 'data' object
                // int userId = data.getInt("id");          // Get the user's ID
                // String usernamee = data.getString("username");  // Get the username
                // String email = data.getString("email");  // Get the email
                // String role = data.getString("role");    // Get the user's role
                
                //SONObject user = data.getJSONObject("user");
                String token = jsonResponse.getJSONObject("token").getString("access"); 
                authToken = token;
                return new LoginResponse(true, "Login successful", token, data);
            } else {
                String message = jsonResponse.optString("message", "Invalid credentials");
                return new LoginResponse(false, message, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResponse(false, "Connection error: " + e.getMessage(), null, null);
        }
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static void clearAuthToken() {
        authToken = null;
    }

    // Helper method to add auth token to requests
    public static HttpRequest.Builder addAuthHeader(HttpRequest.Builder builder) {
        if (authToken != null) {
            return builder.header("Authorization", "Bearer " + authToken);
        }
        return builder;
    }
}