package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

public class LoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    // Existing 'Close' button (likely for the welcome popup)
    @FindBy(xpath = "//button[normalize-space()='Close']")
    private WebElement btnWelcomeClose;

    // NEW 'CLOSE' button for the Captcha/Network Error (Uppercase based on your HTML)
    @FindBy(xpath = "//button[normalize-space()='CLOSE']")
    private WebElement btnErrorClose;

    @FindBy(xpath = "//button[normalize-space()='Click to Explore']")
    private WebElement btnExplore;

    @FindBy(xpath = "//fieldset[.//span[text()='Email']]/preceding-sibling::input")
    private WebElement txtEmail;

    @FindBy(xpath = "//fieldset[.//span[text()='Password']]/preceding-sibling::input")
    private WebElement txtPassword;

    @FindBy(xpath = "//button[normalize-space()='Sign In']")
    private WebElement btnSignIn;

    public LoginPage(WebDriver driver) {
        super(driver);
        logger.info("LoginPage initialized");
    }

    public void loginWithCredentials(String email, String password) {
        // Handle initial welcome popups
        click(btnWelcomeClose);
        click(btnExplore);

        // Enter Credentials
        wait.until(ExpectedConditions.visibilityOf(txtEmail));
        txtEmail.clear();
        txtEmail.sendKeys(email);

        wait.until(ExpectedConditions.visibilityOf(txtPassword));
        txtPassword.clear();
        txtPassword.sendKeys(password);

        click(btnSignIn);

        handleNetworkError();
    }

    public void handleNetworkError() {
        try {

            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));


            WebElement closeBtn = shortWait.until(ExpectedConditions.visibilityOf(btnErrorClose));

            if (closeBtn.isDisplayed()) {
                logger.warn("Network/Captcha error popup detected. Closing it...");
                closeBtn.click();
            }
        } catch (Exception e) {

            logger.info("No network error popup appeared. Proceeding...");
        }
        click(btnSignIn);
    }

    public boolean isLoginSuccessful() {
        String expectedUrl = "https://qa.lokakeralamonline.kerala.gov.in/dashboard";
        try {
            wait.until(ExpectedConditions.urlContains(expectedUrl));
            return true;
        } catch (Exception e) {
            System.out.println("Login validation failed. Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }
}