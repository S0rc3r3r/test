package com.muso.stepDefinition;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.AssumptionViolatedException;
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
    private static final String DEFAULT_OS = System.getProperty("os.name");
    private static final String DEFAULT_OS_VERSION = System.getProperty("os.version");
    private static final String DEFAULT_JENKINS_BUILD = "Local";
    private static final String DEFAULT_APPLICATION_URL = "http://muso-dashboard-qa.s3-website-eu-west-1.amazonaws.com";
    private static final String DEFAULT_BROWSERSTACK_USER = "tanasoiubogdan1";
    private static final String DEFAULT_BROWSERSTACK_ACCESSKEY = "Wqgm52qvGRiroSxFoxxF";
    private static final String DEFAULT_BROWSERSTACK_USE_REAL_DEVICE = "true";
    private final boolean saveScreenshotLocally = true;

    private DesiredCapabilities desiredCapabilities;
    private ChromeOptions options;

    private String username = System.getProperty("BROWSERSTACK_USER");
    private String authkey = System.getProperty("BROWSERSTACK_ACCESSKEY");
    private String os = System.getProperty("BROWSERSTACK_OS");
    private String os_version = System.getProperty("BROWSERSTACK_OS_VERSION");
    private String buildNo = System.getProperty("JENKINS_BUILDNO");
    private String browser = System.getProperty("BROWSERSTACK_BROWSER");
    private String useGrid = System.getProperty("USE_GRID");
    private String application_url = System.getProperty("APPLICATION_URL");
    private String use_real_device = System.getProperty("BROWSERSTACK_USE_REAL_DEVICE");

    private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void init(Scenario scenario) throws MalformedURLException {
        LOGGER.info("--------------------------------------------");
        LOGGER.info("Starting Sccenario: {}", scenario.getName());
        initVars();
        initCapabilities(scenario);
        startBrowser();
    }

    @Before("@Mobile")
    public void skip_scenario(Scenario scenario) {
        if (os.contains("OS X") || os.contains("Windows")) {

        } else {
            LOGGER.warn("SKIP SCENARIO: " + scenario.getName());
            throw new AssumptionViolatedException("Scenario not supported on MOBILE devices.");
        }
    }

    private void initVars() {
        LOGGER.info("Starting variables initialization");

        if (useGrid == null) {
            useGrid = System.getenv("USE_GRID");
            if (useGrid == null) {
                useGrid = "false";
                LOGGER.info("USE_GRID variable not provided or null. Using default value <false>");
            }
        }

        if (browser == null) {
            browser = System.getenv("BROWSERSTACK_BROWSER");
            if (browser == null) {
                browser = "Chrome";
                LOGGER.info("BROWSERSTACK_BROWSER variable not provided or null. Using default value <chrome>");
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

        if (application_url == null) {
            application_url = System.getenv("APPLICATION_URL");
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

            if (buildNo == null) {
                buildNo = System.getenv("JENKINS_BUILDNO");
                if (buildNo == null) {
                    buildNo = DEFAULT_JENKINS_BUILD;
                    LOGGER.info("JENKINS_BUILDNO variable not provided or null.Using random value <{}>", DEFAULT_JENKINS_BUILD);
                }
            }

            if (use_real_device == null) {
                use_real_device = System.getenv("BROWSERSTACK_USE_REAL_DEVICE");
                if (use_real_device == null) {
                    use_real_device = DEFAULT_BROWSERSTACK_USE_REAL_DEVICE;
                    LOGGER.info("BROWSERSTACK_USE_REAL_DEVICE variable not provided or null. Using default value <true>");
                }
            }

        }
    }

    private void initCapabilities(Scenario scenario) throws MalformedURLException {
        LOGGER.info("Init {} browser capabilities", browser);

        options = new ChromeOptions();

        desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);

        desiredCapabilities.setCapability("build", buildNo + " - " + scenario.getName());
        desiredCapabilities.setCapability("browserstack.debug", "true");
        desiredCapabilities.setCapability("project", "Muso Dashboard");

        if (os.equalsIgnoreCase("Android") || os.equalsIgnoreCase("IOS") || os.equalsIgnoreCase("Windows Phone")) {
            desiredCapabilities.setCapability("device", os_version);
            desiredCapabilities.setCapability("realMobile", use_real_device);
            desiredCapabilities.setCapability("real_mobile", use_real_device);
        } else {
            desiredCapabilities.setCapability("os", os.replaceAll("MAC ", ""));
            desiredCapabilities.setCapability("os_version", os_version);
            desiredCapabilities.setCapability("browser", browser);
        }

        switch (browser) {
        case "Chrome":
            options.addArguments("disable-infobars");
            options.addArguments("--start-maximized");
            break;
        case "Firefox":
            break;

        case "IE":
            break;
        case "Safari":
            break;
        default:
            throw new InvalidArgumentException(browser + " is not configured in HOOKS.Please configure");

        }

        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);

    }

    private void startBrowser() throws MalformedURLException {

        if (Boolean.valueOf(useGrid)) {
            LOGGER.info("Starting Browser using GRID URL: https://{}:{}@hub-cloud.browserstack.com/wd/hub", username, authkey);
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + "@hub-cloud.browserstack.com/wd/hub"), desiredCapabilities);
        } else {
            switch (browser) {
            case "Chrome":
                LOGGER.info("Starting ChromeDriver using local install");
                System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");

                if (os.equalsIgnoreCase("Android") || os.equalsIgnoreCase("IOS") || os.equalsIgnoreCase("Windows Phone")) {
                    Map<String, String> mobileEmulation = new HashMap<>();
                    mobileEmulation.put("   ", os_version);

                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

                    driver = new ChromeDriver(chromeOptions);
                } else {
                    driver = new ChromeDriver(options);
                }
                break;
            case "Firefox":
                LOGGER.info("Starting GeckoDriver using local install");
                System.setProperty("webdriver.gecko.driver", "Drivers/geckodriver");
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
        }

        LOGGER.info("---------------------------------");
        LOGGER.info("OS: {}", os);
        if (os.equalsIgnoreCase("Android") || os.equalsIgnoreCase("IOS") || os.equalsIgnoreCase("Windows Phone")) {
            LOGGER.info("DEVICE: {}", os_version);
            LOGGER.info("IS REAL DEVICE: {}", use_real_device);

        } else {
            LOGGER.info("OS_VERSION: {}", os_version);
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        }
        LOGGER.info("BROWSER: {}", browser);
        LOGGER.info("---------------------------------");

        driver.manage().deleteAllCookies();

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

        if (Boolean.valueOf(useGrid)) {
            updateBrowserStackStatus(scenario.isFailed());
        }

        PersistenceManager.getInstance().clear();
        driver.quit();
        System.out.println();
        System.out.println();
        System.out.println();
    }

    public void updateBrowserStackStatus(Boolean isFailed) {

        String browserstackUrl = "https://" + username + ":" + authkey;

        try {
            URI uri = new URI(browserstackUrl + "@www.browserstack.com/automate/sessions/" +
                    driver.getSessionId().toString() + ".json");
            HttpPut putRequest = new HttpPut(uri);

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            if (!isFailed) {
                nameValuePairs.add((new BasicNameValuePair("status", "completed")));
                LOGGER.debug("Marked Browserstack session status: completed.");
            } else {
                nameValuePairs.add((new BasicNameValuePair("status", "error")));
                nameValuePairs.add((new BasicNameValuePair("reason", "failure ")));

                LOGGER.error("Marked Browserstack session status: error!");
            }
            putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpClientBuilder.create().build().execute(putRequest);
        } catch (URISyntaxException | IOException e) {
            LOGGER.error("Error marking test result as Browserstack session status!", e);
        }
    }

}