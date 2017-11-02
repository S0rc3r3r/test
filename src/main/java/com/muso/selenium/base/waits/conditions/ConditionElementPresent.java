package com.muso.selenium.base.waits.conditions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionElementPresent implements ExpectedCondition<WebElement> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionElementPresent.class);

    private final By byObject;

    public ConditionElementPresent(final By byLoc) {
        this.byObject = byLoc;
    }

    @Override
    public WebElement apply(final WebDriver driver) {
        final List<WebElement> elemList = driver.findElements(byObject);

        try {
            if (!elemList.isEmpty()) {
                return elemList.get(0);
            }
        } catch (StaleElementReferenceException e) {
            LOGGER.debug("SERE encountered in ConditionElementPresent");
        }

        return null;
    }

}
