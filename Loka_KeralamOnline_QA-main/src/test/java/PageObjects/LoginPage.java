package PageObjects;

import Utils.CredentialsStorage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

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

    // Direct XPATHs for email and password fields (same as ProfileCompletionPage)
    @FindBy(xpath = "//fieldset[.//span[text()='Email']]/preceding-sibling::input")
    private WebElement txtEmail;

    @FindBy(xpath = "//fieldset[.//span[text()='Password']]/preceding-sibling::input")
    private WebElement txtPassword;

    @FindBy(xpath = "//button[normalize-space()='Sign In']")
    private WebElement btnSignIn;

    @FindBy(xpath = "//p[text()='Passwords not match! Authentication failed.']")
    private WebElement errorMessageInvalidPassword;

    @FindBy(xpath = "(//div[contains(@class,'MuiAvatar-root')])[last()]")
    private WebElement profileIcon;

    @FindBy(xpath = "//span[normalize-space()='Logout']")
    private WebElement btnLogout;

    @FindBy(xpath = "//button[normalize-space()='Resubmit Profile']")
    private WebElement btnResubmitProfile;

    public LoginPage(WebDriver driver) {
        super(driver);
        logger.info("LoginPage initialized");
    }

    public void loginWithCredentials(String email, String password) {
        // Handle initial welcome popups
        click(btnWelcomeClose);
        click(btnExplore);

        // Enter Credentials using direct XPATHs
        wait.until(ExpectedConditions.visibilityOf(txtEmail));
        txtEmail.clear();
        txtEmail.sendKeys(email);

        wait.until(ExpectedConditions.visibilityOf(txtPassword));
        txtPassword.clear();
        txtPassword.sendKeys(password);

        handleNetworkError();
        click(btnSignIn);

    }

    /**
     * Check if login fails with invalid password
     */
    public boolean isLoginFailedWithInvalidPassword() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement errorMsg = shortWait.until(
                    ExpectedConditions.visibilityOf(errorMessageInvalidPassword)
            );
            return errorMsg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click Close button on login page
     */
    public void clickCloseButton() {
        try {
            if (btnWelcomeClose.isDisplayed()) {
                click(btnWelcomeClose);
                logger.info("Clicked Close button on login page");
            }
        } catch (Exception e) {
            logger.debug("Close button not present: {}", e.getMessage());
        }
    }

    /**
     * Click Explore button on login page
     */
    public void clickExploreButton() {
        try {
            if (btnExplore.isDisplayed()) {
                click(btnExplore);
                logger.info("Clicked Explore button on login page");
            }
        } catch (Exception e) {
            logger.debug("Explore button not present: {}", e.getMessage());
        }
    }

    /**
     * Enter email in email field
     */
    public void enterEmail(String email) {
        try {
            wait.until(ExpectedConditions.visibilityOf(txtEmail));
            txtEmail.clear();
            txtEmail.sendKeys(email);
            logger.info("Entered email: {}", email);
        } catch (Exception e) {
            logger.error("Failed to enter email: {}", e.getMessage());
            throw new RuntimeException("Failed to enter email", e);
        }
    }

    /**
     * Enter password in password field
     */
    public void enterPassword(String password) {
        try {
            wait.until(ExpectedConditions.visibilityOf(txtPassword));
            txtPassword.clear();
            txtPassword.sendKeys(password);
            logger.info("Entered password");
        } catch (Exception e) {
            logger.error("Failed to enter password: {}", e.getMessage());
            throw new RuntimeException("Failed to enter password", e);
        }
    }

    /**
     * Click Sign In button
     */
    public void clickSignIn() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(btnSignIn));
            click(btnSignIn);
            logger.info("Clicked Sign In button");
        } catch (Exception e) {
            logger.error("Failed to click Sign In button: {}", e.getMessage());
            throw new RuntimeException("Failed to click Sign In button", e);
        }
    }

    /**
     * Get invalid password error message
     */
    public String getInvalidPasswordErrorMessage() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement errorMsg = shortWait.until(
                    ExpectedConditions.visibilityOf(errorMessageInvalidPassword)
            );
            return errorMsg.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean tryLogin(String email, String password) {
        try {
            // Handle initial popups
            click(btnWelcomeClose);
            click(btnExplore);

            // Enter credentials using direct XPATHs
            wait.until(ExpectedConditions.visibilityOf(txtEmail));
            txtEmail.clear();
            txtEmail.sendKeys(email);

            wait.until(ExpectedConditions.visibilityOf(txtPassword));
            txtPassword.clear();
            txtPassword.sendKeys(password);

            handleNetworkError();
            click(btnSignIn);

            // Check if login successful
            return isLoginSuccessful();
        } catch (Exception e) {
            logger.error("Login attempt failed: {}", e.getMessage());
            return false;
        }
    }

    public void logout() {
        try {
            // Wait until profile icon is clickable
            wait.until(ExpectedConditions.elementToBeClickable(profileIcon));

            // Scroll to element (important for top-right icons)
            js.executeScript("arguments[0].scrollIntoView(true);", profileIcon);

            // Small wait for UI stability
            Thread.sleep(500);

            // Click using JS (bypass overlay issue)
            js.executeScript("arguments[0].click();", profileIcon);

            // Wait and click logout
            wait.until(ExpectedConditions.elementToBeClickable(btnLogout));
            click(btnLogout);
            logger.info("Logged out successfully");
        } catch (Exception e) {
            logger.error("Failed to logout: {}", e.getMessage());
        }
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



    public void verifyRejectedUserLogin(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            String errorMsg = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//p[contains(text(),'Login failed') and contains(text(),'profile has been rejected')]")
                    )
            ).getText();

            Assert.assertTrue(errorMsg.contains("profile has been rejected"),
                    "Expected rejection message not displayed");

            System.out.println("TEST PASSED: Rejected user cannot login");

        } catch (Exception e) {
            Assert.fail("TEST FAILED: Rejection message not found, user might have logged in");
        }
    }

    public void verifyResubmitProfileVisible(WebDriver driver) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement resubmitBtn = wait.until(
                    ExpectedConditions.visibilityOf(btnResubmitProfile)
            );

            String btnText = resubmitBtn.getText();

            Assert.assertTrue(btnText.contains("Resubmit"),
                    "Expected 'Resubmit Profile' button not found");

            System.out.println("TEST PASSED: Resubmit Profile button is visible");

        } catch (Exception e) {
            Assert.fail("TEST FAILED: Resubmit Profile button not visible");
        }
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
            logger.info("Login successful, redirected to dashboard");
            return true;
        } catch (Exception e) {
            logger.info("Login validation failed. Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }
}