package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;

public class BrowserFactory {

    public static WebDriver createDriver(String browser) {
        WebDriver driver;
        if ("edge".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.edge.driver", "msedgedriver.exe");
            EdgeOptions options = new EdgeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-gpu");

            driver = new EdgeDriver(options);
        } else if ("firefox".equalsIgnoreCase(browser)) {
            System.setProperty("webdriver.firefox.bin", "C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            FirefoxOptions options = new FirefoxOptions();
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            // Убираем сложные настройки которые ломают Firefox
            options.addPreference("network.proxy.type", 5); // use system proxy
            options.addPreference("network.dns.disableIPv6", true);
            options.addPreference("network.http.http3.enabled", false);
            options.addPreference("network.trr.mode", 5);

            driver = new FirefoxDriver(options);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // Set generous timeouts after driver creation
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(180));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(60));
        driver.manage().window().maximize();

        return driver;
    }
}