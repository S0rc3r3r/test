package com.muso.selenium.base.waits.conditions.types;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class ConditionalWaitUntilTypeVisibleInSelectionArea implements ExpectedCondition<Boolean> {

    private String name;
    private String holder = "muso-filter-type muso-filter-type-item";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionalWaitUntilTypeVisibleInSelectionArea.class);

    public ConditionalWaitUntilTypeVisibleInSelectionArea(String name) {
        this.name = StringEscapeUtils.escapeEcmaScript(name);
    }

    @Override
    public Boolean apply(final WebDriver driver) {
        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        WebElement elem = driver.findElement(factory.cssContainingText(holder, name));

        try {
            if (elem.getText().equals(name) && (elem.getAttribute("style").equals("display: block;") || elem.getAttribute("style").isEmpty()))
                if (elem.findElement(By.cssSelector("span")).getAttribute("style").equals("opacity: 1;"))
                    return true;

        } catch (StaleElementReferenceException e) {
            LOGGER.warn("SERE encountered in ConditionElementVisible");
        }

        return null;
    }

}