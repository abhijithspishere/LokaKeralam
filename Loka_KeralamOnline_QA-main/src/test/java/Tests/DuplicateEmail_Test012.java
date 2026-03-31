package Tests;

import Hooks.Hook;
import PageObjects.RegistrationPage;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DuplicateEmail_Test012 extends Hook {

    @Test(
            priority = 12,
            testName = "CLKOI-AT-GCCDW-012_Duplicate_Email_Validation",
            description = "Verify duplicate email registration prevention"
    )
    public void duplicateRegistration_test() throws InterruptedException {

        RegistrationPage rp = new RegistrationPage(driver);

        logger.info("Starting duplicate email registration test");
        test.log(Status.INFO, "Starting duplicate email registration test");

        // Navigation
        rp.clickCloseButton();
        rp.clickExploreButton();
        rp.duplicateRegister();


        // ✅ Handle alert
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String alertText = alert.getText();
        System.out.println("Alert Message: " + alertText);

        alert.accept();

        // ✅ Assertion
        Assert.assertEquals(alertText,
                "The email address you entered is already registered in the system");

        logger.info("Duplicate email validation successful");
        test.log(Status.PASS, "Duplicate email validation successful");
    }
}