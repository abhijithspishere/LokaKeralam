package PageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Utils.ConfigReader;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AdminLoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(AdminLoginPage.class);

    @FindBy(xpath = "//input[@type='text' and ancestor::div[.//span[text()='Email']]]")
    private WebElement txtAdminEmail;

    @FindBy(xpath ="//span[text()='Password']/ancestor::div[contains(@class,'MuiOutlinedInput-root')]//input")
    private WebElement txtAdminPassword;

    @FindBy(xpath = "//button[contains(text(),'Log In')]")
    private WebElement btnAdminLogin;

    public AdminLoginPage(WebDriver driver) {
        super(driver);
    }

    public void loginAsAdmin(String email, String password) throws InterruptedException {

        sendKeys(txtAdminEmail, email);
        sendKeys(txtAdminPassword, password);
        wait.until(ExpectedConditions.visibilityOf(btnAdminLogin));
        wait.until(ExpectedConditions.elementToBeClickable(btnAdminLogin));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", btnAdminLogin);
        Thread.sleep(2000);
    }

    public boolean isDashboardDisplayed() {
        return driver.getCurrentUrl().contains("/ApproverDashboard") ||
                driver.getCurrentUrl().contains("admin");
    }


}