package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Utils.ConfigReader;

public class AdminLoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(AdminLoginPage.class);

    @FindBy(xpath = "//input[@type='text' and ancestor::div[.//span[text()='Email']]]")
    private WebElement txtAdminEmail;

    @FindBy(xpath ="//span[text()='Password']/ancestor::div[contains(@class,'MuiOutlinedInput-root')]//input")
    private WebElement txtAdminPassword;

    @FindBy(xpath = "//button[normalize-space()='Log In']")
    private WebElement btnAdminLogin;

    public AdminLoginPage(WebDriver driver) {
        super(driver);
        logger.info("AdminLoginPage initialized");
    }
    public void navigateToAdminPortal() {
        String adminUrl = ConfigReader.getProperty("admin.url");
        logger.info("Navigating to Admin Portal: " + adminUrl);
        driver.get(adminUrl);
        waitForPageLoad();
    }


}