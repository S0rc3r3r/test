package com.muso.testers;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.MenuType;
import com.muso.enums.ReportType;
import com.muso.exceptions.InvalidOptionException;
import com.muso.pages.AntiPiracyLinksPage.AntiPiracyLinksPage;
import com.muso.pages.InfringementSummaryPage.InfringementSummaryPage;
import com.muso.pages.MarketAnalyticsPage.MarketAnalyticsPage;
import com.muso.pages.SubmitInfringementsPage.SubmitInfringementsPage;
import com.muso.selenium.base.waits.WebDriverWaitManager;
import com.muso.selenium.base.waits.conditions.ExtExpectedConditions;

public class ReportTester extends BaseTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportTester.class);

    public ReportTester(WebDriver driver) {
        super(driver);
    }

    public void expandReportMenu(boolean expand) {

        if (expand) {
            LOGGER.debug("Expanding Report menu filter");
            if (!infringementSummaryPage.isMenuExpanded(MenuType.REPORT)) {
                infringementSummaryPage.collapseAllMenus();
                infringementSummaryPage.clickOnReportMenuButton();
            }
        } else {
            LOGGER.debug("Collapsing Report menu filter");
            if (infringementSummaryPage.isMenuExpanded(MenuType.REPORT)) {
                infringementSummaryPage.clickOnReportMenuButton();
            }
        }
    }

    public void setReport(String report) {
        infringementSummaryPage.expandSideBar();
        expandReportMenu(true);
        infringementSummaryPage.setReport(report);

        switch (ReportType.fromString(report)) {
        case Infringement_Summary:
            infringementSummaryPage = new InfringementSummaryPage(driver);
            break;
        case Anti_Piracy_Links:
            antiPiracyLinksPage = new AntiPiracyLinksPage(driver);
            break;
        case Market_Analytics:
            marketAnalyticsPage = new MarketAnalyticsPage(driver);
            break;
        case Submit_Infringements:
            submitInfringementsPage = new SubmitInfringementsPage(driver);
            break;
        default:
            break;
        }
    }

    public boolean isReportOptionSelected(String optionName, boolean expectedSelection) {
        expandReportMenu(true);
        boolean isReportSelected = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExtExpectedConditions.selectionOfReportInFilter(optionName, expectedSelection));
        expandReportMenu(false);
        boolean isReportDisplayed = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExtExpectedConditions.visibilityOfReportInSelectionArea(optionName, expectedSelection));
        if (isReportSelected != isReportDisplayed) {
            LOGGER.error("isReportSelected and isReportDisplayed doesn't have the same value.");
            throw new RuntimeException("something wrong in Reports");
        }
        return isReportSelected && isReportDisplayed;
    }

    public String getReportSelection() {
        return infringementSummaryPage.getReportSelection();
    }

    public ArrayList<String> getReportOptions() {
        return infringementSummaryPage.getReportOptions();
    }

    public void checkReportIsDisplayedCorrectly(String reportName) {
        ReportType report = ReportType.fromString(reportName);

        switch (report) {
        case Anti_Piracy_Links:
            antiPiracyLinksPage.checkUIElements();
            break;
        case Infringement_Summary:
            infringementSummaryPage.checkUIElements();
            break;
        case Market_Analytics:
            marketAnalyticsPage.checkUIElements();
            break;
        case Submit_Infringements:
            submitInfringementsPage.checkUIElements();
            break;
        default:
            throw new InvalidOptionException(reportName + " is not a valid option for Report menu");
        }
    }

}
