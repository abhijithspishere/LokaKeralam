package Tests;

import Hooks.Hook;
import PageObjects.LoginPage;
import Utils.CredentialsStorage;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class InvalidPassword_Test013 extends Hook {

    @Test(
            priority = 13,
            testName = "CLKOI-AT-GCCDW-03_Login_With_Invalid_Password",
            description = "Verify login fails with incorrect password and proper error message is displayed"
    )
    public void loginWithInvalidPassword_test() {

        // 1. Fetch registered email
        String registeredEmail = "asp827@yopmail.com";
        LoginPage loginPage = new LoginPage(driver);

        try {
            String actualError = loginPage.loginWithErrorPassword(registeredEmail);

            String expectedError = "Passwords not match! Authentication failed.";

            logger.info("Captured Error Message: " + actualError);
            test.log(Status.INFO, "Captured error message: " + actualError);

            // 4. Assertion
            Assert.assertEquals(
                    actualError,
                    expectedError,
                    "Error message validation failed!"
            );

            test.log(Status.PASS, "Proper error message displayed for invalid password");

        } catch (Exception e) {
            logger.error("Invalid password test exception", e);
            test.log(Status.FAIL, "Exception during invalid login: " + e.getMessage());
            Assert.fail("Exception occurred during invalid login: " + e.getMessage());
        }
    }
}