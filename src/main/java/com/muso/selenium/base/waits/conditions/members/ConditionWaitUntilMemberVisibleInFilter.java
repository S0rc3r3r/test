package com.muso.selenium.base.waits.conditions.members;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.selenium.base.waits.conditions.campaigns.ConditionWaitUntilCampaignSelected;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class ConditionWaitUntilMemberVisibleInFilter implements ExpectedCondition<WebElement> {

    private String name;
    private String holder = "muso-filter-user ul.dropdown-menu.inner";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilCampaignSelected.class);

    public ConditionWaitUntilMemberVisibleInFilter(String memberName) {
        this.name = StringEscapeUtils.escapeEcmaScript(name);
    }

    @Override
    public WebElement apply(final WebDriver driver) {
        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        WebElement elem;

        elem = driver.findElement(factory.cssContainingText(holder, name));

        try {
            if (elem.isDisplayed())
                return elem;
        } catch (StaleElementReferenceException e) {
            LOGGER.warn("SERE encountered in ConditionElementVisible");
        }

        return null;
    }

}