package Tests;

import Hooks.Hook;
import PageObjects.AdminLoginPage;
import Utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminReject_Test011 extends Hook {

    @Test(
            priority = 11,
            testName = "CLKOI-AT-GCCDW-Reject_Test",
            description = "Verify Admin can reject a user with remarks"
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
        adminPage.clickFirstRowViewButton();
        adminPage.RejectUser();

        System.out.println("========== ADMIN RESUBMIT TEST COMPLETED ==========");
    }
}