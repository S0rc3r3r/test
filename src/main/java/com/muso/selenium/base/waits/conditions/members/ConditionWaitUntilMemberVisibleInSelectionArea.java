package com.muso.selenium.base.waits.conditions.members;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.pages.General.PageLocators;
import com.muso.selenium.base.waits.conditions.campaigns.ConditionWaitUntilCampaignSelected;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class ConditionWaitUntilMemberVisibleInSelectionArea extends PageLocators implements ExpectedCondition<Boolean> {

    private String name;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilCampaignSelected.class);

    public ConditionWaitUntilMemberVisibleInSelectionArea(String name) {
        this.name = StringEscapeUtils.escapeEcmaScript(name);
    }

    @Override
    public Boolean apply(final WebDriver driver) {
        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        WebElement elem = driver.findElement(factory.cssContainingText(memberSelectedItem_CSS, name));

        try {
            if (elem.isDisplayed() && elem.getText().equals(name))
                return true;
        } catch (StaleElementReferenceException e) {
            LOGGER.warn("SERE encountered in ConditionElementVisible");
        }

        return null;
    }

}