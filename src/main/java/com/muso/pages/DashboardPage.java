package com.muso.pages;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.MenuType;
import com.muso.persistence.MenuOptions;
import com.muso.utils.dateTime.DateUtils;

public class DashboardPage extends AbstractPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardPage.class);
    private MenuOptions menuOptions;

    public DashboardPage(WebDriver driver, String user) {
        super(driver, user);

        PageFactory.initElements(driver, this);

        updateCampaigns();

        menuOptions = MenuOptions.getInstance();

    }

    public void waitForAngularToLoad() {
        ngwd.waitForAngularRequestsToFinish();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public int getTotalRemovals() {
        return Integer.valueOf(totalRemovals.getText());
    }

    public int getLastWeekRemovals() {
        return Integer.valueOf(lastWeekRemoval.getText());
    }

    public int getCustomRemovals() {
        return Integer.valueOf(customRemoval.getText());
    }

    @Override
    public void checkUIElements() {
        LOGGER.info("Verifying Menu Options persistance.");
        assertEquals(menuOptions.getReport() + " is not the default selection for Report.", true, isOptionSelected(menuOptions.getReport()));
        assertEquals(menuOptions.getDateRange() + " is not the default selection for DateRange.", true, isOptionSelected(menuOptions.getDateRange()));
        assertEquals(menuOptions.getType() + " is not the default selection for Type.", true, isOptionSelected(menuOptions.getType()));
        for (String campaign : menuOptions.getCampaigns()) {
            assertEquals(campaign + " is not the default selection for Campaign.", true, isOptionSelected(campaign));
        }

        // assertEquals(menuOptions.getProduct().toString() + " is not the default selection for DateRange.", true,
        // isOptionSelected(menuOptions
        // .getProduct().toString()));

    }

    public boolean isFilterApplied(MenuType menu, String tableName) {

        List<String> selectedOptions = new ArrayList<String>();

        List<String> tableValues = new ArrayList<String>();
        Date dateFilter = DateUtils.getInstance().getDateNow();

        switch (menu) {
        case DATE_RANGE:
            selectedOptions.add(getMenuSelectedOption(menu));

            switch (selectedOptions.get(0)) {
            case "All Time":
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
                break;
            default:
                throw new InvalidArgumentException(selectedOptions.get(0) + " was not found or is invalid option for Date Range menu.");
            }

        case CAMPAIGN:
            selectedOptions = getMenuSelectedOptions(menu);
            break;
        case PRODUCT:
            break;
        case TYPE:
            break;
        default:
            break;

        }

        switch (tableName) {
        case "Removal Details":

            switch (menu) {
            case DATE_RANGE:
                tableValues = getTableValues("Takedown sent");
                for (String tableValue : tableValues) {
                    Date tableDate = DateUtils.getInstance().dateFromString(DateUtils.STD_EU_FORMAT, tableValue);
                    if (dateFilter.after(tableDate) || (tableDate.after(DateUtils.getInstance().getDateNow()))) {
                        LOGGER.warn(tableDate.toString() + " is not in the Date Range filter range");
                        return false;
                    }
                }

            case CAMPAIGN:
                tableValues = getTableValues("Campaign");
                for (String tableValue : tableValues) {
                    if (!selectedOptions.contains(tableValue)) {
                        LOGGER.warn(tableValue + " campaign name is not valid with the current campaign filter: " + selectedOptions);
                        return false;
                    }

                }
                break;
            case PRODUCT:
                break;
            case TYPE:
                break;
            default:
                break;
            }

            break;
        default:
            throw new InvalidArgumentException(tableName + " was not found.");
        }

        return true;

    }

    // PRIVATE METHODS
    private void updateCampaigns() {
        MenuType.CAMPAIGN.setOptions(getMenuAvailableOptions(MenuType.CAMPAIGN));
    }

}
