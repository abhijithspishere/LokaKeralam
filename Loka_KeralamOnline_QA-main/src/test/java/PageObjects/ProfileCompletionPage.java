package PageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;
import Utils.FileConstants;

public class ProfileCompletionPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(ProfileCompletionPage.class);

    // --- Locators (Keep your existing Locators) ---
    @FindBy(xpath = "//button[normalize-space()='Close']") private WebElement btnWelcomeClose;
    @FindBy(xpath = "//button[normalize-space()='CLOSE']") private WebElement btnErrorClose;
    @FindBy(xpath = "//button[normalize-space()='Click to Explore']") private WebElement btnExplore;
    @FindBy(xpath = "//fieldset[.//span[text()='Email']]/preceding-sibling::input") private WebElement txtEmail;
    @FindBy(xpath = "//fieldset[.//span[text()='Password']]/preceding-sibling::input") private WebElement txtPassword;
    @FindBy(xpath = "//button[normalize-space()='Sign In']") private WebElement btnSignIn;

    @FindBy(xpath = "//button[@type='button' and contains(., 'Complete Profile')]") private WebElement btnCompleteProfile;
    @FindBy(xpath = "//*[local-name()='svg' and @data-testid='EditIcon']") private WebElement editIcon;
    @FindBy(xpath = "//button[normalize-space()='Capture']") private WebElement btnCapture;
    @FindBy(xpath = "//button[normalize-space()='Save']") private WebElement btnSave;

    // Basic Info
    @FindBy(xpath = "//textarea[@name='aboutMe']") private WebElement txtAboutMe;
    @FindBy(xpath = "//input[@name='salutation']/preceding-sibling::div[@role='combobox']") private WebElement dropDwnSalutation;
    @FindBy(xpath = "//li[normalize-space()='Mr.']") private WebElement dropDwnSelection;
    @FindBy(xpath = "(//button[normalize-space()='Update'])[1]") private WebElement btnUpdate01;

    // NRK Address
    @FindBy(xpath = "//textarea[@name='addressLine1']") private WebElement txtAddressLine1;
    @FindBy(xpath = "(//button[normalize-space()='Update'])[2]") private WebElement btnUpdate02;

    // Kerala Address
    @FindBy(xpath = "//input[@name='pincode']") private WebElement txtPincode;
    @FindBy(xpath = "//input[@name='postOffice']/preceding-sibling::div[@role='combobox']") private WebElement dropDwnPostOffice;
    @FindBy(xpath = "//li[normalize-space()='Kaudiar Square SO']") private WebElement dropDwnKaudiarSquare;
    @FindBy(xpath = "//input[@name='houseNo']") private WebElement dropDwnHouseNo;
    @FindBy(name = "district") private WebElement districtInput;
    @FindBy(xpath = "(//button[normalize-space()='Update'])[3]") private WebElement btnUpdate03;

    // Professional Info
    @FindBy(xpath = "//input[@name='occupation']/preceding-sibling::div[@role='combobox']") private WebElement dropDwnJobSegment;
    @FindBy(xpath = "//li[normalize-space()='IT/ITES Professionals']") private WebElement dropDwnITProfessionals;
    @FindBy(xpath = "//input[@id='professionalInfo-input']") private WebElement txtCompanyName;
    @FindBy(xpath = "//li[@role='option' and normalize-space()='Appa Kadai Restaurant - 16th Street - Dubai - United Arab Emirates']") private WebElement selectCompanyName;
    @FindBy(xpath = "(//button[normalize-space()='Update'])[4]") private WebElement btnUpdate04;

    // Passport
    @FindBy(xpath = "//input[@name='passportNo']") private WebElement txtPassportNo;
    @FindBy(xpath = "//span[text()='Passport Expiry Date']/ancestor::div[contains(@class, 'MuiInputBase-root')]//input") private WebElement txtPassportExpiryDate;
    @FindBy(xpath = "//div[@aria-label='Choose Saturday, February 7th, 2026']") private WebElement datePickerFeb07_2026;
    @FindBy(xpath = "//input[@id='passport-file' and @type='file']") private WebElement passportFileInput;
    @FindBy(xpath = "(//button[normalize-space()='Update'])[5]") private WebElement btnUpdate05;

    // Residence
    @FindBy(xpath = "//input[@name='residenceStatus']/preceding-sibling::div[@role='combobox']") private WebElement dropDwnResidenceStatus;
    @FindBy(xpath = "//li[normalize-space()='NRI']") private WebElement dropDwnNRI;
    @FindBy(xpath = "//input[@name='docType']/preceding-sibling::div[@role='combobox']") private WebElement dropDwnDocumentType;
    @FindBy(xpath = "//li[normalize-space()='Employee Id Card']") private WebElement dropDwnEmployeeIDCard;
    @FindBy(xpath = "//input[@name='documentNo']") private WebElement txtDocumentNo;
    @FindBy(xpath = "//span[text()='Date of Issue']/ancestor::div[contains(@class, 'MuiInputBase-root')]//input") private WebElement txtDateOfIssue;
    @FindBy(xpath = "//div[@aria-label='Choose Sunday, February 1st, 2026']") private WebElement datePickerJan01_2026;
    @FindBy(xpath = "//input[@id='proof-of-residence-file' and @type='file']") private WebElement residenceFileInput;
    @FindBy(xpath = "(//button[normalize-space()='Update'])[6]") private WebElement btnUpdate06;

    // Social Links
    @FindBy(xpath = "//input[@name='facebookUrl']") private WebElement txtFacebookUrl;
    @FindBy(xpath = "//input[@name='linkedinUrl']") private WebElement txtLinkedinUrl;
    @FindBy(xpath = "//input[@name='instagramUrl']") private WebElement txtInstagramUrl;
    @FindBy(xpath = "(//button[normalize-space()='Update'])[7]") private WebElement btnUpdate07;
    @FindBy(xpath = "//button[normalize-space()='Do It Later']") private WebElement btnDoItLater;
    @FindBy(xpath = "//button[normalize-space()='Preview & Submit for Verification & Approval']")private WebElement btnSubmitVerification;
    @FindBy(xpath = "//button[contains(normalize-space(.),'Submit') and contains(normalize-space(.),'Approval')]") private  WebElement btnSubmitApproval;
    @FindBy(xpath = "//span[normalize-space()='I agree']/ancestor::label//input[@type='checkbox']") private WebElement chkAgreeTerms;
    @FindBy(xpath = "//button[normalize-space()='Proceed']") private WebElement btnSubmitProceeding;
    @FindBy(xpath = "(//button[normalize-space()='Close'])[2]") private WebElement btnSubmissionClose;

    public ProfileCompletionPage(WebDriver driver) {
        super(driver);
        logger.info("ProfileCompletionPage initialized");
    }

    // --- CRITICAL FIX: Wait for Page Reload ---
    private void waitForPageReload() {
        try {
            Thread.sleep(2000);
            wait.until(ExpectedConditions.visibilityOf(editIcon));
            logger.info("Page reload completed.");
        } catch (Exception e) {
            logger.warn("Page reload wait timed out or failed.");
        }
    }

    private void scrollToCenter(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    public void loginWithCredentials(String email, String password) {
        try { click(btnWelcomeClose); } catch (Exception e) {}
        try { click(btnExplore); } catch (Exception e) {}

        wait.until(ExpectedConditions.visibilityOf(txtEmail));
        txtEmail.clear();
        txtEmail.sendKeys(email);

        wait.until(ExpectedConditions.visibilityOf(txtPassword));
        txtPassword.clear();
        txtPassword.sendKeys(password);

        wait.until(ExpectedConditions.elementToBeClickable(btnSignIn));
        click(btnSignIn);
        handleNetworkError();
    }

    public void navigateToProfile() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(btnCompleteProfile));
        click(btnCompleteProfile);
        Thread.sleep(1000);
        handleLocationChange();
    }

    public void captureImage() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(editIcon));
        scrollToCenter(editIcon);
        try {
            editIcon.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editIcon);
        }
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOf(btnCapture));
        wait.until(ExpectedConditions.elementToBeClickable(btnCapture));
        click(btnCapture);

        wait.until(ExpectedConditions.visibilityOf(btnSave));
        wait.until(ExpectedConditions.elementToBeClickable(btnSave));
        click(btnSave);

         // Added Reload Wait
    }

    public void updateBasicInfo(String aboutMe) {
        waitForPageReload();
        wait.until(ExpectedConditions.visibilityOf(txtAboutMe));
        scrollToCenter(txtAboutMe);
        txtAboutMe.clear();
        sendKeys(txtAboutMe, aboutMe);

        wait.until(ExpectedConditions.elementToBeClickable(dropDwnSalutation));
        click(dropDwnSalutation);

        wait.until(ExpectedConditions.elementToBeClickable(dropDwnSelection));
        click(dropDwnSelection);

        wait.until(ExpectedConditions.elementToBeClickable(btnUpdate01));
        click(btnUpdate01);

    }

    public void updateNRKAddress(String address) {
        waitForPageReload();
        wait.until(ExpectedConditions.visibilityOf(txtAddressLine1));
        scrollToCenter(txtAddressLine1);
        sendKeys(txtAddressLine1, address);

        wait.until(ExpectedConditions.elementToBeClickable(btnUpdate02));
        click(btnUpdate02);

        //waitForPageReload(); // Added Reload Wait
    }

    public void updateKeralaAddress(String pincode, String houseNo, String district) {
        waitForPageReload();
        wait.until(ExpectedConditions.visibilityOf(txtPincode));
        click(txtPincode);
        scrollToCenter(txtPincode);
        sendKeys(txtPincode, pincode);
        click(dropDwnPostOffice);

        try {
            wait.until(ExpectedConditions.visibilityOf(dropDwnKaudiarSquare));
            wait.until(ExpectedConditions.elementToBeClickable(dropDwnKaudiarSquare));
            click(dropDwnKaudiarSquare);
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropDwnKaudiarSquare);
        }

        wait.until(ExpectedConditions.visibilityOf(dropDwnHouseNo));
        sendKeys(dropDwnHouseNo, houseNo);

        wait.until(ExpectedConditions.visibilityOf(districtInput));
        sendKeys(districtInput, district);

        wait.until(ExpectedConditions.elementToBeClickable(btnUpdate03));
        click(btnUpdate03);

        //waitForPageReload(); // Added Reload Wait
    }

    public void updateProfessionalInfo(String companyName) throws InterruptedException {
        waitForPageReload();
        wait.until(ExpectedConditions.elementToBeClickable(dropDwnJobSegment));
        scrollToCenter(dropDwnJobSegment);
        click(dropDwnJobSegment);

        wait.until(ExpectedConditions.elementToBeClickable(dropDwnITProfessionals));
        click(dropDwnITProfessionals);

        wait.until(ExpectedConditions.visibilityOf(txtCompanyName));
        sendKeys(txtCompanyName, companyName);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(selectCompanyName));
            click(selectCompanyName);
        } catch (Exception e) {
            //newchange
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectCompanyName);
        }
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(btnUpdate04));
        click(btnUpdate04);
        Thread.sleep(1000);
        waitForPageReload(); // Added Reload Wait
    }

    public void updatePassportDetails(String passportNo, String passportPath) {

        //waitForPageReload();
        wait.until(ExpectedConditions.visibilityOf(txtPassportNo));
        scrollToCenter(txtPassportNo);
        txtPassportNo.clear();
        txtPassportNo.sendKeys(passportNo);

        wait.until(ExpectedConditions.elementToBeClickable(txtPassportExpiryDate));
        click(txtPassportExpiryDate);

        wait.until(ExpectedConditions.elementToBeClickable(datePickerFeb07_2026));
        click(datePickerFeb07_2026);

        uploadFile(passportFileInput, passportPath);

        wait.until(ExpectedConditions.elementToBeClickable(btnUpdate05));
        click(btnUpdate05);

        //waitForPageReload();
    }


    public void updateResidenceProof(String documentNo, String residencePath) {

        waitForPageReload();
        wait.until(ExpectedConditions.elementToBeClickable(dropDwnResidenceStatus));
        scrollToCenter(dropDwnResidenceStatus);
        click(dropDwnResidenceStatus);

        wait.until(ExpectedConditions.elementToBeClickable(dropDwnNRI));
        click(dropDwnNRI);

        wait.until(ExpectedConditions.elementToBeClickable(dropDwnDocumentType));
        click(dropDwnDocumentType);

        wait.until(ExpectedConditions.elementToBeClickable(dropDwnEmployeeIDCard));
        click(dropDwnEmployeeIDCard);

        wait.until(ExpectedConditions.visibilityOf(txtDocumentNo));
        txtDocumentNo.clear();
        txtDocumentNo.sendKeys(documentNo);

        wait.until(ExpectedConditions.elementToBeClickable(txtDateOfIssue));
        click(txtDateOfIssue);

        wait.until(ExpectedConditions.elementToBeClickable(datePickerJan01_2026));
        click(datePickerJan01_2026);

        uploadFile(residenceFileInput, residencePath);

        wait.until(ExpectedConditions.elementToBeClickable(btnUpdate06));
        click(btnUpdate06);

        //waitForPageReload();
    }


    public void updateSocialLinks(String facebookUrl, String linkedinUrl, String instagramUrl) throws InterruptedException {

        waitForPageReload();
        wait.until(ExpectedConditions.visibilityOf(txtFacebookUrl));
        scrollToCenter(txtFacebookUrl);
        sendKeys(txtFacebookUrl, facebookUrl);

        wait.until(ExpectedConditions.visibilityOf(txtLinkedinUrl));
        sendKeys(txtLinkedinUrl, linkedinUrl);

        wait.until(ExpectedConditions.visibilityOf(txtInstagramUrl));
        sendKeys(txtInstagramUrl, instagramUrl);

        wait.until(ExpectedConditions.elementToBeClickable(btnUpdate07));
        click(btnUpdate07);

        waitForPageReload();

        wait.until(ExpectedConditions.elementToBeClickable(btnSubmitVerification));
        click(btnSubmitVerification);

        Thread.sleep(1000);

        wait.until(ExpectedConditions.elementToBeClickable(btnSubmitApproval));
        click(btnSubmitApproval);

        Thread.sleep(1000);


        wait.until(ExpectedConditions.elementToBeClickable(chkAgreeTerms));
        click(chkAgreeTerms);
        System.out.println("clicked on agree terms checkbox");

        wait.until(ExpectedConditions.elementToBeClickable(btnSubmitProceeding));
        click(btnSubmitProceeding);

        wait.until(ExpectedConditions.elementToBeClickable(btnSubmissionClose));
        click(btnSubmissionClose);
    }

    public boolean isProfileCompletionSuccessful() {
        try {
            wait.until(ExpectedConditions.urlContains("/my-profile"));
            return true;
        } catch (Exception e) {
            logger.error("Profile completion failed. Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }


    //////////////////////////////////////////////////////////////////////////////////
    // Error Handling Methods ///////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    public void handleNetworkError() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement closeBtn = shortWait.until(ExpectedConditions.visibilityOf(btnErrorClose));
            if (closeBtn.isDisplayed()) {
                closeBtn.click();
                click(btnSignIn);
            }
        } catch (Exception e) {}
    }

    public void handleLocationChange() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement doItLaterBtn = shortWait.until(ExpectedConditions.visibilityOf(btnDoItLater));
            if (doItLaterBtn.isDisplayed()) {
                doItLaterBtn.click();
                click(btnCompleteProfile);
            }
        } catch (Exception e) {}
    }

    private void uploadFile(WebElement fileInput, String filePath) {
        try {
            // Wait until file input is present in DOM
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath(".//input[@type='file']")
            ));

            // Make hidden input visible (important for MUI)
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].style.display='block';", fileInput);
            // Upload file
            fileInput.sendKeys(filePath);
            logger.info("File uploaded successfully: " + filePath);

        } catch (Exception e) {
            logger.error("File upload failed: " + filePath, e);
            throw e;
        }
    }

}