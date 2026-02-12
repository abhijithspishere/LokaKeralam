package Tests;

import Hooks.Hook;
import PageObjects.AdminLoginPage;
import Utils.ConfigReader;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminLoginTest extends Hook {

    @Test(
            priority = 3, // Run after user registration and login tests
            testName = "CLKOI-AT-GCCDW-03_Admin_Login_Test",
            description = "Verify Official Admin Login functionality"
    )
    public void admin_login_test() {
        logger.info("========== ADMIN LOGIN TEST STARTED ==========");
        AdminLoginPage adminPage = new AdminLoginPage(driver);

        adminPage.loginAsAdmin(
                ConfigReader.getProperty("admin.username"),
                ConfigReader.getProperty("admin.password")
        );

        Assert.assertTrue(adminPage.isDashboardDisplayed(),
                "Admin login failed - Dashboard not displayed");
    }
    }