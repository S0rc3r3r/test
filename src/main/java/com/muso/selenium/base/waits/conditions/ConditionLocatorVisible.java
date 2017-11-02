package com.muso.selenium.base.waits.conditions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionLocatorVisible implements ExpectedCondition<WebElement> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionLocatorVisible.class);

    private final Locator locator;
    private final By byObject;
    private final long startTime;

    public ConditionLocatorVisible(final Locator locator, final By byObject) {
        this.locator = locator;
        this.byObject = byObject;
        startTime = System.currentTimeMillis();
    }

    @Override
    public WebElement apply(final WebDriver driver) {
        final List<WebElement> elemList = driver.findElements(byObject);
        WebElement elem;

        if (elemList.isEmpty()) {
            LOGGER.trace("Waiting for element with {} '{}'", locator.getType(), locator.getName());
            elem = null;
        } else {
            elem = elemList.get(0);
            if (elem.isDisplayed()) {
                final long currentTime = System.currentTimeMillis();
                LOGGER.debug("Found element with '{}' in {} ms", locator.getName(),
                        currentTime - startTime);
            } else {
                LOGGER.error("Element is present but it is not displayed.");
                elem = null;
            }
        }

        return elem;
    }

}
