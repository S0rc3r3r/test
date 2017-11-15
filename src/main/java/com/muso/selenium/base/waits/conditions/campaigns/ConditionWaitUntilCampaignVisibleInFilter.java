
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

public class ConditionWaitUntilCampaignVisibleInFilter implements ExpectedCondition<WebElement> {

    private String campaignName;
    private String campaignHolder = "muso-campaign-filter ul.dropdown-menu.inner li";
    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilCampaignSelected.class);

    public ConditionWaitUntilCampaignVisibleInFilter(String campaignName) {
        this.campaignName = campaignName;
    }

    @Override
    public WebElement apply(final WebDriver driver) {
        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        WebElement elem;

        elem = driver.findElement(factory.cssContainingText(campaignHolder, campaignName));

        try {
            if (elem.isDisplayed())
                return elem;
        } catch (StaleElementReferenceException e) {
            LOGGER.warn("SERE encountered in ConditionElementVisible");
        }

        return null;
    }

}