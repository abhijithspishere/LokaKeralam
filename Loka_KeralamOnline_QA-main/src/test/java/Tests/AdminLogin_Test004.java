package Tests;

import Hooks.Hook;
import PageObjects.AdminLoginPage;
import Utils.ConfigReader;
import Utils.CredentialsStorage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminLogin_Test004 extends Hook {

    @Test(
            priority = 4, // Run after user registration and login tests
            testName = "CLKOI-AT-GCCDW-03_Admin_Login_Test",
            description = "Verify Official Admin Login functionality"
    )
    public void admin_login_test() throws InterruptedException {
        System.out.println("========== ADMIN LOGIN TEST STARTED ==========");
        AdminLoginPage adminPage = new AdminLoginPage(driver);

        adminPage.loginAsAdmin(
                ConfigReader.getProperty("admin.username"),
                ConfigReader.getProperty("admin.password")
        );
        adminPage.isDashboardDisplayed();

        Assert.assertTrue(adminPage.isDashboardDisplayed(),
                "Admin login failed - Dashboard not displayed");

        adminPage.clickFirstRowViewButton();
        adminPage.aprroveUser();
        String lkoId = adminPage.fetchLKO_ID();

        // Store the LKO ID
        CredentialsStorage.storeLKOId(lkoId);
        System.out.println("========== ADMIN LOGIN TEST COMPLETED ==========");
    }
}