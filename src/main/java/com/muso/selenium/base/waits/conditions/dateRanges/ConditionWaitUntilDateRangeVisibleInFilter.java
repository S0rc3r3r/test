package com.muso.selenium.base.waits.conditions.dateRanges;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class ConditionWaitUntilDateRangeVisibleInFilter implements ExpectedCondition<WebElement> {

    private String name;
    private String holder = "muso-period-filter ul li";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilDateRangeVisibleInFilter.class);

    public ConditionWaitUntilDateRangeVisibleInFilter(String name) {
        this.name = name;
    }

    @Override
    public WebElement apply(final WebDriver driver) {
        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        WebElement elem = driver.findElement(factory.cssContainingText(holder, name));

        try {
            if (elem.isDisplayed() && elem.getText().equals(name))
                return elem;
        } catch (StaleElementReferenceException e) {
            LOGGER.warn("SERE encountered in ConditionElementVisible");
        }

        return null;
    }

}