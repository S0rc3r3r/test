package com.muso.selenium.base.waits.conditions.products;

import org.apache.commons.lang3.StringEscapeUtils;
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

public class ConditionWaitUntilProductRemovedFromSelectionArea implements ExpectedCondition<Boolean> {

    private String name;
    private String holder = "muso-product-filter ul.dropdown-menu.inner li";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilProductRemovedFromSelectionArea.class);

    public ConditionWaitUntilProductRemovedFromSelectionArea(String name) {
        this.name = StringEscapeUtils.escapeEcmaScript(name);
    }

    @Override
    public Boolean apply(final WebDriver driver) {
        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        try {
            WebElement elem = driver.findElement(factory.cssContainingText(holder, name));

            if (elem.isDisplayed() && elem.getAttribute("aria-selected").equals("true"))
                return false;
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            LOGGER.debug("{} was not found in Product selection area", name);
            return true;
        }

        return null;
    }

}