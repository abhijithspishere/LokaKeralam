package Utils;

public class CredentialsStorage {
    private static String registeredEmail;
    private static String registeredPassword;
    private static String generatedLKOId;
    private static String yopmailUsername; // Add this

    // Separate storage for employer credentials
    private static String employerEmail;
    private static String employerPassword;

    // Employee credentials methods
    public static void storeCredentials(String email, String password) {
        registeredEmail = email;
        registeredPassword = password;
        // Extract and store yopmail username if email is from yopmail
        if (email.contains("@yopmail.com")) {
            yopmailUsername = email.split("@")[0];
        }
        System.out.println("Employee credentials stored - Email: " + email + ", Password: " + password);
        System.out.println("Yopmail username: " + yopmailUsername);
    }

    // Add getter for yopmail username
    public static String getYopmailUsername() {
        return yopmailUsername;
    }

    public static String getRegisteredEmail() {
        return registeredEmail;
    }

    public static String getRegisteredPassword() {
        return registeredPassword;
    }

    // Employer credentials methods
    public static void storeEmployerCredentials(String Empemail, String Emppassword) {
        employerEmail = Empemail;
        employerPassword = Emppassword;
        System.out.println("Employer credentials stored - Email: " + Empemail + ", Password: " + Emppassword);
    }

    public static String getEmployerEmail() {
        return employerEmail;
    }

    public static String getEmployerPassword() {
        return employerPassword;
    }

    public static void clearCredentials() {
        registeredEmail = null;
        registeredPassword = null;
        generatedLKOId = null;
        employerEmail = null;
        employerPassword = null;
        yopmailUsername = null;
    }

    // LKO ID methods for employee
    public static void storeLKOId(String lkoId) {
        generatedLKOId = lkoId;
        System.out.println("Employee LKO ID stored: " + lkoId);
    }

    public static String getStoredLKOId() {
        return generatedLKOId;
    }
}