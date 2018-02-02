package com.muso.selenium.base.waits.conditions.campaigns;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionWaitUntilCampaignSelected extends AbstractCampaign implements ExpectedCondition<Boolean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilCampaignSelected.class);

    public ConditionWaitUntilCampaignSelected(String campaignName) {
        super(campaignName);
    }

    @Override
    public Boolean apply(final WebDriver driver) {

        WebElement elem = getCampaignElementFromFilter(driver);

        try {
            if (elem.isDisplayed() && (elem.getAttribute("class").equals("selected") || elem.getAttribute("class").equals("selected active")))
                return true;
        } catch (StaleElementReferenceException e) {
            LOGGER.warn("SERE encountered in ConditionElementVisible");
        }

        return null;
    }

}
