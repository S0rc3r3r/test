package com.muso.selenium.base.waits.conditions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionLocatorPresent implements ExpectedCondition<WebElement> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionLocatorPresent.class);

    private final Locator locator;
    private final By byObject;
    private final long startTime;

    public ConditionLocatorPresent(final Locator locator, final By byObject) {

        this.locator = locator;
        this.byObject = byObject;
        startTime = System.currentTimeMillis();
    }

    @Override
    public WebElement apply(final WebDriver driver) {

        WebElement elem;
        final List<WebElement> elemList = driver.findElements(byObject);
        if (elemList.isEmpty()) {
            LOGGER.trace("Waiting for element with {} '{}'", locator.getType(), locator.getName());
            elem = null;
        } else {
            final long currentTime = System.currentTimeMillis();
            LOGGER.debug("Found element with '{}' in {} ms", locator.getName(),
                    currentTime - startTime);
            elem = elemList.get(0);
        }

        return elem;
    }

}
