package com.muso.selenium.base.waits.conditions.campaigns;

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

public class ConditionWaitUntilCampaignVisibleInSelectionArea implements ExpectedCondition<Boolean> {

    private String campaignName;
    private String campaignItemHolder = "muso-campaign-filter muso-filter-campaign-item span";
    private String campaignCategoryHolder = "muso-campaign-filter muso-filter-campaign-type-item span";
    private final Logger LOGGER = LoggerFactory.getLogger(ConditionWaitUntilCampaignVisibleInSelectionArea.class);
    private WebDriver driver;
    private NgWebDriver ngwd;
    private ByAngular.Factory factory;

    public ConditionWaitUntilCampaignVisibleInSelectionArea(String campaignName) {
        this.campaignName = campaignName;
    }

    @Override
    public Boolean apply(final WebDriver driver) {
        this.driver = driver;

        ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        factory = ngwd.makeByAngularFactory();

        return isCampaigmItemDisplayed() || isCampaigmTypeDisplayed();

    }

    private boolean isCampaigmItemDisplayed() {
        try {
            WebElement elemItem = driver.findElement(factory.cssContainingText(campaignItemHolder, campaignName));
            if (elemItem.isDisplayed() && elemItem.getAttribute("style").equals("opacity: 1;"))
                return true;

        } catch (StaleElementReferenceException | NoSuchElementException e) {
        }

        return false;
    }

    private boolean isCampaigmTypeDisplayed() {
        try {
            WebElement elemCategory = driver.findElement(factory.cssContainingText(campaignCategoryHolder, campaignName));
            if (elemCategory.isDisplayed() && elemCategory.getAttribute("style").equals("opacity: 1;"))
                return true;

        } catch (StaleElementReferenceException | NoSuchElementException e) {
        }

        return false;
    }

}