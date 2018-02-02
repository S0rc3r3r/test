package com.muso.pages.General;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.persistence.PersistenceManager;
import com.muso.selenium.base.waits.WebDriverWaitManager;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

public abstract class AbstractBasePage extends PageLocators {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBasePage.class);

    // HEADERS
    @FindBy(css = reportHeaderElement_CSS)
    protected WebElement reportHeaderElement;

    @FindBy(css = campaignHeaderElement_CSS)
    protected WebElement campaignHeaderElement;

    @FindBy(css = dateRangeHeaderElement_CSS)
    protected WebElement dateRangeHeaderElement;

    @FindBy(css = logoHeaderElement_CSS)
    protected WebElement logoHeaderElement;

    @FindBy(css = Sidebar_CSS)
    protected WebElement SidebarMenu;

    @FindBy(id = ToggleButton_ID)
    protected WebElement ToggleButton;

    protected ByAngular.Factory factory;
    protected PersistenceManager persistenceManager;
    protected NgWebDriver ngwd;
    protected WebDriver driver;

    protected AbstractBasePage(WebDriver driver, String applicationURL) {

        navigateTo(applicationURL);

        PageFactory.initElements(driver, this);
    }

    protected AbstractBasePage(WebDriver driver) {

        this.driver = driver;
        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("header")));

        PageFactory.initElements(driver, this);

        ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        factory = ngwd.makeByAngularFactory();
        persistenceManager = PersistenceManager.getInstance();

        WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExpectedConditions.attributeToBe(By.cssSelector("muso-filter-type > li"), "style", "opacity: 1;"));
        //WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExpectedConditions.attributeToBe(By.cssSelector("muso-period-filter > li"), "style", "opacity: 1;"));
        //WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExpectedConditions.attributeToBe(By.cssSelector("muso-campaign-filter > li"), "style", "opacity: 1;"));
        //WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExpectedConditions.attributeToBe(By.cssSelector("muso-report-filter > li"), "style", "opacity: 1;"));
        //WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExpectedConditions.attributeToBe(By.cssSelector("muso-product-filter > li"), "style", "opacity: 1;"));
    }

    private void navigateTo(final String dashBoard_Url) {
        LOGGER.info("Navigate to {}", dashBoard_Url);

        driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
        driver.get(dashBoard_Url);

        ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        factory = ngwd.makeByAngularFactory();
        persistenceManager = PersistenceManager.getInstance();
    }

    public String getDateRangeFromHeader() {
        LOGGER.debug("Date Range displayed in header: {}", dateRangeHeaderElement.getText());
        return dateRangeHeaderElement.getText();
    }

    public String getCampaignFromHeader() {

        Actions action = new Actions(driver);
        action.sendKeys(Keys.HOME);
        action.build().perform();

        WebDriverWaitManager.getInstance().explicitShortWaitUntil(driver, ExpectedConditions.visibilityOf(campaignHeaderElement));

        LOGGER.debug("Campaign displayed in header: {}", campaignHeaderElement.getText());
        return campaignHeaderElement.getText();
    }

    public String getReportfromHeader() {
        LOGGER.debug("Report displayed in header: {}", reportHeaderElement.getText());
        return reportHeaderElement.getText();
    }

    public boolean isLogoDisplayed() {
        if (logoHeaderElement.getAttribute("src").contains("assets/images/logo.png"))
            return true;
        return false;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public void checkUIElements() {
        // Verify MUSO LOGO is displayed
        assertTrue("MUSO Logo is not displayed in page header", isLogoDisplayed());
    }

}
