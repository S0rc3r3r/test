package com.muso.testers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.dataproviders.UserDataProvider;
import com.muso.enums.MenuType;
import com.muso.enums.ReportType;
import com.muso.enums.Table;
import com.muso.enums.TypeType;
import com.muso.exceptions.InvalidOptionException;
import com.muso.models.User;
import com.muso.pages.AntiPiracyLinksPage.AntiPiracyLinksPage;
import com.muso.pages.InfringementSummaryPage.InfringementSummaryPage;
import com.muso.pages.MarketAnalyticsPage.MarketAnalyticsPage;
import com.muso.pages.SubmitInfringementsPage.SubmitInfringementsPage;
import com.muso.persistence.PersistenceManager;
import com.muso.utils.dateTime.DateUtils;

public class DashboardTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardTester.class);
    protected InfringementSummaryPage infringementSummaryPage;
    protected AntiPiracyLinksPage antiPiracyLinksPage;
    protected MarketAnalyticsPage marketAnalyticsPage;
    protected SubmitInfringementsPage submitInfringementsPage;
    protected WebDriver driver;

    public DashboardTester() {

    }

    public void navigateToDashboard(WebDriver driver, String userFile) {
        this.driver = driver;

        final UserDataProvider userDataProvider = new UserDataProvider("/testdata/json/" + userFile + ".json");
        User user = userDataProvider.getData();

        final String application_url = System.getProperty("application_url") + "/?token=" + user.getJwt_token();

        LOGGER.info("Navigate to: " + application_url);
        driver.get(application_url);

        infringementSummaryPage = new InfringementSummaryPage(driver);

        rememberUserCampaign();

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

    public void expandReportMenu() {

        if (!infringementSummaryPage.isMenuExpanded(MenuType.REPORT)) {
            infringementSummaryPage.collapseAllMenus();
            infringementSummaryPage.clickOnReportMenuButton();
        }
    }

    public void expandCampaignMenu() {

        if (!infringementSummaryPage.isMenuExpanded(MenuType.CAMPAIGN)) {
            infringementSummaryPage.collapseAllMenus();
            infringementSummaryPage.clickOnCampaignMenuButton();

        }
    }

    public void expandDateRangeMenu() {

        if (!infringementSummaryPage.isMenuExpanded(MenuType.DATE_RANGE)) {
            infringementSummaryPage.collapseAllMenus();
            infringementSummaryPage.clickOnDateRangeMenuButton();
        }
    }

    public void expandTypeMenu() {

        if (!infringementSummaryPage.isMenuExpanded(MenuType.TYPE)) {
            infringementSummaryPage.collapseAllMenus();
            infringementSummaryPage.clickOnTypeMenuButton();
        }
    }

    public void expandProductMenu() {

        if (!infringementSummaryPage.isMenuExpanded(MenuType.PRODUCT)) {
            infringementSummaryPage.collapseAllMenus();
            infringementSummaryPage.clickOnProductMenuButton();
        }
    }

    public void expandRegionMenu() {

        if (!marketAnalyticsPage.isMenuExpanded(MenuType.REGION)) {
            marketAnalyticsPage.collapseAllMenus();
            marketAnalyticsPage.clickOnRegionButton();
        }
    }

    public void expandPiracyCategoryMenu() {

        if (!marketAnalyticsPage.isMenuExpanded(MenuType.CATEGORY)) {
            marketAnalyticsPage.collapseAllMenus();
            marketAnalyticsPage.clickOnPiracyCategoryButton();
        }
    }

    public void setReport(String report) {
        expandReportMenu();
        infringementSummaryPage.setReport(report);

        switch (ReportType.fromString(report)) {
        case Infringement_Summary:
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

    public void setRegion(String optionName) {
        marketAnalyticsPage.setRegion(optionName);
    }

    public void setPiracyCategory(String optionName) {
        marketAnalyticsPage.setPiracyCategory(optionName);
    }

    public void searchForRegionAndSelect(String optionName) {
        marketAnalyticsPage.searchForRegionAndSelect(optionName);
    }

    public ArrayList<String> getRegionSelection() {
        return marketAnalyticsPage.getRegionSelection();
    }

    public ArrayList<String> getPiracyCategorySelection() {
        return marketAnalyticsPage.getPiracyCategorySelection();
    }

    public void searchForCampaignAndSelect(String optionName) {
        infringementSummaryPage.searchForCampaignAndSelect(optionName);
    }

    public void setCampaign(String campaign) {
        expandCampaignMenu();
        infringementSummaryPage.setCampaign(campaign);
    }

    public void setDateRange(String dateRange) {
        expandDateRangeMenu();
        infringementSummaryPage.setDateRange(dateRange);
    }

    public void setProduct(String product) {
        expandProductMenu();
        infringementSummaryPage.setProduct(product);
    }

    public void setType(String type) {
        expandTypeMenu();
        infringementSummaryPage.setType(type);
    }

    public String getReportSelection() {
        return infringementSummaryPage.getReportSelection();
    }

    public ArrayList<String> getReportOptions() {
        return infringementSummaryPage.getReportOptions();
    }

    public ArrayList<String> getCampaignSelection() {
        return infringementSummaryPage.getCampaignFilterSelectedOptions();
    }

    public String getDateRangeSelection() {
        return infringementSummaryPage.getDateRangeSelection();
    }

    public ArrayList<String> getDateRangeOptions() {
        return infringementSummaryPage.getDateRangeOptions();
    }

    public ArrayList<String> getProductSelection() {
        return infringementSummaryPage.getProductSelection();
    }

    public ArrayList<String> getTypeSelection() {
        return infringementSummaryPage.getTypeSelection();
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

    private void rememberUserCampaign() {
        expandCampaignMenu();
        MenuType.CAMPAIGN.setMenuOptions(infringementSummaryPage.getCampaignOptions());
    }

    public boolean isFilterApplied(MenuType menu) {

        ArrayList<String> selectedOptions = new ArrayList<String>();
        Date dateFilter = DateUtils.getInstance().getDateNow();

        switch (menu) {
        case DATE_RANGE:
            selectedOptions.add(getDateRangeSelection());

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
                throw new InvalidArgumentException(selectedOptions.get(0) + " was not found or is invalid option for Date Range menu.");
            }

            return isDateRangeFilterApplied(dateFilter);

        case CAMPAIGN:
            selectedOptions = getCampaignSelection();
            return isCampaignFilterApplied(selectedOptions);
        case PRODUCT:
            selectedOptions = getProductSelection();
            return isProductFilterApplied(selectedOptions);
        case TYPE:
            selectedOptions = getTypeSelection();
            return isTypeFilterApplied(selectedOptions);
        default:
            return false;

        }
    }

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

    private boolean isCampaignFilterApplied(ArrayList<String> filter) {

        ReportType reportType = PersistenceManager.getInstance().getReport();

        switch (reportType) {
        case Infringement_Summary:
            break;
        case Anti_Piracy_Links:
            return antiPiracyLinksPage.isCampaignFilterApplied(filter);
        case Market_Analytics:
            break;
        case Submit_Infringements:
            break;
        default:
            break;
        }
        return false;
    }

    private boolean isProductFilterApplied(ArrayList<String> filter) {

        ReportType reportType = PersistenceManager.getInstance().getReport();

        switch (reportType) {
        case Infringement_Summary:
            break;
        case Anti_Piracy_Links:
            return antiPiracyLinksPage.isProductFilterApplied(filter);
        case Market_Analytics:
            break;
        case Submit_Infringements:
            break;
        default:
            break;
        }
        return false;
    }

    private boolean isTypeFilterApplied(ArrayList<String> filter) {

        ReportType reportType = PersistenceManager.getInstance().getReport();

        switch (reportType) {
        case Infringement_Summary:
            break;
        case Anti_Piracy_Links:
            return antiPiracyLinksPage.isTypeFilterApplied(filter);
        case Market_Analytics:
            break;
        case Submit_Infringements:
            break;
        default:
            break;
        }
        return false;
    }

    public Long getCampaignInfringements(String campaignName, String period) {
        return infringementSummaryPage.getInfringementsByCampaignNameAndPeriod(campaignName, period);
    }

    public Long getProductInfringements(String productName, String period) {
        return infringementSummaryPage.getInfringementsByProductNameAndPeriod(productName, period);
    }

    public boolean isOptionSelected(String optionName) {

        List<String> options;

        MenuType menu = MenuType.getMenuFromOption(optionName);

        switch (menu) {
        case REPORT:
            expandReportMenu();
            options = Arrays.asList(infringementSummaryPage.getReportSelection());
            break;
        case DATE_RANGE:
            expandDateRangeMenu();
            options = Arrays.asList(infringementSummaryPage.getDateRangeSelection());
            break;
        case TYPE:
            expandTypeMenu();
            options = infringementSummaryPage.getTypeSelection();
            break;
        case PRODUCT:
            expandProductMenu();
            throw new InvalidArgumentException("NOT IMPLEMENTED");
        case CAMPAIGN:
            expandCampaignMenu();
            options = infringementSummaryPage.getCampaignFilterSelectedOptions();
            break;
        default:
            throw new InvalidArgumentException("NOT IMPLEMENTED");
        }

        if (!options.contains(optionName)) {
            LOGGER.warn("{} is not selected for {} filter. Current selection is: ", optionName, menu.getName(), options.toString());
            return false;
        }

        return true;
    }

    public boolean isCategoryElementsDisabled(String categoryName) {

        MenuType menu = MenuType.getMenuFromOption(categoryName);

        switch (menu) {
        case TYPE:
            expandTypeMenu();
            return false;

        case CAMPAIGN:
            expandCampaignMenu();
            return infringementSummaryPage.areCampaignsFromCategoryDisabled(categoryName);

        default:
            throw new InvalidArgumentException("NOT IMPLEMENTED");
        }

    }
}
