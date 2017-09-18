package com.muso.pages.General;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

public abstract class AbstractBasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBasePage.class);

    // HEADERS
    @FindBy(css = "#header h4")
    protected WebElement reportInfo;

    @FindBy(css = "h2.allCampaigns")
    protected WebElement campaignInfo;

    @FindBy(css = "h2.dateRange")
    protected WebElement dateRangeInfo;

    @FindBy(css = "#header img")
    protected WebElement logoImg;

    protected ByAngular.Factory factory;
    protected NgWebDriver ngwd;
    protected WebDriver driver;

    protected AbstractBasePage(WebDriver driver, String applicationURL) {

        navigateTo(applicationURL);

        PageFactory.initElements(driver, this);
    }

    protected AbstractBasePage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);

        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

        ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        factory = ngwd.makeByAngularFactory();
    }

    private void navigateTo(final String dashBoard_Url) {
        LOGGER.info("Navigate to {}", dashBoard_Url);

        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        driver.get(dashBoard_Url);

        ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        factory = ngwd.makeByAngularFactory();
    }

    public String getDateRangeFromHeader() {
        LOGGER.debug("Date Range displayed in header: {}", dateRangeInfo.getText());
        return dateRangeInfo.getText();
    }

    public String getCampaignFromHeader() {
        LOGGER.debug("Campaign displayed in header: {}", campaignInfo.getText());
        return campaignInfo.getText();
    }

    public String getReportfromHeader() {
        LOGGER.debug("Report displayed in header: {}", reportInfo.getText());
        return reportInfo.getText();
    }

    public boolean isLogoDisplayed() {
        if (logoImg.getAttribute("src").contains("assets/images/logo.png"))
            return true;
        return false;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

}
