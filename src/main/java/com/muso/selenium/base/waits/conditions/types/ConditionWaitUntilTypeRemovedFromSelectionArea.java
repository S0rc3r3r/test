package com.muso.selenium.base.waits.conditions.types;

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

public class ConditionWaitUntilTypeRemovedFromSelectionArea implements ExpectedCondition<Boolean> {

    private String name;
    private String holder = "muso-filter-type muso-filter-type-item";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilTypeRemovedFromSelectionArea.class);

    public ConditionWaitUntilTypeRemovedFromSelectionArea(String name) {
        this.name = StringEscapeUtils.escapeEcmaScript(name);
    }

    @Override
    public Boolean apply(final WebDriver driver) {
        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        try {
            WebElement elem = driver.findElement(factory.cssContainingText(holder, name));
            if (elem.getText().equals(name) && (elem.getAttribute("style").equals("display: block;") || elem.getAttribute("style") == null))
                return false;
        } catch (StaleElementReferenceException | NoSuchElementException e) {
            LOGGER.debug("{} was not found in Type selection area", name);
            return true;
        }

        return null;
    }

}