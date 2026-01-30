package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

public class ProfileCompletionPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(ProfileCompletionPage.class);

    @FindBy(xpath = "//button[normalize-space()='Close']")
    private WebElement btnWelcomeClose;

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

    @FindBy(xpath = "//button[normalize-space()='Complete Profile']")
    private WebElement btnCompleteProfile;

    @FindBy(xpath = "//*[local-name()='svg' and @data-testid='EditIcon']")
    private WebElement editIcon;

    @FindBy(xpath = "//button[normalize-space()='Capture']")
    private WebElement btnCapture;

    @FindBy(xpath = "//button[normalize-space()='Save']")
    private WebElement btnSave;

    @FindBy(xpath="//textarea[@name='aboutMe']")
    private WebElement txtAboutMe;

    @FindBy(xpath = "//input[@name='salutation']/preceding-sibling::div[@role='combobox']")
    private WebElement dropDwnSalutation;

    @FindBy(xpath = "//li[normalize-space()='Mr.']")
    private WebElement dropDwnSelection;

    @FindBy(xpath = "(//button[normalize-space()='Update'])[1]")
    private WebElement btnUpdate01;

    @FindBy(xpath = "//textarea[@name='addressLine1']")
    private WebElement txtAddressLine1;

    @FindBy(xpath = "(//button[normalize-space()='Update'])[2]")
    private WebElement btnUpdate02;

    @FindBy(xpath = "//input[@name='pincode']")
    private WebElement txtPincode;

    @FindBy(xpath = "//li[normalize-space()='Kaudiar Square SO']")
    private WebElement dropDwnKaudiarSquare;

    @FindBy(xpath = "//input[@name='houseNo']")
    private WebElement dropDwnHouseNo;

    @FindBy(name = "district")
    private WebElement districtInput;

    @FindBy(xpath = "(//button[normalize-space()='Update'])[3]")
    private WebElement btnUpdate03;

    @FindBy(xpath = "//input[@name='occupation']/preceding-sibling::div[@role='combobox']")
    private WebElement dropDwnJobSegment;

    @FindBy(xpath = "//li[normalize-space()='IT/ITES Professionals']")
    private WebElement dropDwnITProfessionals;

    @FindBy(xpath = "//input[@id='professionalInfo-input']")
    WebElement txtCompanyName;

    /*@FindBy(xpath = "//input[@name='companyAddress']")
    WebElement txtCompanyAddress;*/

    @FindBy(xpath = "(//button[normalize-space()='Update'])[4]")
    private WebElement btnUpdate04;

    @FindBy(xpath = "//input[@name='passportNo']")
    private WebElement txtPassportNo;

    @FindBy(xpath = "//span[text()='Passport Expiry Date']/ancestor::div[contains(@class, 'MuiInputBase-root')]//input")
    private WebElement txtPassportExpiryDate;

    @FindBy(xpath="//div[@aria-label='Choose Saturday, February 7th, 2026']")
    private WebElement datePickerFeb07_2026;

    @FindBy(xpath = "//label[@for='passport-file']")
    private WebElement uploadPassportDoc;

    @FindBy(xpath = "(//button[normalize-space()='Update'])[5]")
    private WebElement btnUpdate05;

    @FindBy(xpath = "//input[@name='residenceStatus']/preceding-sibling::div[@role='combobox']")
    private WebElement dropDwnResidenceStatus;

    @FindBy(xpath = "//li[normalize-space()='NRI']")
    private WebElement dropDwnNRI;

    @FindBy(xpath = "//input[@name='docType']/preceding-sibling::div[@role='combobox']")
    private WebElement dropDwnDocumentType;

    @FindBy(xpath = "//li[normalize-space()='Employee Id Card']")
    private WebElement dropDwnEmployeeIDCard;

    @FindBy(xpath = "//input[@name='documentNo']")
    private WebElement txtDocumentNo;

    @FindBy(xpath = "//span[text()='Date of Issue']/ancestor::div[contains(@class, 'MuiInputBase-root')]//input")
    private WebElement txtDateOfIssue;

    @FindBy(xpath = "//div[@aria-label='Choose Thursday, January 1st, 2026']")
    private WebElement datePickerJan01_2026;

    @FindBy(xpath = "//label[@for='proof-of-residence-file']")
    private WebElement uploadProofOfResidence;

    @FindBy(xpath = "(//button[normalize-space()='Update'])[6]")
    private WebElement btnUpdate06;

    @FindBy(xpath = "//input[@name='facebookUrl']")
    private  WebElement txtFacebookUrl;

    @FindBy(xpath="//input[@name='linkedinUrl']")
    private WebElement txtLinkedinUrl;

    @FindBy(xpath = "//input[@name='instagramUrl']")
    private WebElement txtInstagramUrl;

    @FindBy(xpath = "(//button[normalize-space()='Update'])[7]")
    private WebElement btnUpdate07;









    public ProfileCompletionPage(WebDriver driver) {
        super(driver);
        logger.info("ProfileCompletionPage initialized");
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
}
