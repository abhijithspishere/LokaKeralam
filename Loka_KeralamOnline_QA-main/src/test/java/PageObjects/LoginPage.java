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

    @FindBy(xpath = "//p[text()='Passwords not match! Authentication failed.']")
    private WebElement errorMessageInvalidPassword;

    @FindBy(xpath = "(//div[contains(@class,'MuiAvatar-root') and normalize-space()='AS'])[2]")
    private WebElement profileIcon;

    @FindBy(xpath = "//span[normalize-space()='Logout']")
    private WebElement btnLogout;

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

        handleNetworkError();
        click(btnSignIn);
    }

    public void logout() {
        // Wait until profile icon is clickable
        wait.until(ExpectedConditions.elementToBeClickable(profileIcon));

        // Scroll to element (important for top-right icons)
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", profileIcon);

        // Small wait for UI stability
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // Click using JS (bypass overlay issue)
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", profileIcon);

        // Wait and click logout
        wait.until(ExpectedConditions.elementToBeClickable(btnLogout));
        click(btnLogout);
    }

    public String loginWithErrorPassword(String email) {
        click(btnWelcomeClose);
        click(btnExplore);

        wait.until(ExpectedConditions.visibilityOf(txtEmail));
        txtEmail.clear();
        txtEmail.sendKeys(email);

        wait.until(ExpectedConditions.visibilityOf(txtPassword));
        txtPassword.clear();
        txtPassword.sendKeys("IncorrectPassword123!");

        handleNetworkError();
        click(btnSignIn);

        return wait.until(ExpectedConditions
                        .visibilityOf(errorMessageInvalidPassword))
                .getText();
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