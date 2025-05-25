package com.wirehec.front_wirehec.Controllers;

public class SessionManager {
    private static String currentUser;
    private static String currentRole;

    public static void setCurrentUser(String user) {
        currentUser = user;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentRole(String role) {
        currentRole = role;
    }

    public static String getCurrentRole() {
        return currentRole;
    }

    public static void clear() {
        currentUser = null;
        currentRole = null;
    }
}
