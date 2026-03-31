package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class AdminLoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(AdminLoginPage.class);

    @FindBy(xpath = "//input[@type='text' and ancestor::div[.//span[text()='Email']]]")
    private WebElement txtAdminEmail;

    @FindBy(xpath ="//span[text()='Password']/ancestor::div[contains(@class,'MuiOutlinedInput-root')]//input")
    private WebElement txtAdminPassword;

    @FindBy(xpath = "//button[contains(text(),'Log In')]")
    private WebElement btnAdminLogin;

    @FindBy(xpath = "//span[normalize-space()='View User List']")
    private WebElement btnViewUserList;

    @FindBy(xpath = "//table//tbody/tr")
    private List<WebElement> userRows;

    @FindBy(xpath = "//button[normalize-space()='Approve']")
    private WebElement btnApprove;

    @FindBy(xpath = "//p[contains(text(),'LKO ID')]")
    private WebElement lkoIdText;

    @FindBy(xpath = "//button[normalize-space()='Resubmit']")
    private WebElement btnResubmit;

    @FindBy(xpath = "//textarea[@id='modal-description']")
    private WebElement txtRemarks;

    @FindBy(xpath="(//button[normalize-space()='Resubmit'])[2]")
    private WebElement btnResubmit2;

    @FindBy(xpath = "//button[normalize-space()='Reject']")
    private WebElement btnReject;

    @FindBy(xpath = "(//button[normalize-space()='Reject'])[2]")
    private WebElement btnReject2;

    public AdminLoginPage(WebDriver driver) {
        super(driver);
    }
    public void loginAsAdmin(String email, String password) throws InterruptedException {

    sendKeys(txtAdminEmail, email);
    sendKeys(txtAdminPassword, password);

    wait.until(ExpectedConditions.elementToBeClickable(btnAdminLogin));

    int attempts = 0;
    Thread .sleep(2000); // Brief pause to allow any potential UI changes
    while (attempts < 2) {
        try {
            btnAdminLogin.click();
            logger.info("Clicked login button");
            break;
        } catch (Exception e) {
            logger.warn("Normal click failed, retrying with JS click...");
            try {
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", btnAdminLogin);
                break;
            } catch (Exception ex) {
                attempts++;
            }
        }
    }

    wait.until(ExpectedConditions.or(
            ExpectedConditions.urlContains("ApproverDashboard"),
            ExpectedConditions.urlContains("admin")
    ));

    logger.info("Login successful, navigated to: " + driver.getCurrentUrl());
}
    public boolean isDashboardDisplayed() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("ApproverDashboard"),
                    ExpectedConditions.urlContains("admin")
            ));
            return true;
        } catch (Exception e) {
            logger.error("Dashboard not loaded. Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }

    public void clickFirstRowViewButton() {
        btnViewUserList.click();

        By firstRowViewBtn = By.xpath("(//table//tbody/tr)[1]//td[last()]//button");

        wait.until(ExpectedConditions.elementToBeClickable(firstRowViewBtn));
        driver.findElement(firstRowViewBtn).click();

        logger.info("Clicked View button of first row");
    }
    public void aprroveUser() {
        wait.until(ExpectedConditions.elementToBeClickable(btnApprove));
        btnApprove.click();
        logger.info("Clicked Approve button");
    }

    public void ResubmitUser() {
        wait.until(ExpectedConditions.elementToBeClickable(btnResubmit));
        btnResubmit.click();
        logger.info("Clicked initial Resubmit button");
        wait.until(ExpectedConditions.visibilityOf(txtRemarks));
        txtRemarks.clear();
        txtRemarks.sendKeys("Please add valid documents");
        logger.info("Entered remarks for resubmission");
        wait.until(ExpectedConditions.elementToBeClickable(btnResubmit2));
        btnResubmit2.click();
        logger.info("Clicked final Resubmit button");
    }

    public void RejectUser() {
        wait.until(ExpectedConditions.elementToBeClickable(btnReject));
        btnReject.click();
        logger.info("Clicked Reject button");
        wait.until(ExpectedConditions.visibilityOf(txtRemarks));
        txtRemarks.clear();
        txtRemarks.sendKeys("Please add valid documents");
        logger.info("Entered remarks for resubmission");
        wait.until(ExpectedConditions.elementToBeClickable(btnReject2));
        btnReject2.click();
        logger.info("Clicked final Resubmit button");
    }

    public String fetchLKO_ID() {
        wait.until(ExpectedConditions.visibilityOf(lkoIdText));
        String LKO_ID = getText(lkoIdText).replace("LKO ID: ", "").trim();
        logger.info("Fetched LKO ID: " + LKO_ID);
        return LKO_ID;
    }

}