package com.muso.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.ReportType;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class DashboardPage {

    @FindBy(id = "header")
    protected WebElement headerElement;

    @FindBy(css = "h2.allCampaigns")
    protected WebElement campaignInfo;

    @FindBy(css = "h2.dateRange")
    protected WebElement dateRangeInfo;

    @FindBy(css = "button[title='Report']")
    protected WebElement reportButton;

    @FindBy(css = "button[title='Date range']")
    protected WebElement dateRangeButton;

    @FindBy(css = "button[title='Campaign']")
    protected WebElement campaignButton;

    @FindBy(css = "button[title='Type']")
    protected WebElement typeButton;

    @FindBy(css = "button[title='Report'] + div[class='dropdown-menu open']")
    protected WebElement reportOptionsHolder;

    @FindBy(css = "#period-container > div")
    protected WebElement dateRangeHolder;

    @FindBy(css = "#selectedPeriod.selectedOptions")
    protected WebElement dateRangeSelection;

    @FindBy(css = "div.selectedOptions")
    protected WebElement reportSelection;

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardPage.class);
    private static final String DASHBOARD_URL = "http://st-dashboard.muso.com.s3-website-us-east-1.amazonaws.com/?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOjg1LCJjcmVhdGVkIjoxNDkxMzc2Mjc3NTg2LCJleHAiOjE1MjI5MTYyNzd9.Hrjq0SqgyMWwhXuTNVHxecsUs20kOwUKPJd_kvvl3SewVyKh8hyC0eRX5ubrE48gCAiPbDVmpre5ccXu3U-96A";
    protected WebDriver driver;
    protected ByAngular.Factory factory;
    public NgWebDriver ngwd;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;

        navigateTo();
        PageFactory.initElements(driver, this);

        ngwd.waitForAngularRequestsToFinish();
    }

    public void waitForAngularToLoad() {
        ngwd.waitForAngularRequestsToFinish();
    }

    private void navigateTo() {
        driver.get(DASHBOARD_URL);
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

        ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        factory = ngwd.makeByAngularFactory();
    }

    public void setReportType(ReportType reportType) {

        WebElement reportElement = driver.findElement(factory.cssContainingText("span.text", reportType.getText()));
        reportElement.click();
    }

    public DashboardPage clickOnReportButton() {
        reportButton.click();
        ngwd.waitForAngularRequestsToFinish();
        //ThreadHandler.sleep(1000);//todo fix this
        return this;
    }

    public int getAvailableOptions(String type) {

        switch (type) {
            case "Report":
                List<WebElement> reportElements = reportOptionsHolder.findElements(By.cssSelector("li"));
                return reportElements.size();
            case "Date Range":
                List<WebElement> dateRangeElements = dateRangeHolder.findElements(By.cssSelector("li"));
                return dateRangeElements.size();
            default:
                throw new InvalidArgumentException("Unknown option: " + type);
        }

    }

    public DashboardPage clickOnCampaignButton() {
        campaignButton.click();
        ngwd.waitForAngularRequestsToFinish();

        return this;
    }

    public DashboardPage clickOnDateRangeButton() {
        dateRangeButton.click();
        ngwd.waitForAngularRequestsToFinish();
        return this;
    }

    public DashboardPage clickOnTypeButton() {
        typeButton.click();
        ngwd.waitForAngularRequestsToFinish();
        return this;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void setDateRange() {

    }

    public void setCampaign() {

    }

    public void setType() {

    }

    public boolean isOptionDisplayed(String option, String activePage) {

        boolean isOptionDisplayed = false;
        List<WebElement> options;

        switch (activePage) {
            case "Report":
                options = reportOptionsHolder.findElements(By.cssSelector("li"));
                break;
            case "Date Range":
                options = dateRangeHolder.findElements(By.cssSelector("li"));
                break;

            default:
                throw new InvalidArgumentException("Unknown option: " + activePage);
        }

        for (WebElement optionName : options) {
            if (optionName.getText().equals(option))
                isOptionDisplayed = true;
        }

        return isOptionDisplayed;

    }

    @Deprecated
    public boolean isOptionSelected2(String option, String activePage) {

        boolean isOptionSelected = false;
        List<WebElement> options;

        switch (activePage) {
            case "Report":
                options = reportOptionsHolder.findElements(By.cssSelector("li"));
                break;
            case "Date Range":
                options = dateRangeHolder.findElements(By.cssSelector("li"));
                break;

            default:
                throw new InvalidArgumentException("Unknown option: " + activePage);
        }

        for (WebElement optionName : options) {
            LOGGER.info("Element {}, attribute {}", optionName.getText(), optionName.getAttribute("class"));
            if (optionName.getText().equals(option) && optionName.getAttribute("class").equals("selected"))
                isOptionSelected = true;
            break;
        }

        return isOptionSelected;
    }

    public boolean isOptionSelected(String option, String activePage) {

        boolean isOptionSelected = false;

        switch (activePage) {
            case "Report":
                if (reportSelection.getText().equals(option))
                    isOptionSelected = true;
                break;
            case "Date Range":
                if (dateRangeSelection.getText().equals(option))
                    isOptionSelected = true;
                break;

            default:
                throw new InvalidArgumentException("Unknown option: " + activePage);
        }

        return isOptionSelected;
    }
}
