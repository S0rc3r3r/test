package com.muso.selenium.base.waits.conditions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionElementVisible implements ExpectedCondition<WebElement> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionElementVisible.class);

    private final By byObject;

    public ConditionElementVisible(final By byLoc) {
        this.byObject = byLoc;
    }

    @Override
    public WebElement apply(final WebDriver driver) {
        WebElement elem;
        final List<WebElement> elemList = driver.findElements(byObject);

        try {
            if (!elemList.isEmpty()) {
                elem = elemList.get(0);
                if (elem.isDisplayed()) {
                    return elem;
                }
            }
        } catch (StaleElementReferenceException e) {
            LOGGER.debug("SERE encountered in ConditionElementVisible");
        }

        return null;
    }
}
