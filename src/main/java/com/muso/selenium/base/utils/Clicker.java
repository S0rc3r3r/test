package com.muso.selenium.base.utils;

import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.selenium.base.waits.IMethodWaitBoolean;
import com.muso.selenium.base.waits.MethodWaitHandler;

public class Clicker {

    private static final Logger LOGGER = LoggerFactory.getLogger(Clicker.class);

    public static void safeClick(final WebDriver driver, final WebElement element) {

        try {
            element.click();
        } catch (ElementNotVisibleException e) {
            LOGGER.debug("Element not visible exception encountered: ", e);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);arguments[0].click();",
                    new Object[] { element });
        } catch (WebDriverException e) {
            if (e.getLocalizedMessage().contains("is not clickable at point")) {
                LOGGER.debug("Element is not clickable at point exception encountered: ");
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                        new Object[] { element });
                element.click();
            } else {
                LOGGER.warn(e.getLocalizedMessage());
            }
        }
    }

    /**
     * Try to click again on the specified element upon receiving
     * WebDriverException until successful or timeout
     * 
     * Usage this method might be useful in case of
     * "org.openqa.selenium.WebDriverException: Element is not clickable at point.."
     * exception when the click cannot be performed due to some other elements
     * hiding the actual element; it is supposed that those overlapped elements
     * will eventually hide/disappear and let the flow continue as normal,
     * otherwise the method might not be applicable
     * 
     * @param driver
     * @param element
     * @param timeoutSeconds
     */
    public static void safeClick(final WebDriver driver, final WebElement element,
        final int timeoutSeconds) {

        new MethodWaitHandler().waitForConditionTrue(new IMethodWaitBoolean<WebElement>() {
            @Override
            public boolean waitCondition(final WebElement element) {
                boolean wasClickSafe = false;
                try {
                    element.click();
                    wasClickSafe = true;
                } catch (WebDriverException e) {
                    LOGGER.warn("Error while trying to click on '{}' element, retrying...",
                            element.getAttribute("outerHTML"));
                    wasClickSafe = false;
                }
                return wasClickSafe;
            }

        }, element, timeoutSeconds);

    }

}
