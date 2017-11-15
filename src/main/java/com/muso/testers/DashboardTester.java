package com.muso.testers;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.Table;
import com.muso.enums.TypeType;

public class DashboardTester extends BaseTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardTester.class);

    public DashboardTester(WebDriver driver) {
        super(driver);
    }

    public void clickOnSupportButton() {
        infringementSummaryPage.clickOnSupportButton();
    }

    public void clickOnAccountButton() {
        infringementSummaryPage.clickOnAccountButton();
    }

    public void clickOnLogoutButton() {
        infringementSummaryPage.clickOnLogoutButton();
    }

    public String getPageTitle() {
        return infringementSummaryPage.getPageTitle();
    }

    public boolean isCounterDisplayed(String counterName) {
        return infringementSummaryPage.isCounterDisplayed(counterName);
    }

    public int getInfringementsTotalRemovals() {
        return infringementSummaryPage.getTotalRemovals();
    }

    public int getInfringementsLastWeekRemovals() {
        return infringementSummaryPage.getLastWeekRemovals();
    }

    public int getInfringementsCustomRemovals() {
        return infringementSummaryPage.getCustomRemovals();
    }

    public void setRemovalDetailRowsToDisplay(int rowsToDisplay) {
        antiPiracyLinksPage.setRowsToDisplay(rowsToDisplay);

    }

    public int getTablePages(Table table) {
        switch (table) {
        case REMOVAL_DETAILS:
            return antiPiracyLinksPage.getPages();
        }
        return 0;
    }

    public int getTableRows(Table table) {
        switch (table) {
        case REMOVAL_DETAILS:
            return antiPiracyLinksPage.getTableRows();
        }
        return 0;
    }

    public HashMap<String, Double> getRemovalsByType() {
        return infringementSummaryPage.gtRemovalsByType();

    }

    public HashMap<String, Double> getRemovalsByStatus() {
        return infringementSummaryPage.gtRemovalsByStatus();

    }

    public Double getRemovalByTypeValue(TypeType type) {
        HashMap<String, Double> removalsByTypeMap = getRemovalsByType();

        for (Map.Entry<String, Double> entry : removalsByTypeMap.entrySet()) {
            if (entry.getKey().equals(type.getText())) {
                return entry.getValue();
            }
        }
        return -1.00;
    }

    public Double getRemovalByStatusValue(String status) {
        HashMap<String, Double> removalsByTypeMap = getRemovalsByStatus();

        for (Map.Entry<String, Double> entry : removalsByTypeMap.entrySet()) {
            if (entry.getKey().equals(status)) {
                return entry.getValue();
            }
        }
        return -1.00;
    }

    public Long getCampaignInfringements(String campaignName, String period) {
        return infringementSummaryPage.getInfringementsByCampaignNameAndPeriod(campaignName, period);
    }

    public Long getProductInfringements(String productName, String period) {
        return infringementSummaryPage.getInfringementsByProductNameAndPeriod(productName, period);
    }

}
