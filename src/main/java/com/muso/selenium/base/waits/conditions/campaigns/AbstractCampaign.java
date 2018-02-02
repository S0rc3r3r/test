package com.muso.selenium.base.waits.conditions.campaigns;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

public abstract class AbstractCampaign {

    protected String campaignFilter = "#campaign-picker-container ul.dropdown-menu.inner li";
    protected String campaignItemHolder = "muso-filter-campaign-item span";
    protected String campaignCategoryHolder = "muso-filter-campaign-type-item span";
    protected String campaignName;

    public AbstractCampaign(String campaignName) {
        this.campaignName = campaignName;
    }

    public WebElement getCampaignElementFromFilter(WebDriver driver) {

        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        WebElement elem = driver.findElement(factory.cssContainingText(campaignFilter, campaignName));

        return elem;

    }

    public WebElement getCampaignElementFromItemHolder(WebDriver driver) {

        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        WebElement elem = driver.findElement(factory.cssContainingText(campaignItemHolder, campaignName));

        return elem;

    }

    public WebElement getCampaignElementFromCategoryHolder(WebDriver driver) {

        NgWebDriver ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        ByAngular.Factory factory = ngwd.makeByAngularFactory();

        WebElement elem = driver.findElement(factory.cssContainingText(campaignCategoryHolder, campaignName));

        return elem;

    }

}
