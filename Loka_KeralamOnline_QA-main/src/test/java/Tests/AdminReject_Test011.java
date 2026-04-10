    package Tests;

    import Hooks.Hook;
    import PageObjects.AdminLoginPage;
    import PageObjects.LoginPage;
    import Utils.ConfigReader;
    import Utils.CredentialsStorage;
    import com.aventstack.extentreports.Status;
    import org.testng.Assert;
    import org.testng.SkipException;
    import org.testng.annotations.Test;

    public class AdminReject_Test011 extends Hook {

        @Test(
                priority = 11,
                testName = "CLKOI-AT-GCCDW-Reject_Test",
                description = "Verify Admin can reject a user with remarks"
        )
        public void admin_reject_test() throws InterruptedException {

            System.out.println("========== ADMIN REJECT TEST STARTED ==========");


            // 🔍 DEBUG: Check what credentials are actually stored
            System.out.println("\n=== DEBUG: Current CredentialsStorage Content ===");
            System.out.println("Employer Email: " + CredentialsStorage.getEmployerEmail());
            System.out.println("Employer Password: " + CredentialsStorage.getEmployerPassword());
            System.out.println("================================================\n");



            String registeredEmail = CredentialsStorage.getEmployerEmail();    // CHANGED
            String registeredPassword = CredentialsStorage.getEmployerPassword();

            if (registeredEmail == null || registeredPassword == null) {
                String errorMsg = "Credentials not found! Ensure Registration Test (priority=1) runs successfully before this test.";
                logger.error(errorMsg);
                test.log(Status.SKIP, errorMsg);
                throw new SkipException(errorMsg);
            }
            logger.info("Fetching credentials from Storage...");
            logger.info("Email: " + registeredEmail);

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

            driver.get(ConfigReader.getProperty("qa.app.url"));

            test.log(Status.INFO, "Attempting login with registered email: " + registeredEmail);

            LoginPage loginPage = new LoginPage(driver);
            loginPage.loginWithCredentials(registeredEmail, registeredPassword);
            loginPage.verifyRejectedUserLogin(driver);
        }

    }