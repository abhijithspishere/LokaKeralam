package Tests;

import Hooks.Hook;
import PageObjects.RegistrationPage;
import Utils.CredentialsStorage;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import java.io.IOException;
import Utils.ExcelUtils;

public class RegistrationPage_Test001 extends Hook {

    @DataProvider(name = "registrationData")
    public Object[][] getRegistrationData() throws IOException {
        String filePath = "src/test/resources/testdata/RegistrationData.xlsx";
        String sheetName = "Employee";
        return ExcelUtils.getTestData(filePath, sheetName);
    }

    @Test(
            priority = 1,
            testName = "CLKOI-AT-GCCDW-01_Complete_Registration_Flow",
            description = "Verify complete end-to-end registration flow using OTP",
            dataProvider = "registrationData"
    )
    public void registration_test(
            String yopmailUsername,
            String firstName,
            String middleName,
            String lastName,
            String phoneNumber,
            String password
    ) {
        CredentialsStorage.clearCredentials();
        int experienceYears = 5;

        logger.info("========== TEST STARTED ==========");
        logger.info("User: " + firstName + " " + lastName);
        logger.info("Yopmail Username: " + yopmailUsername);
        logger.info("Phone: " + phoneNumber);

        test.log(Status.INFO, "Test started");
        test.log(Status.INFO, "User: " + firstName + " " + lastName);
        test.log(Status.INFO, "Yopmail Username: " + yopmailUsername);
        test.log(Status.INFO, "Phone: " + phoneNumber);

        RegistrationPage registrationPage = new RegistrationPage(driver);

        try {
            logger.info("STEP 1: Closing popup and exploring");
            registrationPage.clickCloseButton();
            registrationPage.clickExploreButton();

            logger.info("STEP 2: Registering with Yopmail: " + yopmailUsername);
            registrationPage.registerWithYopmail(yopmailUsername);

            logger.info("STEP 3: Selecting country and city");
            registrationPage.selectCountryAndCity();

            logger.info("STEP 4: Filling personal details");
            registrationPage.fillPersonalDetails(
                    firstName,
                    middleName,
                    lastName,
                    phoneNumber
            );

            // Step 4.5: Select user description (Employer)
            logger.info("STEP 4.5: Selecting user description as Employer");
            registrationPage.selectEmployee();  // Assuming you have this method

            // Step 4.6: Set experience and submit with password
            logger.info("STEP 4.6: Setting experience and submitting registration");
            registrationPage.setExperienceAndSubmit(password, experienceYears);

            logger.info("STEP 5: Validating registration");
            boolean isRegistered = registrationPage.isRegistrationSuccessful();

            Assert.assertTrue(
                    isRegistered,
                    "Registration failed for user: " + firstName + " " + lastName
            );

            // Create the full email
            String email = yopmailUsername + "@yopmail.com";

            // Store credentials in CredentialsStorage
            // This will now also store the yopmail username internally
            CredentialsStorage.storeCredentials(email, password);

            logger.info("Employer credentials stored for profile completion test:");
            logger.info("Employer Email: " + email);
            logger.info("Employer Password: " + password);
            logger.info("Yopmail Username: " + yopmailUsername);

            logger.info("Registration validation successful");
            test.log(Status.PASS, "Registration verified successfully");

            logger.info("STEP 6: Logging out registered user");
            registrationPage.logoutRegisteredUser();
            logger.info("User logged out successfully");

            test.log(Status.PASS, "End-to-end registration flow completed successfully");
            logger.info("========== TEST PASSED ==========");

        } catch (Exception e) {
            logger.error("Test failed: ", e);
            test.log(Status.FAIL, "Test failed: " + e.getMessage());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}