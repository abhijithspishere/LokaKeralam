package Utils;

public class CredentialsStorage {
    private static String registeredEmail;
    private static String registeredPassword;
    private static String generatedLKOId;  // Added for LKO ID storage

    public static void storeCredentials(String email, String password) {
        registeredEmail = email;
        registeredPassword = password;
        System.out.println("Credentials stored - Email: " + email + ", Password: " + password);
    }

    public static String getRegisteredEmail() {
        return registeredEmail;
    }

    public static String getRegisteredPassword() {
        return registeredPassword;
    }

    public static void clearCredentials() {
        registeredEmail = null;
        registeredPassword = null;
        generatedLKOId = null;  // Clear LKO ID as well
    }

    // New methods for LKO ID storage
    public static void storeLKOId(String lkoId) {
        generatedLKOId = lkoId;
        System.out.println("LKO ID stored: " + lkoId);
    }

    public static String getStoredLKOId() {
        return generatedLKOId;
    }
}