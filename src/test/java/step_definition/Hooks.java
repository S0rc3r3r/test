package step_definition;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.OutputType;
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

import com.muso.persistence.PersistenceManager;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
    // public static WebDriver driver;
    public static RemoteWebDriver driver;
    private static final String DEFAULT_OS = "Windows";
    private static final String DEFAULT_OS_VERSION = "10";
    private static final String DEFAULT_JENKINS_BUILD = "Local";
    private static final String DEFAULT_CHROME_VERSION = "61";
    private static final String DEFAULT_APPLICATION_URL = "http://localhost:4200";
    private static final String DEFAULT_BROWSERSTACK_USER = "tanasoiubogdan1";
    private static final String DEFAULT_BROWSERSTACK_ACCESSKEY = "Wqgm52qvGRiroSxFoxxF";
    private final boolean saveScreenshotLocally = true;

    private DesiredCapabilities chromeCapabilities;

    String username = System.getenv("BROWSERSTACK_USER");
    String authkey = System.getenv("BROWSERSTACK_ACCESSKEY");
    String os = System.getenv("BROWSERSTACK_OS");
    String os_version = System.getenv("BROWSERSTACK_OS_VERSION");
    String buildNo = System.getenv("JENKINS_BUILDNO");
    String browser = System.getenv("BROWSERSTACK_BROWSER");
    String browser_version = System.getenv("BROWSERSTACK_BROWSER_VERSION");
    String useGrid = System.getenv("USE_GRID");
    String application_url = System.getenv("APPLICATION_URL");

    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void init(Scenario scenario) throws MalformedURLException {

        LOGGER.info("Starting Sccenario: {}", scenario.getName());
        initVars();
        initBrowser(scenario);
        startBrowser();
    }

    private void initVars() {
        LOGGER.info("Starting variables initialization");

        if (useGrid == null) {
            useGrid = System.getProperty("USE_GRID");
            if (useGrid == null) {
                useGrid = "false";
                LOGGER.info("USE_GRID variable not provided or null. Using default value <false>");
            }
        }

        if (browser == null) {
            browser = System.getProperty("BROWSER");
            if (browser == null) {
                browser = "Chrome";
                LOGGER.info("BROWSERSTACK_BROWSER variable not provided or null. Using default value <chrome>");
            }
        }

        if (browser_version == null) {
            browser_version = System.getProperty("BROWSERSTACK_BROWSER_VERSION");
            if (browser_version == null) {
                switch (browser) {
                case "Chrome":
                    browser_version = DEFAULT_CHROME_VERSION;
                    LOGGER.info("BROWSERSTACK_BROWSER_VERSION variable not provided or null. Using default value " + DEFAULT_CHROME_VERSION);
                    break;
                default:
                    throw new InvalidArgumentException("Please add default browser version for browser " + browser);
                }
            }
        }

        if (application_url == null) {
            application_url = System.getProperty("APPLICATION_URL");
            if (application_url == null) {
                application_url = DEFAULT_APPLICATION_URL;
                LOGGER.info("APPLICATION_URL variable not provided or null. Using default value <{}>", DEFAULT_APPLICATION_URL);
            }
        }

        System.setProperty("application_url", application_url);

        if (Boolean.valueOf(useGrid)) {
            if (username == null) {
                username = System.getenv("BROWSERSTACK_USER");
                if (username == null) {
                    // LOGGER.error("BROWSERSTACK_USER variable not provided or null. Unable to proceed");
                    // throw new InvalidArgumentException("BROWSERSTACK_USER variable not provided or null. Unable to
                    // proceed");
                    username = DEFAULT_BROWSERSTACK_USER;
                }
            }
            if (authkey == null) {
                authkey = System.getenv("BROWSERSTACK_ACCESSKEY");
                if (authkey == null) {
                    // LOGGER.error("BROWSERSTACK_ACCESSKEY variable not provided or null. Unable to proceed");
                    // throw new InvalidArgumentException("BROWSERSTACK_ACCESSKEY variable not provided or null. Unable
                    // to proceed");
                    authkey = DEFAULT_BROWSERSTACK_ACCESSKEY;
                }
            }

            if (os == null) {
                os = System.getenv("BROWSERSTACK_OS");
                if (os == null) {
                    os = DEFAULT_OS;
                    LOGGER.info("BROWSERSTACK_OS variable not provided or null.Using default value <{}>", DEFAULT_OS);

                }
            }

            if (os_version == null) {
                os_version = System.getenv("BROWSERSTACK_OS_VERSION");
                if (os_version == null) {
                    os_version = DEFAULT_OS_VERSION;
                    LOGGER.info("BROWSERSTACK_OS_VERSION variable not provided or null.Using default value <{}>", DEFAULT_OS_VERSION);
                }
            }

            if (buildNo == null) {
                buildNo = System.getenv("JENKINS_BUILDNO");
                if (buildNo == null) {
                    buildNo = DEFAULT_JENKINS_BUILD;
                    LOGGER.info("JENKINS_BUILDNO variable not provided or null.Using random value <{}>", DEFAULT_JENKINS_BUILD);
                }
            }
        }
    }

    private void initBrowser(Scenario scenario) throws MalformedURLException {
        LOGGER.info("Init {} browser capabilities", browser);

        switch (browser) {
        case "Chrome":
            chromeCapabilities = new DesiredCapabilities();
            chromeCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("disable-infobars");
            options.addArguments("--start-maximized");
            chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, options);

            chromeCapabilities.setCapability("build", buildNo + " - " + scenario.getName());
            chromeCapabilities.setCapability("os", os);
            chromeCapabilities.setCapability("os_version", os_version);
            chromeCapabilities.setCapability("browser", browser);
            chromeCapabilities.setCapability("version", browser_version);
            chromeCapabilities.setCapability("browserstack.debug", "true");
            break;
        case "Firefox":

        case "IE":

        case "Safari":

        default:
            throw new InvalidArgumentException(browser + " is not configured in HOOKS.Please configure");

        }

    }

    private void startBrowser() throws MalformedURLException {
        switch (browser) {
        case "Chrome":

            if (Boolean.valueOf(useGrid)) {
                LOGGER.info("Starting Chrome using GRID URL: https://{}:{}@hub-cloud.browserstack.com/wd/hub", username, authkey);
                driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + "@hub-cloud.browserstack.com/wd/hub"), chromeCapabilities);
            } else {
                LOGGER.info("Starting ChromeDriver using local install");
                System.setProperty("webdriver.chrome.driver", "chromeDriver/chromedriver");
                driver = new ChromeDriver(chromeCapabilities);
            }

            break;
        case "Firefox":
            driver = new FirefoxDriver();
            break;
        case "IE":
            driver = new InternetExplorerDriver();
            break;
        case "Safari":
            driver = new SafariDriver();
            break;
        default:
            driver = new ChromeDriver();
            break;
        }

        driver.manage().deleteAllCookies();
        // driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void embedScreenshot(Scenario scenario) {
        LOGGER.info("Ending scenario: {} . Status: {} - IsFailed {}", scenario.getName(), scenario.getStatus(), scenario.isFailed());
        if (scenario.isFailed()) {
            LOGGER.info("Saving Screenshot");
            try {
                scenario.write("Current Page URL is " + driver.getCurrentUrl());
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");

                if (saveScreenshotLocally) {
                    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    FileUtils.copyFile(scrFile, new File(scenario.getName() + ".jpg"));
                }

            } catch (WebDriverException | IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        PersistenceManager.getInstance().clear();
        driver.quit();
    }

}