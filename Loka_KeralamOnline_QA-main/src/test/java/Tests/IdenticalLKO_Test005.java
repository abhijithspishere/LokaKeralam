package Tests;

import Hooks.Hook;
import PageObjects.ProfileCompletionPage;
import Utils.ConfigReader;
import Utils.CredentialsStorage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IdenticalLKO_Test005 extends Hook {

    @Test(
            priority = 5,
            testName = "CLKOI-AT-GCCDW-03_Verify_Identical_LKO_ID",
            description = "Verify Official LKO Id with User ID Identical"
    )
    public void verifyIdenticalLKOId() throws InterruptedException {
        System.out.println("========== IDENTICAL LKO ID VERIFICATION TEST STARTED ==========");

        // Get stored LKO ID from admin test
        String adminLkoId = CredentialsStorage.getStoredLKOId();
        Assert.assertNotNull(adminLkoId, "Admin LKO ID should not be null");

        // Get user credentials from registration
        String userEmail = CredentialsStorage.getRegisteredEmail();
        String userPassword = CredentialsStorage.getRegisteredPassword();
        Assert.assertNotNull(userEmail, "User email should not be null");
        Assert.assertNotNull(userPassword, "User password should not be null");

        // Login as user and fetch LKO ID from profile
        ProfileCompletionPage userPage = new ProfileCompletionPage(driver);
        String appUrl = ConfigReader.getAppUrl();
        driver.get(appUrl);
        Thread.sleep(2000);

        userPage.loginWithCredentials(userEmail, userPassword);
        String userLkoId = userPage.getUserLkoId();

        Assert.assertNotNull(userLkoId, "User LKO ID should not be null");

        // Compare LKO IDs
        System.out.println("Admin LKO ID: " + adminLkoId);
        System.out.println("User LKO ID: " + userLkoId);

        Assert.assertEquals(userLkoId, adminLkoId, "LKO IDs are not identical");

        System.out.println("SUCCESS: LKO IDs are identical - " + userLkoId);
        System.out.println("========== IDENTICAL LKO ID VERIFICATION TEST PASSED ==========");
    }
}