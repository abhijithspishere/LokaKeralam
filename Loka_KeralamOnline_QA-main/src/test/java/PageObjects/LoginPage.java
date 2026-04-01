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

    // Better selectors for email and password fields - multiple options for reliability
    @FindBy(xpath = "//input[@type='email']")
    private WebElement txtEmailByType;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement txtEmailByName;

    @FindBy(xpath = "//input[@id='email']")
    private WebElement txtEmailById;

    @FindBy(xpath = "//fieldset[.//span[text()='Email']]/preceding-sibling::input")
    private WebElement txtEmail;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement txtPasswordByType;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement txtPasswordByName;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement txtPasswordById;

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

    public LoginPage(WebDriver driver) {
        super(driver);
        logger.info("LoginPage initialized");
    }

    /**
     * Get email field with fallback selectors
     */
    private WebElement getEmailField() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(txtEmailByType));
        } catch (Exception e1) {
            try {
                return wait.until(ExpectedConditions.visibilityOf(txtEmailByName));
            } catch (Exception e2) {
                try {
                    return wait.until(ExpectedConditions.visibilityOf(txtEmailById));
                } catch (Exception e3) {
                    return wait.until(ExpectedConditions.visibilityOf(txtEmail));
                }
            }
        }
    }

    /**
     * Get password field with fallback selectors
     */
    private WebElement getPasswordField() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(txtPasswordByType));
        } catch (Exception e1) {
            try {
                return wait.until(ExpectedConditions.visibilityOf(txtPasswordByName));
            } catch (Exception e2) {
                try {
                    return wait.until(ExpectedConditions.visibilityOf(txtPasswordById));
                } catch (Exception e3) {
                    return wait.until(ExpectedConditions.visibilityOf(txtPassword));
                }
            }
        }
    }

    public void loginWithCredentials(String email, String password) {
        // Handle initial welcome popups
        click(btnWelcomeClose);
        click(btnExplore);

        // Enter Credentials using robust methods
        WebElement emailField = getEmailField();
        emailField.clear();
        emailField.sendKeys(email);

        WebElement passwordField = getPasswordField();
        passwordField.clear();
        passwordField.sendKeys(password);

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
            WebElement emailField = getEmailField();
            emailField.clear();
            emailField.sendKeys(email);
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
            WebElement passwordField = getPasswordField();
            passwordField.clear();
            passwordField.sendKeys(password);
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

            // Enter credentials using robust methods
            WebElement emailField = getEmailField();
            emailField.clear();
            emailField.sendKeys(email);

            WebElement passwordField = getPasswordField();
            passwordField.clear();
            passwordField.sendKeys(password);

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

        WebElement emailField = getEmailField();
        emailField.clear();
        emailField.sendKeys(email);

        WebElement passwordField = getPasswordField();
        passwordField.clear();
        passwordField.sendKeys("IncorrectPassword123!");

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
            logger.info("Login successful, redirected to dashboard");
            return true;
        } catch (Exception e) {
            logger.info("Login validation failed. Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }
}