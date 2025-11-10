package tests;

import config.BrowserFactory;
import data.TestDataProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pages.DashboardPage;
import pages.LoginPage;
import utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class LoginTests {
    private static final Logger logger = LogManager.getLogger(LoginTests.class);
    private final String browser;
    private WebDriver driver;
    private LoginPage loginPage;

    public LoginTests(String browser) {
        this.browser = browser;
    }

    @Parameterized.Parameters(name = "Browser: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(TestDataProvider.browsers());
    }

    @Before
    public void setUp() {
        logger.info("Starting test in browser: {}", browser);
        driver = BrowserFactory.createDriver(browser);
        DriverManager.setDriver(driver);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testLoginWithEmptyCredentials() {
        loginPage
                .typeUsername("test_user")
                .typePassword("test_password")
                .clearUsername()
                .clearPassword()
                .clickLogin();

        String errorMessage = loginPage.getErrorMessage();

            assertThat("Error message should indicate login failure for empty credentials",
                errorMessage, anyOf(
                        containsString("Username is required"),
                        containsString("Username and password do not match")
                ));

        logger.info("Verified error message for empty credentials: {}", errorMessage);
    }

    @Test
    public void testLoginWithMissingPassword() {
        loginPage
                .typeUsername("standard_user")
                .typePassword("temp_password")
                .clearPassword()
                .clickLogin();

        String errorMessage = loginPage.getErrorMessage();

                assertThat("Error message should indicate login failure for missing password",
                errorMessage, anyOf(
                        containsString("Password is required"),
                        containsString("Username and password do not match")
                ));

        logger.info("Verified error message for missing password: {}", errorMessage);
    }

    @Test
    public void testValidLogin() {
        DashboardPage dashboardPage = loginPage
                .loginAsValidUser("standard_user", "secret_sauce");

        String title = dashboardPage.getTitle();

        assertThat("Dashboard title should be 'Swag Labs' after successful login",
                title, is(equalTo("Swag Labs")));

        logger.info("Successfully verified dashboard title: {}", title);
    }

    @After
    public void tearDown() {
        logger.info("Test finished in browser: {}", browser);
        DriverManager.quitDriver();
    }
}