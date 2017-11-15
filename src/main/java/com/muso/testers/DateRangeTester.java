package com.muso.testers;

import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.MenuType;
import com.muso.enums.ReportType;
import com.muso.persistence.PersistenceManager;
import com.muso.selenium.base.waits.WebDriverWaitManager;
import com.muso.selenium.base.waits.conditions.ExtExpectedConditions;
import com.muso.utils.dateTime.DateUtils;

public class DateRangeTester extends BaseTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateRangeTester.class);

    public DateRangeTester(WebDriver driver) {
        super(driver);
    }

    public void expandDateRangeMenu(boolean expand) {
        if (expand) {
            if (!infringementSummaryPage.isMenuExpanded(MenuType.DATE_RANGE)) {
                infringementSummaryPage.collapseAllMenus();
                infringementSummaryPage.clickOnDateRangeMenuButton();
            }
        } else {
            if (infringementSummaryPage.isMenuExpanded(MenuType.DATE_RANGE)) {
                infringementSummaryPage.clickOnDateRangeMenuButton();
            }
        }
    }

    public void setDateRange(String dateRange) {
        expandDateRangeMenu(true);
        infringementSummaryPage.setDateRange(dateRange);
    }

    public String getDateRangeSelection() {
        return infringementSummaryPage.getDateRangeSelection();
    }

    public ArrayList<String> getDateRangeOptions() {
        return infringementSummaryPage.getDateRangeOptions();
    }

    public boolean isDateRangeOptionSelected(String optionName, boolean expectedSelection) {
        expandDateRangeMenu(true);
        boolean isDateSelected = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExtExpectedConditions.selectionOfDateRangeInFilter(optionName, expectedSelection));
        expandDateRangeMenu(false);
        boolean isDateDisplayed = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExtExpectedConditions.visibilityOfDateRangeInSelectionArea(optionName, expectedSelection));
        if (isDateSelected != isDateDisplayed) {
            LOGGER.error("isDateSelected and isDateDisplayed doesn't have the same value.");
            throw new RuntimeException("something wrong in dateRange");
        }
        return isDateSelected && isDateDisplayed;
    }

    public boolean isDateRangeFilterApplied() {
        Date dateFilter = DateUtils.getInstance().getDateNow();
        String selectedOption = getDateRangeSelection();

        switch (getDateRangeSelection()) {
        case "All Time":
            dateFilter = DateUtils.getMonthsInPast(600);
            break;
        case "Last 12 Months":
            dateFilter = DateUtils.getMonthsInPast(12);
            break;
        case "Last 6 Months":
            dateFilter = DateUtils.getMonthsInPast(6);
            break;
        case "Last 2 Months":
            dateFilter = DateUtils.getMonthsInPast(2);
            break;
        case "Last Month":
            dateFilter = DateUtils.getMonthsInPast(1);
            break;
        case "Last 4 Weeks":
            dateFilter = DateUtils.getWeeksInPast(4);
            break;
        case "Last Week":
            dateFilter = DateUtils.getWeeksInPast(1);
            break;
        case "Custom Range":
        default:
            throw new InvalidArgumentException(selectedOption + " was not found or is invalid option for Date Range menu.");
        }

        return isDateRangeFilterApplied(dateFilter);
    }

    //PRIVATE METHODS
    private boolean isDateRangeFilterApplied(Date filter) {

        ReportType reportType = PersistenceManager.getInstance().getReport();

        switch (reportType) {
        case Infringement_Summary:
            return infringementSummaryPage.isDateRangeFilterApplied(filter);
        case Anti_Piracy_Links:
            return antiPiracyLinksPage.isDateRangeFilterApplied(filter);
        case Market_Analytics:
            break;
        case Submit_Infringements:
            break;
        default:
            break;
        }

        return false;

    }

}
