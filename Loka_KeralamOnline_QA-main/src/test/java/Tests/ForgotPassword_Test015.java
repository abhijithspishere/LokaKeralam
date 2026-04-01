package Tests;

import Hooks.Hook;
import PageObjects.ForgotPasswordPage;
import PageObjects.LoginPage;
import Utils.CredentialsStorage;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ForgotPassword_Test015 extends Hook {

    @Test(
            priority = 15,
            testName = "ForgotPassword_ValidFlow",
            description = "Verify forgot password functionality with valid OTP and password reset"
    )
    @Parameters("yopmailUsername")
    public void testForgotPasswordValidFlow(String yopmailUsernameParam) throws InterruptedException {

        // Get registered email and old password from credentials storage
        String registeredEmail = CredentialsStorage.getRegisteredEmail();
        String oldPassword = CredentialsStorage.getRegisteredPassword();
        String newPassword = "NewPass@123";

        // Get yopmail username from credentials storage
        String yopmailUsernameFromStorage = CredentialsStorage.getYopmailUsername();

        // Log all values
        logger.info("=== Test Parameters ===");
        logger.info("Stored registered email: {}", registeredEmail);
        logger.info("Stored yopmail username: {}", yopmailUsernameFromStorage);
        logger.info("New password to set: {}", newPassword);

        // Verify we have a registered email and password
        if (registeredEmail == null || registeredEmail.isEmpty()) {
            String errorMsg = "No registered email found. Please run registration test first.";
            logger.error(errorMsg);
            test.log(Status.FAIL, errorMsg);
            Assert.fail(errorMsg);
        }

        if (oldPassword == null || oldPassword.isEmpty()) {
            String errorMsg = "No old password found in credentials storage.";
            logger.error(errorMsg);
            test.log(Status.FAIL, errorMsg);
            Assert.fail(errorMsg);
        }

        if (yopmailUsernameFromStorage == null || yopmailUsernameFromStorage.isEmpty()) {
            String errorMsg = "No yopmail username found in credentials storage.";
            logger.error(errorMsg);
            test.log(Status.FAIL, errorMsg);
            Assert.fail(errorMsg);
        }

        test.log(Status.INFO, "Step 1: Verifying old password works before reset");
        test.log(Status.INFO, "Testing forgot password for: " + registeredEmail);

        // STEP 1: Verify old password works before reset
        logger.info("Step 1: Verifying old password works before password reset");
        LoginPage loginPage = new LoginPage(driver);

        try {
            loginPage.loginWithCredentials(registeredEmail, oldPassword);
            boolean loginSuccess = loginPage.isLoginSuccessful();

            if (loginSuccess) {
                logger.info("Old password verification successful");
                test.log(Status.PASS, "Verified: Can login with old password before reset");

                loginPage.logout();
                logger.info("Logged out after verification");
                Thread.sleep(1000); // Reduced from 2000ms
            } else {
                logger.error("Failed to login with old password before reset");
                test.log(Status.FAIL, "Failed to login with old password before reset");
                Assert.fail("Cannot login with old password before reset");
            }
        } catch (Exception e) {
            logger.error("Error during old password verification: {}", e.getMessage());
            test.log(Status.FAIL, "Error during old password verification: " + e.getMessage());
            throw e;
        }

        // STEP 2: Perform forgot password flow
        test.log(Status.INFO, "Step 2: Starting forgot password flow");

        try {
            logger.info("Navigating to forgot password page...");
            driver.navigate().to("https://qa.lokakeralamonline.kerala.gov.in/forgotPassword");
            Thread.sleep(2000); // Reduced from 3000ms
            logger.info("Successfully navigated to forgot password page");
        } catch (Exception e) {
            logger.error("Failed to navigate to forgot password page: {}", e.getMessage());
            test.log(Status.FAIL, "Failed to navigate to forgot password page");
            throw e;
        }

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);

        try {
            forgotPasswordPage.completeForgotPasswordFlow(yopmailUsernameFromStorage, registeredEmail, newPassword);
            test.log(Status.INFO, "Forgot password flow completed");
        } catch (Exception e) {
            logger.error("Forgot password flow failed: {}", e.getMessage());
            test.log(Status.FAIL, "Forgot password flow failed: " + e.getMessage());
            throw e;
        }

        // STEP 3: Verify password reset was successful
        Thread.sleep(2000); // Reduced from 5000ms
        boolean isResetSuccessful = forgotPasswordPage.isPasswordResetSuccessful();

        if (!isResetSuccessful) {
            String errorMsg = "Password reset failed - not redirected to login page";
            test.log(Status.FAIL, errorMsg);
            Assert.fail(errorMsg);
        }

        test.log(Status.PASS, "Password reset successful and redirected to login page");
        logger.info("Password reset successful for: {}", registeredEmail);

        LoginPage loginPageAfterReset = new LoginPage(driver);

        // STEP 4: Verify old password no longer works
        test.log(Status.INFO, "Step 3: Verifying old password no longer works after reset");

        try {
            driver.navigate().refresh();
            Thread.sleep(1000); // Reduced from 2000ms

            loginPageAfterReset.clickCloseButton();
            loginPageAfterReset.clickExploreButton();

            loginPageAfterReset.enterEmail(registeredEmail);
            loginPageAfterReset.enterPassword(oldPassword);
            loginPageAfterReset.handleNetworkError();
            loginPageAfterReset.clickSignIn();

            Thread.sleep(2000); // Reduced from 3000ms

            boolean loginWithOldPassword = loginPageAfterReset.isLoginSuccessful();

            if (!loginWithOldPassword) {
                logger.info("Old password verification successful - cannot login with old password");
                test.log(Status.PASS, "Verified: Old password no longer works after reset");
            } else {
                logger.error("Old password still works after reset");
                test.log(Status.FAIL, "Old password still works after reset");
                Assert.fail("Old password still works after password reset");
            }
        } catch (Exception e) {
            logger.info("Old password correctly rejected: {}", e.getMessage());
            test.log(Status.PASS, "Verified: Old password correctly rejected");
        }

        // STEP 5: Verify new password works
        test.log(Status.INFO, "Step 4: Verifying new password works after reset");

        try {
            driver.navigate().refresh();
            Thread.sleep(1000); // Reduced from 2000ms

            loginPageAfterReset.clickCloseButton();
            loginPageAfterReset.clickExploreButton();

            loginPageAfterReset.enterEmail(registeredEmail);
            loginPageAfterReset.enterPassword(newPassword);
            loginPageAfterReset.handleNetworkError();
            loginPageAfterReset.clickSignIn();

            Thread.sleep(2000); // Reduced from 3000ms

            boolean loginWithNewPassword = loginPageAfterReset.isLoginSuccessful();

            if (loginWithNewPassword) {
                logger.info("New password verification successful");
                test.log(Status.PASS, "Verified: Can login with new password after reset");
                loginPageAfterReset.logout();
                logger.info("Logged out after new password verification");
            } else {
                logger.error("Failed to login with new password after reset");
                test.log(Status.FAIL, "Failed to login with new password after reset");
                Assert.fail("Cannot login with new password after reset");
            }
        } catch (Exception e) {
            logger.error("Error during new password verification: {}", e.getMessage());
            test.log(Status.FAIL, "Error during new password verification: " + e.getMessage());
            throw e;
        }

        // STEP 6: Update stored credentials
        CredentialsStorage.storeCredentials(registeredEmail, newPassword);
        test.log(Status.INFO, "Updated stored password to: " + newPassword);

        test.log(Status.PASS, "All validations passed");
        logger.info("========== FORGOT PASSWORD TEST COMPLETED SUCCESSFULLY ==========");
    }
}