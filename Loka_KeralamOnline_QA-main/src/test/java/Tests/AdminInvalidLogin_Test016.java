package Tests;

import Hooks.Hook;
import PageObjects.AdminLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminInvalidLogin_Test016 extends Hook {

    @Test(
            priority = 16,
            testName = "CLKOI-AT-GCCDW-16_Verify_Login_Fails_With_Invalid_Admin_Credentials",
            description = "Verify login fails with invalid admin credentials"
    )
    public void invalid_admin_login_test() throws InterruptedException {
        System.out.println("========== INVALID ADMIN LOGIN TEST STARTED ==========");

        AdminLoginPage adminPage = new AdminLoginPage(driver);

        // Step 1: Try to login with invalid credentials
        adminPage.loginWithInvalidCredentials();

        // Step 2: Verify error message "No account found with this email. Please sign up."
        String errorMessage = adminPage.getNoAccountErrorMessage();
        Assert.assertEquals(errorMessage, "No account found with this email. Please sign up.",
                "Error message doesn't match expected");

        // Step 3: Verify user remains on login page (URL validation)
        String currentUrl = adminPage.getCurrentPageUrl();
        Assert.assertTrue(currentUrl.contains("login") || currentUrl.contains("Login"),
                "User is not on login page. Current URL: " + currentUrl);

        System.out.println("========== INVALID ADMIN LOGIN TEST COMPLETED ==========");
    }
}