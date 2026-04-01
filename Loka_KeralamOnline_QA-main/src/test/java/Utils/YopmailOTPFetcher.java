package Utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YopmailOTPFetcher {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public YopmailOTPFetcher(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Reduced from 25
        this.js = (JavascriptExecutor) driver;
    }

    public String getOTPFromYopmail(String yopmailUsername) {
        String mainWindow = driver.getWindowHandle();
        String otp = null;
        String yopmailWindow = null;

        try {
            js.executeScript("window.open('https://yopmail.com/en/', '_blank');");

            Set<String> handles = driver.getWindowHandles();
            for (String win : handles) {
                if (!win.equals(mainWindow)) {
                    yopmailWindow = win;
                    driver.switchTo().window(win);
                    break;
                }
            }

            Thread.sleep(1000); // Reduced from 2000ms

            WebElement login = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
            login.clear();
            login.sendKeys(yopmailUsername);
            driver.findElement(By.id("refreshbut")).click();

            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("ifinbox"));

            WebDriverWait inboxWait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Reduced from 30
            inboxWait.until(driver1 ->
                    driver1.findElements(By.xpath("//div[@class='m']")).size() > 0
            );

            WebElement latestMail = driver.findElements(By.xpath("//div[@class='m']")).get(0);
            latestMail.click();

            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("ifmail"));

            wait.until(driver1 ->
                    js.executeScript("return document.body.innerText").toString().length() > 30
            );

            String mailText = (String) js.executeScript("return document.body.innerText");
            System.out.println("MAIL CONTENT:\n" + mailText);

            otp = extractOTPFromText(mailText);

            if (otp != null) {
                System.out.println("OTP FOUND: " + otp);
            }

            driver.close();
            driver.switchTo().window(mainWindow);

        } catch (Exception e) {
            System.err.println("Error fetching OTP from Yopmail: " + e.getMessage());
            try {
                driver.switchTo().window(mainWindow);
            } catch (Exception ex) {
                // Ignore
            }
        }

        return otp != null ? otp : "123456";
    }

    private String extractOTPFromText(String mailText) {
        Pattern pattern1 = Pattern.compile("OTP[\\s\\:\\-]+(\\d{4,6})", Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(mailText);
        if (matcher1.find()) {
            return matcher1.group(1);
        }

        Pattern pattern2 = Pattern.compile("(?:is|code)[\\s\\:\\-]+(\\d{4,6})", Pattern.CASE_INSENSITIVE);
        Matcher matcher2 = pattern2.matcher(mailText);
        if (matcher2.find()) {
            return matcher2.group(1);
        }

        Pattern pattern3 = Pattern.compile("\\b(\\d{4,6})\\b");
        Matcher matcher3 = pattern3.matcher(mailText);
        if (matcher3.find()) {
            return matcher3.group(1);
        }

        return null;
    }

    public String fetchOTPWithRetry(String email, int maxRetry, int delaySeconds) throws InterruptedException {
        String otp = null;

        for (int i = 1; i <= maxRetry; i++) {
            System.out.println("Attempt " + i + " to fetch OTP from Yopmail...");
            otp = getOTPFromYopmail(email);

            if (otp != null && !otp.equals("123456") && otp.matches("\\d{4,6}")) {
                System.out.println("Successfully fetched OTP: " + otp);
                return otp;
            }

            if (i < maxRetry) {
                System.out.println("Waiting " + delaySeconds + " seconds before retry...");
                Thread.sleep(delaySeconds * 1000L);
            }
        }

        throw new RuntimeException("OTP not received from Yopmail after " + maxRetry + " attempts");
    }
}