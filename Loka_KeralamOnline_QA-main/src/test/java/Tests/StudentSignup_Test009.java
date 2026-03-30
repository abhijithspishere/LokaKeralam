package Tests;

import Hooks.Hook;
import PageObjects.RegistrationPage;
import Utils.ConfigReader;
import Utils.CredentialsStorage;  // Add this import
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import java.io.IOException;
import Utils.ExcelUtils;

public class StudentSignup_Test009 extends Hook {

    @DataProvider(name = "registrationData")
    public Object[][] getRegistrationData() throws IOException {
        return ExcelUtils.getTestData("src/test/resources/testdata/RegistrationData.xlsx", "Sheet5");
    }

    @Test(priority = 6, dataProvider = "registrationData")
    public void employer_signup_test(
            String yopmailUsername,
            String firstName,
            String middleName,
            String lastName,
            String phoneNumber,
            String password
    ) throws InterruptedException {
        CredentialsStorage.clearCredentials();
        RegistrationPage regPage = new RegistrationPage(driver);
        String appUrl = ConfigReader.getAppUrl();
        driver.get(appUrl);
        Thread.sleep(2000);

        regPage.clickCloseButton();
        regPage.clickExploreButton();
        regPage.registerWithYopmail(yopmailUsername);
        regPage.selectCountryAndCity();
        regPage.fillPersonalDetails(firstName, middleName, lastName, phoneNumber);
        regPage.selectStudent();
        regPage.setExperienceAndSubmit(password, 5);

        // Wait for registration to complete
        Thread.sleep(3000);

        // Verify registration was successful
        boolean isRegistered = regPage.isRegistrationSuccessful();

        if (isRegistered) {
            // CRITICAL: Store the employer credentials
            String email = yopmailUsername + "@yopmail.com";
            CredentialsStorage.storeEmployerCredentials(email, password);

            logger.info("✓ EMPLOYER CREDENTIALS STORED:");
            logger.info("  Email: " + email);
            logger.info("  Password: " + password);
        } else {
            logger.error("Employer registration failed for: " + firstName + " " + lastName);
        }

        // Logout the registered employer
        regPage.logoutRegisteredUser();

        // Print debug info
        System.out.println("\n========== EMPLOYER REGISTRATION COMPLETED ==========");
        System.out.println("Email: " + yopmailUsername + "@yopmail.com");
        System.out.println("Password: " + password);
        System.out.println("Stored in CredentialsStorage: " + CredentialsStorage.getEmployerEmail());
        System.out.println("==================================================\n");
    }
}