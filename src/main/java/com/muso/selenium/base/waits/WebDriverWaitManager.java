package com.muso.selenium.base.waits;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WebDriverWaitManager {

    private static final WebDriverWaitManager INSTANCE = new WebDriverWaitManager();

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverWaitManager.class);

    private boolean implicitWaitEnabled;
    private long implicitWait;
    private long explicitLongWait;
    private long explicitShortWait;

    // Constructor

    private WebDriverWaitManager() {
        implicitWaitEnabled = false;
        implicitWait = 60;
        explicitLongWait = 10;
        explicitShortWait = 5;
    }

    public static WebDriverWaitManager getInstance() {
        return INSTANCE;
    }

    // WaitManager configuration

    public void setLongWait(final long seconds) {
        explicitLongWait = seconds;
        LOGGER.debug("Setting explicit long wait = {}", explicitLongWait);
    }

    public void setShortWait(final long seconds) {
        explicitShortWait = seconds;
        LOGGER.debug("Setting explicit short wait = {}", explicitShortWait);
    }

    public void enableImplicitWait(final long seconds) {
        implicitWaitEnabled = true;
        implicitWait = seconds;
    }

    public boolean isImplicitWaitEnabled() {
        return implicitWaitEnabled;
    }

    public long getImplicitWaitTimeout() {
        return implicitWait;
    }

    // Explicit wait methods

    public WebDriverWait getExplicitWebDriverWait(final WebDriver driver, final long seconds) {
        return new WebDriverWait(driver, seconds);
    }

    public WebDriverWait getLongWebDriverWait(final WebDriver driver) {
        return getExplicitWebDriverWait(driver, explicitLongWait);
    }

    public WebDriverWait getShortWebDriverWait(final WebDriver driver) {
        return getExplicitWebDriverWait(driver, explicitShortWait);
    }

    public WebElement explicitWaitUntil(final WebDriver driver,
        final ExpectedCondition<WebElement> condition, final long seconds) {

        WebElement element = null;

        try {
            element = getExplicitWebDriverWait(driver, seconds)
                    .until(condition);
        } catch (TimeoutException exc) {

            throw exc;
        }

        return element;
    }

    public Boolean explicitWaitUntilTrue(final WebDriver driver, final ExpectedCondition<Boolean> condition, final long seconds) {

        Boolean isCompleted = false;

        try {
            isCompleted = getExplicitWebDriverWait(driver, seconds)
                    .until(condition);
        } catch (TimeoutException exc) {

            //throw exc;
        }

        return isCompleted;
    }

    public WebElement explicitLongWaitUntil(final WebDriver driver,
        final ExpectedCondition<WebElement> condition) {

        return explicitWaitUntil(driver, condition, explicitLongWait);
    }

    public WebElement explicitShortWaitUntil(final WebDriver driver,
        final ExpectedCondition<WebElement> condition) {

        return explicitWaitUntil(driver, condition, explicitShortWait);
    }

    public Boolean explicitShortWaitUntilTrue(WebDriver driver, ExpectedCondition<Boolean> condition) {
        return explicitWaitUntilTrue(driver, condition, explicitShortWait);

    }

}
