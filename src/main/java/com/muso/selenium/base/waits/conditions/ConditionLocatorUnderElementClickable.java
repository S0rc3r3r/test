package com.muso.selenium.base.waits.conditions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionLocatorUnderElementClickable implements ExpectedCondition<WebElement> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionLocatorUnderElementClickable.class);

    private final Locator locator;
    private final By byObject;
    private final long startTime;
    private final WebElement rootElement;

    public ConditionLocatorUnderElementClickable(final Locator locator, final By byObject,
                                                 final WebElement rootElement) {
        this.locator = locator;
        this.byObject = byObject;
        this.rootElement = rootElement;
        startTime = System.currentTimeMillis();
    }

    @Override
    public WebElement apply(final WebDriver driver) {
        final List<WebElement> elemList = rootElement.findElements(byObject);
        WebElement elem;

        if (elemList.isEmpty()) {
            LOGGER.trace("Waiting for element with {} '{}'", locator.getType(), locator.getName());
            elem = null;
        } else {
            elem = elemList.get(0);
            if (elem.isDisplayed() && elem.isEnabled()) {
                final long currentTime = System.currentTimeMillis();
                LOGGER.debug("Found element with '{}' in {} ms", locator.getName(),
                        currentTime - startTime);
            } else {
                LOGGER.error("Element is present and displayed but it is not clickable.");
                elem = null;
            }
        }

        return elem;
    }

}
