package com.muso.selenium.base.waits.conditions.dateRanges;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.selenium.base.waits.conditions.products.ConditionWaitUntilProductSelected;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class ConditionWaitUntilDateRangeDeselected implements ExpectedCondition<Boolean> {

    private String name;
    private String holder = "muso-period-filter ul li";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilProductSelected.class);

    public ConditionWaitUntilDateRangeDeselected(String name) {
        this.name = name;
    }

    @Override
    public Boolean apply(final WebDriver driver) {
        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        try {
            WebElement elem = driver.findElement(factory.cssContainingText(holder, name));

            if (elem.getText().equals(name) && !elem.getAttribute("class").equals("selected"))
                return true;
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            LOGGER.debug("{} was not found in Product filter list", name);
            return true;
        }

        return null;
    }

}