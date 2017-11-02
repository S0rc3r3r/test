package com.muso.selenium.base.waits;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WebdriverTimeSetter {

    private long currentTime;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebdriverTimeSetter.class);

    /* Private constructor prevents instantiation from other classes */
    public WebdriverTimeSetter(final long time) {
        currentTime = time;
    }

    public void setWaitTime(final WebDriver driver, final long time) {

        currentTime = time;
        driver.manage().timeouts().implicitlyWait(currentTime, TimeUnit.SECONDS);
        LOGGER.trace("Change wait time to {} seconds", currentTime);
    }

    public void resetWaitTime(final WebDriver driver) {

        currentTime = 60;
        driver.manage().timeouts().implicitlyWait(currentTime, TimeUnit.SECONDS);
        LOGGER.trace("Change wait time to {} seconds", currentTime);
    }

    public long getCurrentTime() {
        return currentTime;
    }
}
