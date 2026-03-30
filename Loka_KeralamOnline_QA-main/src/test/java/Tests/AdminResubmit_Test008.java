package Tests;

import Hooks.Hook;
import PageObjects.AdminLoginPage;
import Utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminResubmit_Test008 extends Hook {

    @Test(
            priority = 8,
            testName = "CLKOI-AT-GCCDW-08_Admin_Resubmit_Test",
            description = "Verify Admin can resubmit a user with remarks"
    )
    public void admin_resubmit_test() throws InterruptedException {

        System.out.println("========== ADMIN RESUBMIT TEST STARTED ==========");

        AdminLoginPage adminPage = new AdminLoginPage(driver);

        // Login
        adminPage.loginAsAdmin(
                ConfigReader.getProperty("admin.username"),
                ConfigReader.getProperty("admin.password")
        );

        Assert.assertTrue(adminPage.isDashboardDisplayed(),
                "Admin login failed - Dashboard not displayed");

        // Navigate to user
        adminPage.clickFirstRowViewButton();

        // Resubmit user with remarks
        adminPage.ResubmitUser();

        System.out.println("========== ADMIN RESUBMIT TEST COMPLETED ==========");
    }
}