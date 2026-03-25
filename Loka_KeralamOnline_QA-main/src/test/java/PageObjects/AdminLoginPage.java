package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import Utils.ConfigReader;
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



    public AdminLoginPage(WebDriver driver) {
        super(driver);
    }

            public void loginAsAdmin(String email, String password) throws InterruptedException {

            sendKeys(txtAdminEmail, email);
            sendKeys(txtAdminPassword, password);

            wait.until(ExpectedConditions.elementToBeClickable(btnAdminLogin));
            Thread.sleep(2000);
            btnAdminLogin.click();
            System.out.println("clicked login button");

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
        wait.until(ExpectedConditions.visibilityOfAllElements(userRows));

        if (!userRows.isEmpty()) {
            WebElement firstRow = userRows.get(0);

            WebElement viewBtn = firstRow.findElement(
                    By.xpath(".//td[last()]//button")
            );

            wait.until(ExpectedConditions.elementToBeClickable(viewBtn));
            viewBtn.click();

            logger.info("Clicked View button of first row");
        } else {
            logger.error("No rows found in user table");
            throw new RuntimeException("User table is empty");
        }
    }


}