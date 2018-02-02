package com.muso.selenium.base.waits.conditions.campaigns;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ConditionWaitUntilCampaignVisibleInSelectionArea extends AbstractCampaign implements ExpectedCondition<Boolean> {

    //private final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilCampaignVisibleInSelectionArea.class);
    private WebDriver driver;

    public ConditionWaitUntilCampaignVisibleInSelectionArea(String campaignName) {
        super(campaignName);
    }

    @Override
    public Boolean apply(final WebDriver driver) {
        this.driver = driver;

        return isCampaigmItemDisplayed() || isCampaigmTypeDisplayed();

    }

    private boolean isCampaigmItemDisplayed() {
        try {
            WebElement elemItem = getCampaignElementFromItemHolder(driver);

            if (elemItem.isDisplayed() && elemItem.getAttribute("style").equals("opacity: 1;"))
                return true;

        } catch (StaleElementReferenceException | NoSuchElementException e) {
        }

        return false;
    }

    private boolean isCampaigmTypeDisplayed() {
        try {
            WebElement elemCategory = getCampaignElementFromCategoryHolder(driver);

            if (elemCategory.isDisplayed() && elemCategory.getAttribute("style").equals("opacity: 1;"))
                return true;

        } catch (StaleElementReferenceException | NoSuchElementException e) {
        }

        return false;
    }

}