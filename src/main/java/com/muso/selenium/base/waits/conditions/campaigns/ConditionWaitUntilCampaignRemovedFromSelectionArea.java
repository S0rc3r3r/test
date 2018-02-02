package com.muso.selenium.base.waits.conditions.campaigns;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionWaitUntilCampaignRemovedFromSelectionArea extends AbstractCampaign implements ExpectedCondition<Boolean> {

    private final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilCampaignSelected.class);

    public ConditionWaitUntilCampaignRemovedFromSelectionArea(String campaignName) {
        super(campaignName);
    }

    @Override
    public Boolean apply(final WebDriver driver) {

        return isCampaigmItemRemoved(driver) || isCampaigmTypeRemoved(driver);

    }

    private Boolean isCampaigmItemRemoved(WebDriver driver) {

        try {
            WebElement elemItem = getCampaignElementFromItemHolder(driver);

            if (elemItem.isDisplayed() && elemItem.getAttribute("style").equals("opacity: 1;"))
                return false;

        } catch (StaleElementReferenceException | NoSuchElementException e) {
            LOGGER.debug("{} was not found in Campaign selection area", campaignName);
            return true;
        }

        return null;
    }

    private Boolean isCampaigmTypeRemoved(WebDriver driver) {

        try {
            WebElement elemCategory = getCampaignElementFromCategoryHolder(driver);
            if (elemCategory.isDisplayed() && elemCategory.getAttribute("style").equals("opacity: 1;"))
                return false;

        } catch (StaleElementReferenceException | NoSuchElementException e) {
            LOGGER.debug("{} was not found in Campaign selection area", campaignName);
            return true;
        }

        return null;
    }

}