package Tests;

import Hooks.Hook;
import PageObjects.AdminLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminResubmitDisabled_Test018 extends Hook {

    @Test(
            priority = 18,
            testName = "CLKOI-AT-GCCDW-18_Verify_Resubmission_Blocked_When_Remarks_Empty",
            description = "Verify resubmission blocked when remarks field is empty"
    )
    public void verify_resubmission_blocked_empty_remarks() throws InterruptedException {
        System.out.println("========== RESUBMISSION WITH EMPTY REMARKS TEST STARTED ==========");

        AdminLoginPage adminPage = new AdminLoginPage(driver);

        // Login as admin
        adminPage.loginAsAdmin("mp1.norka@kerala.gov.in", "123456");

        // Navigate to View User List and click first row view button
        adminPage.clickFirstRowViewButton();

        // Click Resubmit button and keep remarks empty
        adminPage.clickResubmitWithEmptyRemarks();

        // Verify Resubmit button is disabled (form submission blocked)
        boolean isButtonDisabled = adminPage.isResubmitButtonDisabled();
        Assert.assertTrue(isButtonDisabled,
                "FAILED: Resubmit button is enabled when remarks field is empty!");

        System.out.println("✓ Test passed - Resubmit button is disabled with empty remarks");
        System.out.println("========== RESUBMISSION WITH EMPTY REMARKS TEST COMPLETED ==========");
    }
}