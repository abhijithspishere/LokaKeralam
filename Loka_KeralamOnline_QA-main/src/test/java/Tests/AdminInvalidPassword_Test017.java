package Tests;

import Hooks.Hook;
import PageObjects.AdminLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminInvalidPassword_Test017 extends Hook {

    @Test(
            priority = 17,
            testName = "CLKOI-AT-GCCDW-17_Verify_Login_Fails_With_Wrong_Password",
            description = "Verify login fails with valid email but wrong password"
    )
    public void wrong_password_login_test() throws InterruptedException {
        System.out.println("========== WRONG PASSWORD LOGIN TEST STARTED ==========");

        AdminLoginPage adminPage = new AdminLoginPage(driver);

        // Step 1: Login with valid email but wrong password
        adminPage.loginWithWrongPassword();

        // Step 2: Verify error message "Passwords not match"
        String errorMessage = adminPage.getPasswordNotMatchErrorMessage();
        Assert.assertEquals(errorMessage, "Passwords not match! Authentication failed.",
                "Error message doesn't match expected");

        // Step 3: Verify user remains on login page (URL validation)
        String currentUrl = adminPage.getCurrentPageUrl();
        Assert.assertTrue(currentUrl.contains("login") || currentUrl.contains("Login") || currentUrl.contains("OfficialLogin"),
                "User is not on login page. Current URL: " + currentUrl);

        System.out.println("✓ Error message verified: " + errorMessage);
        System.out.println("✓ Current URL: " + currentUrl);
        System.out.println("========== WRONG PASSWORD LOGIN TEST COMPLETED ==========");
    }
}