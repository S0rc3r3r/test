package com.muso.selenium.base.waits.conditions.campaigns;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class ConditionWaitUntilCampaignSelected implements ExpectedCondition<Boolean> {

    private String campaignName;
    private String campaignFilter = "muso-campaign-filter ul.dropdown-menu.inner li";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilCampaignSelected.class);

    public ConditionWaitUntilCampaignSelected(String campaignName) {
        this.campaignName = campaignName;
    }

    @Override
    public Boolean apply(final WebDriver driver) {
        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        WebElement elem = driver.findElement(factory.cssContainingText(campaignFilter, campaignName));

        try {
            if (elem.isDisplayed() && (elem.getAttribute("class").equals("selected") || elem.getAttribute("class").equals("selected active")))
                return true;
        } catch (StaleElementReferenceException e) {
            LOGGER.warn("SERE encountered in ConditionElementVisible");
        }

        return null;
    }

}
