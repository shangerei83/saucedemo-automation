package utils;

import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(DriverManager.class);

    private DriverManager() {}

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            try {
                logger.info("Attempting to quit WebDriver for thread: {}", Thread.currentThread().getName());

                // Only use quit() - it handles both windows and driver cleanup
                webDriver.quit();
                logger.info("WebDriver quit successfully");

            } catch (Exception e) {
                logger.warn("Error during WebDriver cleanup: {}", e.getMessage());

                if (e.getMessage() != null && e.getMessage().contains("Tried to run command without establishing a connection")) {
                    logger.info("WebDriver session was already terminated, continuing with cleanup");
                }

            } finally {
                driver.remove();
                logger.debug("ThreadLocal driver reference cleared");
            }
        }
    }
}