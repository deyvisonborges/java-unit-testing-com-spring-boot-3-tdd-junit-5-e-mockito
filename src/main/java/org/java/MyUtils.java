package org.java;

public class MyUtils {
    public static String getWelcomeMessage(String username, boolean isCustomer) {
        if (isCustomer) return "Dear " + username;
        return "Welcome " + username;
    }
}
