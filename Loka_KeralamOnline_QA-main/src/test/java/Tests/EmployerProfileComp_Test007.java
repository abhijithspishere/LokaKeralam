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

public class EmployerProfileComp_Test007 extends Hook {

    @DataProvider(name = "profileData")
    public Object[][] getProfileData() throws IOException {
        String filePath = "src/test/resources/testdata/RegistrationData.xlsx";
        String sheetName = "EmployerProfile";
        return ExcelUtils.getTestData(filePath, sheetName);
    }

    @Test(
            priority = 7,
            testName = "CLKOI-AT-GCCDW-07_Employer_Profile_Completion",
            description = "Verify employer profile completion with data from Excel",
            dataProvider = "profileData"
    )
    public void employer_profile_completion_test(
            String aboutMe,
            String address,
            String pincode,
            String houseNo,
            String district,
            String businessDescription,
            String passportNo,
            String docNo,
            String fbUrl,
            String liUrl,
            String instaUrl
    )
            throws InterruptedException {

        ProfileCompletionPage profilePage = new ProfileCompletionPage(driver);

        // Get EMPLOYER credentials specifically
        String email = CredentialsStorage.getEmployerEmail();
        String password = CredentialsStorage.getEmployerPassword();

        if (email == null || password == null) {
            System.out.println("Employer credentials not found in memory. Using fallback test credentials.");
            email = "employer_test@yopmail.com";
            password = "Test@123";
            test.log(Status.WARNING, "Using fallback employer credentials");
        } else {
            test.log(Status.INFO, "Using stored employer credentials from registration");
        }

        test.log(Status.INFO, "Test Employer: " + email);
        profilePage.loginWithCredentials(email, password);

        try {
            profilePage.navigateToProfile();

            profilePage.captureImage();
            test.log(Status.INFO, "Image capture completed");

            profilePage.updateBasicInfo(aboutMe);
            profilePage.updateNRKAddress(address);
            profilePage.updateKeralaAddress(pincode, houseNo, district);

            // Using updateBusinessInfo for employer
            profilePage.updateBusinessinfo(businessDescription);

            profilePage.updatePassportDetails(
                    passportNo,
                    FileConstants.PASSPORT
            );

            profilePage.updateResidenceProof(
                    docNo,
                    FileConstants.AADHAR
            );

            profilePage.updateSocialLinks(fbUrl, liUrl, instaUrl);
            test.log(Status.PASS, "Employer profile completed successfully");

            System.out.println("========== EMPLOYER PROFILE TEST PASSED ==========");

        } catch (Exception e) {
            System.out.println("Test Failed: " + e.getMessage());
            test.log(Status.FAIL, e.getMessage());
            Assert.fail(e.getMessage());
        }

        try {
            boolean isSuccess = profilePage.isProfileCompletionSuccessful();

            System.out.println("Employer profile submission success status: " + isSuccess);
            test.log(Status.INFO, "Verifying dashboard redirection after login");

            Assert.assertTrue(
                    isSuccess,
                    "Employer Profile Completion Failed - Profile URL did not load."
            );

            test.log(Status.PASS, "Employer Profile Completion successful");

        } catch (Exception e) {
            System.out.println("Employer Profile Completion test exception: " + e.getMessage());
            test.log(Status.FAIL, "Exception during profile completion: " + e.getMessage());
            Assert.fail("Exception occurred during profile completion: " + e.getMessage());
        }
    }
}