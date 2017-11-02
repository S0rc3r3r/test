package com.muso.selenium.base.waits.conditions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionLocatorNotPresent implements ExpectedCondition<Boolean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionLocatorNotPresent.class);

    private final Locator locator;
    private final By byObject;

    public ConditionLocatorNotPresent(final Locator locator, final By byObject) {
        this.locator = locator;
        this.byObject = byObject;
    }

    @Override
    public Boolean apply(final WebDriver driver) {

        final boolean notFound = driver.findElements(byObject).isEmpty();

        if (notFound) {
            LOGGER.trace("Element with {} '{}' not found as expected", locator.getType(),
                    locator.getName());
        } else {
            LOGGER.trace("Element with {} '{}' found", locator.getType(), locator.getName());
        }

        return notFound;
    }

}
