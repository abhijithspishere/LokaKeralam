package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import Utils.YopmailOTPFetcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ForgotPasswordPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(ForgotPasswordPage.class);

    // Step 1: Email input page
    @FindBy(xpath = "//input[@id='email']")
    private WebElement txtEmailInput;

    @FindBy(xpath = "//button[normalize-space()='Send Email OTP']")
    private WebElement btnSendEmailOtp;

    // OTP input fields
    @FindBy(xpath = "//input[@aria-label='Please enter verification code. Digit 1']")
    private WebElement otp1stDigit;

    @FindBy(xpath = "//input[@aria-label='Digit 2']")
    private WebElement otp2Digit;

    @FindBy(xpath = "//input[@aria-label='Digit 3']")
    private WebElement otp3Digit;

    @FindBy(xpath = "//input[@aria-label='Digit 4']")
    private WebElement otp4Digit;

    @FindBy(xpath = "//input[@aria-label='Digit 5']")
    private WebElement otp5Digit;

    @FindBy(xpath = "//input[@aria-label='Digit 6']")
    private WebElement otp6Digit;

    @FindBy(xpath = "//button[normalize-space()='Validate OTP']")
    private WebElement btnValidateOtp;

    // New password page
    @FindBy(xpath = "//input[@id='password']")
    private WebElement txtNewPassword;

    @FindBy(xpath = "//input[@id='confirm-password']")
    private WebElement txtConfirmPassword;

    @FindBy(xpath = "//button[normalize-space()='Reset Password']")
    private WebElement btnResetPassword;

    private final YopmailOTPFetcher otpFetcher;

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
        this.otpFetcher = new YopmailOTPFetcher(driver);
        logger.info("ForgotPasswordPage initialized");
    }

    /**
     * Get email input field
     */
    private WebElement getEmailInput() {
        return wait.until(ExpectedConditions.visibilityOf(txtEmailInput));
    }

    /**
     * Request OTP for forgot password
     */
    public void requestPasswordResetOTP(String email) {
        logger.info("Requesting password reset OTP for email: {}", email);

        try {
            WebElement emailInput = getEmailInput();
            emailInput.clear();
            emailInput.sendKeys(email);

            Thread.sleep(500); // Reduced from 1000ms

            wait.until(ExpectedConditions.elementToBeClickable(btnSendEmailOtp));
            click(btnSendEmailOtp);

            logger.info("OTP request sent successfully");
        } catch (Exception e) {
            logger.error("Failed to request OTP: {}", e.getMessage());
            throw new RuntimeException("Failed to request password reset OTP", e);
        }
    }

    /**
     * Enter OTP into the 6-digit fields
     */
    public void enterOTP(String otp) {
        if (otp == null || otp.isEmpty()) {
            throw new RuntimeException("OTP is null or empty");
        }

        logger.info("Entering OTP");

        char[] digits = otp.toCharArray();
        WebElement[] otpFields = {otp1stDigit, otp2Digit, otp3Digit, otp4Digit, otp5Digit, otp6Digit};

        for (int i = 0; i < Math.min(digits.length, otpFields.length); i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(otpFields[i]));
                otpFields[i].clear();
                otpFields[i].sendKeys(String.valueOf(digits[i]));
                Thread.sleep(50); // Reduced from 100ms
                logger.debug("Entered OTP digit {}", i + 1);
            } catch (Exception e) {
                logger.error("Failed to enter OTP digit {}: {}", i + 1, e.getMessage());
                throw new RuntimeException("Failed to enter OTP", e);
            }
        }
    }

    /**
     * Click Validate OTP button
     */
    public void clickValidateOTP() {
        logger.info("Clicking Validate OTP button");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(btnValidateOtp));
            click(btnValidateOtp);
        } catch (Exception e) {
            logger.error("Validate OTP button not clickable: {}", e.getMessage());
            throw new RuntimeException("Failed to click Validate OTP button", e);
        }
    }

    /**
     * Wait for password reset form
     */
    public boolean waitForPasswordResetForm(int timeoutSeconds) {
        logger.info("Waiting for password reset form");
        try {
            wait.until(ExpectedConditions.visibilityOf(txtNewPassword));
            logger.info("Password reset form is visible");
            return true;
        } catch (Exception e) {
            logger.error("Password reset form not visible: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Set new password
     */
    public void setNewPassword(String newPassword) {
        logger.info("Setting new password");

        try {
            wait.until(ExpectedConditions.visibilityOf(txtNewPassword));
            txtNewPassword.clear();
            txtNewPassword.sendKeys(newPassword);

            txtConfirmPassword.clear();
            txtConfirmPassword.sendKeys(newPassword);

            logger.info("New password entered successfully");
        } catch (Exception e) {
            logger.error("Failed to set new password: {}", e.getMessage());
            throw new RuntimeException("Failed to set new password", e);
        }
    }

    /**
     * Click Reset Password button
     */
    public void clickResetPassword() {
        logger.info("Clicking Reset Password button");

        try {
            wait.until(ExpectedConditions.elementToBeClickable(btnResetPassword));
            click(btnResetPassword);
            logger.info("Reset Password button clicked");
        } catch (Exception e) {
            logger.error("Failed to click Reset Password button: {}", e.getMessage());
            throw new RuntimeException("Failed to click Reset Password button", e);
        }
    }

    /**
     * Verify password reset success
     */
    public boolean isPasswordResetSuccessful() {
        try {
            logger.info("Verifying password reset success");
            Thread.sleep(2000); // Reduced from 5000ms

            String currentUrl = driver.getCurrentUrl();
            if (currentUrl.contains("login-page") || currentUrl.contains("login")) {
                logger.info("Redirected to login page - password reset successful");
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Error verifying password reset success: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Complete forgot password flow - OPTIMIZED
     */
    public void completeForgotPasswordFlow(String yopmailUsername, String email, String newPassword) throws InterruptedException {
        logger.info("Starting forgot password flow for email: {}", email);

        requestPasswordResetOTP(email);

        logger.info("Waiting for OTP email to arrive...");
        Thread.sleep(2000);


        String otp = otpFetcher.fetchOTPWithRetry(yopmailUsername, 3, 3);
        logger.info("OTP fetched successfully: {}", otp);

        enterOTP(otp);
        clickValidateOTP();

        logger.info("Waiting for password reset form...");
        Thread.sleep(2000);

        if (!waitForPasswordResetForm(5)) {
            throw new RuntimeException("Password reset form did not appear after OTP validation");
        }
        setNewPassword(newPassword);

        Thread.sleep(500);
        clickResetPassword();

        logger.info("Forgot password flow completed");
    }
}