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
        String sheetName = "Sheet2";
        return ExcelUtils.getTestData(filePath, sheetName);
    }

    @Test(
            priority = 1,
            testName = "CLKOI-AT-GCCDW-02_Profile_Completion",
            description = "Verify profile completion with data from Excel",
            dataProvider = "profileData"
    )
    public void profile_completion_test(
            String aboutMe,
            String address,
            String pincode, String houseNo, String district,
            String companyName,
            String passportNo,
            String docNo,
            String fbUrl, String liUrl, String instaUrl
    ) {
        ProfileCompletionPage profilePage = new ProfileCompletionPage(driver);


        String email = CredentialsStorage.getRegisteredEmail();
        String password = CredentialsStorage.getRegisteredPassword();

        if (email == null || password == null) {
            logger.warn("Credentials not found in memory. Using fallback test credentials.");

            email = "asp472@yopmail.com";
            password = "Test@123";
        }

        test.log(Status.INFO, "Test User: " + email);
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
            test.log(Status.PASS, "Profile completed successfully");

            logger.info("========== PROFILE TEST PASSED ==========");

        } catch (Exception e) {
            logger.error("Test Failed: ", e);
            test.log(Status.FAIL, e.getMessage());
            Assert.fail(e.getMessage());
        }
        try {
            boolean isSuccess = profilePage.isProfileCompletionSuccessful();

            logger.info("Profile submission success status: " + isSuccess);
            test.log(Status.INFO, "Verifying dashboard redirection after login");

            Assert.assertTrue(
                    isSuccess,
                    "Profile Completion Failed Profile URL did not load."
            );

            test.log(Status.PASS, "Profile Completion successful");

        } catch (Exception e) {
            logger.error("Profile Completion test exception", e);
            test.log(Status.FAIL, "Exception during login: " + e.getMessage());
            Assert.fail("Exception occurred during login: " + e.getMessage());
        }
    }
}
