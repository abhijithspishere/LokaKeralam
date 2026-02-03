package Tests;

import Hooks.Hook;
import PageObjects.LoginPage;
import Utils.CredentialsStorage;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class LoginPage_Test extends Hook {

    @Test(
            priority = 1,
            testName = "CLKOI-AT-GCCDW-02_Login_With_Registered_User",
            description = "Verify login using credentials created in Registration Test"
    )
    public void login_test() {

        // 1. Fetch the credentials from Storage
        String registeredEmail = CredentialsStorage.getRegisteredEmail();
        String registeredPassword = CredentialsStorage.getRegisteredPassword();

        logger.info("========== LOGIN TEST STARTED ==========");

        // 2. Validate that credentials exist (prevents null pointer if Registration failed/didn't run)
        if (registeredEmail == null || registeredPassword == null) {
            String errorMsg = "Credentials not found! Ensure Registration Test runs successfully before this test.";
            logger.error(errorMsg);
            test.log(Status.SKIP, errorMsg);
            throw new SkipException(errorMsg);
        }

        logger.info("Fetching credentials from Storage...");
        logger.info("Email: " + registeredEmail);

        test.log(Status.INFO, "Attempting login with registered email: " + registeredEmail);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithCredentials(registeredEmail, registeredPassword);
        loginPage.isLoginSuccessful();

        try {
            boolean isSuccess = loginPage.isLoginSuccessful();

            logger.info("Login success status: " + isSuccess);
            test.log(Status.INFO, "Verifying dashboard redirection after login");

            Assert.assertTrue(
                    isSuccess,
                    "Login verification failed: Dashboard URL did not load."
            );

            test.log(Status.PASS, "Login successful - Redirected to Dashboard");

        } catch (Exception e) {
            logger.error("Login test exception", e);
            test.log(Status.FAIL, "Exception during login: " + e.getMessage());
            Assert.fail("Exception occurred during login: " + e.getMessage());
        }

    }
}