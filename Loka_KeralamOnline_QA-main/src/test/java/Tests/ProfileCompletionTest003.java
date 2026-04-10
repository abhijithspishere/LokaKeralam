package Tests;

import Hooks.Hook;
import PageObjects.ProfileCompletionPage;
import Utils.CredentialsStorage;
import Utils.ExcelUtils;
import Utils.FileConstants;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.IOException;

public class ProfileCompletionTest003 extends Hook {

    @DataProvider(name = "profileData")
    public Object[][] getProfileData() throws IOException {
        String filePath = "src/test/resources/testdata/RegistrationData.xlsx";
        String sheetName = "EmployeeProfile";
        return ExcelUtils.getTestData(filePath, sheetName);
    }

    @Test(
            priority = 3,
            testName = "CLKOI-AT-GCCDW-03_Employee_Profile_Completion",
            description = "Verify employee profile completion with data from Excel",
            dataProvider = "profileData"
    )
    public void employee_profile_completion_test(
            String aboutMe,
            String address,
            String pincode, String houseNo, String district,
            String companyName,
            String passportNo,
            String docNo,
            String fbUrl, String liUrl, String instaUrl
    )
            throws InterruptedException {

        ProfileCompletionPage profilePage = new ProfileCompletionPage(driver);

        // Get EMPLOYEE credentials specifically
        String email = CredentialsStorage.getRegisteredEmail();
        String password = CredentialsStorage.getRegisteredPassword();

        if (email == null || password == null) {
            logger.warn("Employee credentials not found in memory. Using fallback test credentials.");
            email = "asp472@yopmail.com";
            password = "Test@123";
            test.log(Status.WARNING, "Using fallback employee credentials");
        } else {
            test.log(Status.INFO, "Using stored employee credentials from registration");
        }

        test.log(Status.INFO, "Test Employee: " + email);
        profilePage.loginWithCredentials(email, password);

        try {
            profilePage.navigateToProfile();

            profilePage.captureImage();
            test.log(Status.INFO, "Image capture completed");

            profilePage.updateBasicInfo(aboutMe);
            profilePage.updateNRKAddress(address);
            profilePage.updateKeralaAddress(pincode, houseNo, district);

            profilePage.updateProfessionalInfo(companyName);

            profilePage.updatePassportDetails(
                    passportNo,
                    FileConstants.PASSPORT
            );

            profilePage.updateResidenceProof(
                    docNo,
                    FileConstants.AADHAR
            );

            profilePage.updateSocialLinks(fbUrl, liUrl, instaUrl);
            test.log(Status.PASS, "Employee profile completed successfully");

            logger.info("========== EMPLOYEE PROFILE TEST PASSED ==========");

        } catch (Exception e) {
            logger.error("Test Failed: ", e);
            test.log(Status.FAIL, e.getMessage());
            Assert.fail(e.getMessage());
        }

        try {
            boolean isSuccess = profilePage.isProfileCompletionSuccessful();

            logger.info("Employee profile submission success status: " + isSuccess);
            test.log(Status.INFO, "Verifying dashboard redirection after login");

            Assert.assertTrue(
                    isSuccess,
                    "Employee Profile Completion Failed - Profile URL did not load."
            );

            test.log(Status.PASS, "Employee Profile Completion successful");


        } catch (Exception e) {
            logger.error("Employee Profile Completion test exception", e);
            test.log(Status.FAIL, "Exception during profile completion: " + e.getMessage());
            Assert.fail("Exception occurred during profile completion: " + e.getMessage());
        }
    }

}