package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    private final By usernameField = By.cssSelector("#user-name");
    private final By passwordField = By.cssSelector("#password");
    private final By loginButton = By.cssSelector("#login-button");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        navigateToLoginPage();
        waitForPageToLoad();
    }

    private void navigateToLoginPage() {
        // Use a longer page load timeout to be more tolerant of slow network
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));

        int attempts = 0;
        while (attempts < 3) {
            try {
                driver.navigate().to("https://www.saucedemo.com/");
                logger.info("Opened SauceDemo login page");

                // If Firefox hit a network error page, retry navigation
                String currentUrl = driver.getCurrentUrl();
                if (currentUrl != null && currentUrl.startsWith("about:neterror")) {
                    throw new org.openqa.selenium.WebDriverException("Network error page encountered");
                }
                break; // success
            } catch (org.openqa.selenium.TimeoutException e) {
                logger.warn("Page load timeout occurred: {}", e.getMessage());
                logger.info("Attempting to continue despite page load timeout");
                break; // allow waitForPageToLoad to continue
            } catch (Exception e) {
                attempts++;
                if (attempts >= 3) {
                    logger.error("Failed to navigate to login page after {} attempts: {}", attempts, e.getMessage());
                    throw e;
                }
                logger.warn("Navigation attempt {} failed: {}. Retrying...", attempts, e.getMessage());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
                // Try a refresh before next attempt
                try {
                    driver.navigate().refresh();
                } catch (Exception refreshError) {
                    logger.warn("Refresh failed before retry: {}", refreshError.getMessage());
                }
            }
        }
    }

    private void waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            // Wait for page to be ready with multiple checks
            wait.until(driver -> ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
            logger.info("Login page loaded successfully");
        } catch (Exception e) {
            logger.warn("Page load wait timed out after 30 seconds, attempting to continue anyway: {}", e.getMessage());
            // Try one more time with a shorter wait
            try {
                Thread.sleep(2000); // Brief pause
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                shortWait.until(ExpectedConditions.presenceOfElementLocated(usernameField));
                logger.info("Login page elements found after retry");
            } catch (Exception retryException) {
                logger.error("Failed to find login page elements even after retry: {}", retryException.getMessage());
            }
        }
    }

    public LoginPage typeUsername(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement usernameElement = wait.until(ExpectedConditions.elementToBeClickable(usernameField));
        usernameElement.clear();
        usernameElement.sendKeys(username);
        logger.info("Typed username: {}", username);
        return this;
    }

    public LoginPage typePassword(String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement passwordElement = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordElement.clear();
        passwordElement.sendKeys(password);
        logger.info("Typed password");
        return this;
    }

    public LoginPage clearUsername() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement usernameElement = wait.until(ExpectedConditions.elementToBeClickable(usernameField));
        usernameElement.clear();
        logger.info("Cleared username field");
        return this;
    }

    public LoginPage clearPassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement passwordElement = wait.until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordElement.clear();
        logger.info("Cleared password field");
        return this;
    }

    public LoginPage clickLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement loginElement = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginElement.click();
        logger.info("Clicked Login button");
        return this;
    }

    public String getErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
            String message = error.getText();
            logger.info("Error message: {}", message);
            return message;
        } catch (Exception e) {
            logger.warn("Error message not found or timeout occurred: {}", e.getMessage());
            return "";
        }
    }

    public DashboardPage loginAsValidUser(String username, String password) {
        typeUsername(username).typePassword(password).clickLogin();
        return new DashboardPage(driver);
    }
}