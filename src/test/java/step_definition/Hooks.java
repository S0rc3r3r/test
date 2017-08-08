package step_definition;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
    // public static WebDriver driver;
    public static RemoteWebDriver driver;
    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);

    @Before
    /**
     * Delete all cookies at the start of each scenario to avoid
     * shared state between tests
     */
    public void openBrowser() throws MalformedURLException {
        String useGrid = System.getProperty("USE_GRID");
        if (useGrid == null) {
            useGrid = System.getenv("USE_GRID");
            if (useGrid == null) {
                useGrid = "true";
            }

        }

        String browser = System.getProperty("BROWSER");
        if (browser == null) {
            browser = System.getenv("BROWSER");
            if (browser == null) {
                browser = "chrome";
            }
        }
        switch (browser) {
            case "chrome":

                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName(browser);
                capabilities.setVersion("");
                capabilities.setPlatform(Platform.WINDOWS);
                capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

                ChromeOptions options = new ChromeOptions();
                options.addArguments("disable-infobars");
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);

                if (Boolean.valueOf(useGrid)) {
                    LOGGER.info("Starting ChromeDriver using grid");
                    driver = new RemoteWebDriver(new URL("http://localhost:5555/wd/hub"), capabilities);
                    break;
                }
                LOGGER.info("Starting ChromeDriver using local installation");
                System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
                driver = new ChromeDriver(capabilities);
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "ie":
                driver = new InternetExplorerDriver();
                break;
            case "safari":
                driver = new SafariDriver();
                break;
            default:
                driver = new ChromeDriver();
                break;
        }
        System.out.println("Opening Browser...." + browser);
        driver.manage().deleteAllCookies();
    }

    @After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void embedScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                scenario.write("Current Page URL is " + driver.getCurrentUrl());
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");

            } catch (WebDriverException ex) {
                System.out.println(ex.getMessage());
            }
        }
        driver.quit();
    }
}