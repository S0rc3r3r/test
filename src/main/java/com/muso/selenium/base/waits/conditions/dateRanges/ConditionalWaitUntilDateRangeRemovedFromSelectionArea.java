package com.muso.selenium.base.waits.conditions.dateRanges;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class ConditionalWaitUntilDateRangeRemovedFromSelectionArea implements ExpectedCondition<Boolean> {

    private String name;
    private String holder = "muso-period-filter #selectedPeriod span";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionalWaitUntilDateRangeRemovedFromSelectionArea.class);

    public ConditionalWaitUntilDateRangeRemovedFromSelectionArea(String name) {
        this.name = name;
    }

    @Override
    public Boolean apply(final WebDriver driver) {
        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        try {
            WebElement elem = driver.findElement(factory.cssContainingText(holder, name));

            if (elem.isDisplayed() && elem.getText().equals(name))
                return false;
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            LOGGER.debug("{} was not found in Product selection area", name);
            return true;
        }

        return null;
    }

}