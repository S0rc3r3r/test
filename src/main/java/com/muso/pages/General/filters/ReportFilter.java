package com.muso.pages.General.filters;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.ReportType;
import com.muso.persistence.PersistenceManager;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

public class ReportFilter {

    @FindBy(css = "button[title='Report']")
    protected WebElement ReportMenuButton;

    @FindBy(css = "muso-report-filter ul.dropdown-menu.inner")
    protected WebElement reportOptionsHolder;

    @FindBy(css = "muso-report-filter div.selectedOptions")
    protected WebElement reportSelectionArea;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportFilter.class);
    private ByAngular.Factory factory;
    private PersistenceManager persistenceManager;
    private NgWebDriver ngwd;
    private WebDriver driver;

    public ReportFilter(WebDriver driver) {
        PageFactory.initElements(driver, this);

        ngwd = new NgWebDriver((JavascriptExecutor) driver).withRootSelector("app-root");
        ngwd.waitForAngularRequestsToFinish();

        factory = ngwd.makeByAngularFactory();
        persistenceManager = PersistenceManager.getInstance();
    }

    public ReportFilter getReportOptions() {

        return this;
    }

    public ReportFilter expandReport() {

        return this;
    }

    public boolean isMenuExpanded() {
        if (Boolean.valueOf(reportOptionsHolder.getAttribute("aria-expanded"))) {
            return true;
        }
        return false;
    }

    /**
     * @param reportName
     *            set Report option
     */
    public void setReport(String reportName) {
        LOGGER.info("Click on Report option {}", reportName);

        WebElement reportElement = driver.findElement(factory.cssContainingText("span.text", reportName));
        reportElement.click();
        persistenceManager.setReport(ReportType.fromString(reportName));
    }

    /**
     * @param reportName
     * @return true if reportName is selected and displayed under Report Filter
     *         false if reportName is not selected or displayed under Report FIlter
     */
    public boolean isReportSelected(String reportName) {
        if (reportName.equals(getSelectedOption())) {
            if (reportName.equals(getDisplayedReport())) {
                return true;
            } else {
                LOGGER.info("{} is not displayed under Report filter", reportName);
                return false;
            }
        }

        LOGGER.info("{} is not selected under Report filter", reportName);
        return false;
    }

    /**
     * @return the selected report name displayed when Report Filter is expanded
     */
    private String getSelectedOption() {
        WebElement menuSelection = null;

        try {
            menuSelection = reportOptionsHolder.findElement(By.className("selected"));
        } catch (NoSuchElementException ex) {
            LOGGER.warn("Report filter has no option selected");
            return "N/A";
        }

        LOGGER.debug("Report filter has the following options selected: {}", menuSelection.getText());

        return menuSelection.getText();
    }

    /**
     * @return the report name displayed when Report Filter is collapsed
     */
    private String getDisplayedReport() {

        return reportSelectionArea.getText();

    }

}
