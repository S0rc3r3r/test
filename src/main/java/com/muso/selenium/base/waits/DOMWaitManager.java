package com.muso.selenium.base.waits;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DOMWaitManager {

    private final static int TIMEOUT_SECONDS = 120;

    private static final Logger LOGGER = LoggerFactory.getLogger(DOMWaitManager.class);

    public static void waitDOMContentLoaded(final WebDriver driver, final int timeout) {

        boolean pageLoaded = false;
        String pageState;
        final long startTime = System.currentTimeMillis() / 1000;
        long currentTime = System.currentTimeMillis() / 1000;

        LOGGER.debug("Waiting for DOM content to be loaded");
        while ((!pageLoaded) && ((currentTime - startTime) < timeout)) {
            currentTime = System.currentTimeMillis() / 1000;

            try {
                pageState = (String) ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState");
                LOGGER.trace("Current page state = " + pageState);
                if (pageState.equalsIgnoreCase("complete")) {
                    pageLoaded = true;
                }
            }

            catch (Exception e) {
                LOGGER.error("XMLHttpRequest onerror handler", e);
            }
        }

        if ((currentTime - startTime) >= timeout) {
            LOGGER.warn("Timeout reached in waitDOMContentLoaded. Timeout: " + timeout
                    + " seconds");
        }
    }

    public static void waitDOMContentLoaded(final WebDriver driver) {

        waitDOMContentLoaded(driver, TIMEOUT_SECONDS);
    }
}
