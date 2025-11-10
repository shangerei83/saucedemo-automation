package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

public class DashboardPage {
    private final WebDriver driver;
    private static final Logger logger = LogManager.getLogger(DashboardPage.class);

    private final By appLogo = By.cssSelector(".app_logo");
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        waitForDashboardToLoad();
    }

    private void waitForDashboardToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(appLogo));
    }

    public String getTitle() {
        String title = driver.getTitle();
        logger.info("Dashboard title: {}", title);
        return title;
    }
}