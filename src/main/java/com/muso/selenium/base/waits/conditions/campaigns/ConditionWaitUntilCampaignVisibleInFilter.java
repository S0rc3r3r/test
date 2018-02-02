
package com.muso.selenium.base.waits.conditions.campaigns;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionWaitUntilCampaignVisibleInFilter extends AbstractCampaign implements ExpectedCondition<WebElement> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilCampaignSelected.class);

    public ConditionWaitUntilCampaignVisibleInFilter(String campaignName) {
        super(campaignName);
    }

    @Override
    public WebElement apply(final WebDriver driver) {

        WebElement elem = getCampaignElementFromFilter(driver);

        try {
            if (elem.isDisplayed())
                return elem;
        } catch (StaleElementReferenceException e) {
            LOGGER.warn("SERE encountered in ConditionElementVisible");
        }

        return null;
    }

}