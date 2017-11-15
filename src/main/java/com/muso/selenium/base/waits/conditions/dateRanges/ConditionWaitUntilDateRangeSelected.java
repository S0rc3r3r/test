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

public class ConditionWaitUntilDateRangeSelected implements ExpectedCondition<Boolean> {

    private String name;
    private String holder = "muso-period-filter ul li";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilDateRangeSelected.class);

    public ConditionWaitUntilDateRangeSelected(String name) {
        this.name = name;
    }

    @Override
    public Boolean apply(final WebDriver driver) {
        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        WebElement elem = driver.findElement(factory.cssContainingText(holder, name));

        try {
            if (elem.getText().equals(name) && elem.getAttribute("class").equals("active"))
                return true;
        } catch (StaleElementReferenceException e) {
            LOGGER.warn("SERE encountered in ConditionElementVisible");
        }

        return null;
    }

}