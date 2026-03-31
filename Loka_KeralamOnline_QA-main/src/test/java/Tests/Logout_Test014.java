package Tests;

import Hooks.Hook;
import PageObjects.LoginPage;
import Utils.CredentialsStorage;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class Logout_Test014 extends Hook {

    @Test(
            priority = 14,
            testName = "CLKOI-AT-GCCDW-04_Logout_Functionality",
            description = "Verify user is able to logout successfully from the application"
    )
    public void logout_test() {

        logger.info("========== LOGOUT TEST STARTED ==========");

        // 1. Fetch credentials
        String registeredEmail = CredentialsStorage.getRegisteredEmail();
        String registeredPassword = CredentialsStorage.getRegisteredPassword();

        // 2. Validate credentials
        if (registeredEmail == null || registeredPassword == null) {
            String errorMsg = "Credentials not found! Ensure Registration/Login Test runs before this test.";
            logger.error(errorMsg);
            test.log(Status.SKIP, errorMsg);
            throw new SkipException(errorMsg);
        }

        logger.info("Logging in before logout...");
        test.log(Status.INFO, "Logging in with registered credentials");

        LoginPage loginPage = new LoginPage(driver);

        try {
            // 3. Login first
            loginPage.loginWithCredentials(registeredEmail, registeredPassword);
            boolean loginStatus = loginPage.isLoginSuccessful();

            Assert.assertTrue(loginStatus, "Login failed. Cannot proceed to logout.");

            logger.info("Login successful. Proceeding to logout...");
            test.log(Status.INFO, "Login successful. Performing logout");

            // 4. Perform logout
            loginPage.logout();

            // 5. Validate logout (URL or login page element)
            String currentUrl = driver.getCurrentUrl();

            boolean isLoggedOut = currentUrl.contains("login") || currentUrl.contains("home");

            logger.info("Current URL after logout: " + currentUrl);

            Assert.assertTrue(
                    isLoggedOut,
                    "Logout failed: User is still on dashboard."
            );

            test.log(Status.PASS, "Logout successful - User redirected to login/home page");

        } catch (Exception e) {
            logger.error("Logout test exception", e);
            test.log(Status.FAIL, "Exception during logout: " + e.getMessage());
            Assert.fail("Exception occurred during logout: " + e.getMessage());
        }
    }
}