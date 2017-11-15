package com.muso.pages.InfringementSummaryPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.DateRangeType;
import com.muso.enums.ReportType;
import com.muso.enums.Table;
import com.muso.exceptions.InvalidCampaignTableColumnException;
import com.muso.exceptions.InvalidProductTableColumnException;
import com.muso.exceptions.MissingCampaignException;
import com.muso.exceptions.MissingProductException;
import com.muso.pages.AntiPiracyLinksPage.AntiPiracyLinksPage;
import com.muso.pages.General.RemovalDetailsFrame;
import com.muso.utils.regexTools.RegExpTools;

public class InfringementSummaryPage extends InfringementSummaryPageBase {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AntiPiracyLinksPage.class);
    private final static String REPORT_NAME = ReportType.Anti_Piracy_Links.getText();

    private RemovalDetailsFrame removalDetailsFrame;
    private WebElement totalRemovalsText;
    private WebElement lastWeekRemovalText;
    private WebElement customRemovalPeriodText;

    @FindBy(id = "counterAllTime")
    protected WebElement totalRemovals;

    @FindBy(id = "counterLastWeek")
    protected WebElement lastWeekRemoval;

    @FindBy(id = "counterCustomPeriod")
    protected WebElement customRemoval;

    @FindBy(css = "muso-line-chart-per-day p")
    protected WebElement removalsPerDayTitle;

    @FindBy(id = "chart_divEvolution")
    protected WebElement removalsPerDayChart;

    // PRODUCT TABLE
    @FindBy(css = "muso-products .row.top-module-space")
    protected WebElement productTable;

    // CAMPAIGN Table
    @FindBy(css = "muso-campaigns-data-table div.row")
    protected WebElement campaignTable;

    @FindBy(css = "muso-campaigns-data-table thead")
    protected WebElement campaignTableHeader;

    @FindBy(css = "muso-campaigns-data-table tbody")
    protected WebElement campaignTableHolder;

    @FindBy(css = "muso-campaigns-data-table span[class='pages']")
    protected WebElement campaignTableDisplayPage;

    @FindBy(css = "muso-campaigns-data-table button.btn.dropdown-toggle.pager")
    protected WebElement campaignTableshowNoOfRowsButton;

    @FindBy(css = "div#data-table-page-size-picker-container ul.dropdown-menu.inner")
    protected WebElement campaignTableshowNoOfRowsOptions;

    // REMOVALS BY STATUS / TYPE
    @FindBy(css = "muso-infringements-by-high-level-type-chart muso-pie-chart-legend svg > g > g")
    protected List<WebElement> removalsByTypeHolder;

    @FindBy(css = "muso-removals-by-status-and-high-level-type muso-infringements-per-status-chart svg > g > g")
    protected List<WebElement> removalsByStatusHolder;

    public InfringementSummaryPage(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);

        totalRemovalsText = totalRemovals.findElement(By.xpath(".."));
        lastWeekRemovalText = lastWeekRemoval.findElement(By.xpath(".."));
        customRemovalPeriodText = customRemoval.findElement(By.xpath(".."));

        removalDetailsFrame = new RemovalDetailsFrame(driver);
    }

    // HEADER ACTIONS
    public int getTotalRemovals() {
        return Integer.valueOf(totalRemovals.getText().replaceAll(",", ""));
    }

    public int getLastWeekRemovals() {
        return Integer.valueOf(lastWeekRemoval.getText().replaceAll(",", ""));
    }

    public int getCustomRemovals() {
        return Integer.valueOf(customRemoval.getText().replaceAll(",", ""));
    }

    public boolean isCounterDisplayed(String counterName) {

        boolean isCounterDisplayed = false;
        try {
            final WebElement counterElement = driver.findElement(factory.cssContainingText("div.col-sm-6", counterName));
            if (counterName.equals(counterElement.getText()))
                isCounterDisplayed = true;
        } catch (NoSuchElementException ex) {

        }

        if (isCounterDisplayed) {
            LOGGER.debug("{} counter is dispayed", counterName);
        } else {
            LOGGER.warn("{} counter is not dispayed", counterName);
        }

        return isCounterDisplayed;
    }

    // CAMPAIGN TABLE ACTIONS
    public String getCampaignTableTitle() {
        return campaignTable.findElement(By.cssSelector("p")).getText();
    }

    public boolean isTableHeaderValid(Table table) {
        ArrayList<String> tableHeaders = new ArrayList<String>();

        switch (table) {
        case CAMPAIGNS:
            tableHeaders = getTableHeader(table);
            return Table.CAMPAIGNS.areTableHeadersValid(tableHeaders);
        case REMOVAL_DETAILS:
            tableHeaders = getTableHeader(table);
            return Table.CAMPAIGNS.areTableHeadersValid(tableHeaders);
        default:
            return false;
        }

    }

    public String getTableDisplayPage() {
        return campaignTableDisplayPage.getText().trim();
    }

    public Integer getTablePageSize() {

        campaignTableshowNoOfRowsButton.click();

        final String pageSizeDisplayedText = campaignTableshowNoOfRowsButton.getText();
        String selectedPageSizeText = "N/A";

        for (WebElement pageSizeElement : campaignTableshowNoOfRowsOptions.findElements(By.cssSelector("li"))) {
            if (pageSizeElement.getAttribute("class").equals("selected")) {
                selectedPageSizeText = pageSizeElement.getText();
                break;
            }
        }
        final String pageSize = RegExpTools.regExpExtractor(pageSizeDisplayedText, "(\\d+)");
        assertEquals(pageSize + " should be the selected option from pageSize.", pageSize, selectedPageSizeText);
        return Integer.valueOf(pageSize);
    }

    @Override
    public void checkUIElements() {
        LOGGER.info("{} report is displayed. Checking UI Elements", REPORT_NAME);
        super.checkUIElements();

        check_infringement_removals_grouped_by_period();

        check_removal_per_day();

        check_campaign_table_info();

        check_removals_by_status();

        check_removals_by_type();

        removalDetailsFrame.verifyRemovalDetailsTable();
    }

    public HashMap<String, Double> gtRemovalsByType() {
        LOGGER.debug("Retrieving Removals By Type from Chart");

        return getRemovalsMapFromElement(removalsByTypeHolder);
    }

    public HashMap<String, Double> gtRemovalsByStatus() {
        LOGGER.debug("Retrieving Removals By Status from Chart");

        return getRemovalsMapFromElement(removalsByStatusHolder);
    }

    private ArrayList<String> getTableHeader(Table table) {
        ArrayList<String> tableHeader = new ArrayList<>();

        switch (table) {
        case CAMPAIGNS:
            for (WebElement element : campaignTableHeader.findElements(By.cssSelector("th")))
                tableHeader.add(element.getText());
            LOGGER.debug("Returning Campaign table headers {}", tableHeader.toString());

            return tableHeader;
        case REMOVAL_DETAILS:
            return removalDetailsFrame.getTableHeader();

        default:
            return tableHeader;
        }

    }

    // PRODUCT TABLE
    private boolean isProductTableDisplayed() {
        if (productTable.getAttribute("style").contains("display: block"))
            return true;
        return false;
    }

    // CAMPAIGN TABLE
    private boolean isCampaignTableDisplayed() {
        if (campaignTable.getAttribute("style").contains("opacity: 1"))
            return true;
        return false;
    }

    private boolean isChartDisplayed(WebElement chart) {
        if (chart.getAttribute("style").contains("opacity: 1"))
            return true;
        return false;
    }

    private void check_infringement_removals_grouped_by_period() {
        final String dateRangeText = getDateRangeSelection();
        final DateRangeType dateRange = DateRangeType.fromString(dateRangeText);

        switch (dateRange) {
        case LAST_12_MONTHS:
        case LAST_6_MONTHS:
        case LAST_2_MONTHS:
        case LAST_MONTH:
        case LAST_4_WEEKS:
            assertTrue("Custom removal text is not displayed correctly when date range filter is " + dateRangeText, customRemovalPeriodText.getText().contains(dateRangeText));
        default:
            assertTrue("Total Removals text is not displayed.", totalRemovalsText.getText().contains("Total Removals"));
            assertTrue("Removals Last Week text is not displayed.", lastWeekRemovalText.getText().contains("Removals Last Week"));
        }

    }

    private void check_removal_per_day() {
        assertEquals("Removals Per Day table title is not displayed", "Removals Per Day", removalsPerDayTitle.getText());
        // TODO also check chart
    }

    private void check_campaign_table_info() {
        if (isCampaignTableDisplayed()) {
            assertEquals(Table.CAMPAIGNS.getTableName() + " table title should be displayed.", "Campaigns", getCampaignTableTitle());

            assertTrue(Table.CAMPAIGNS.getTableName() + " table header are invalid or incomplete", isTableHeaderValid(Table.CAMPAIGNS));

            assertTrue(Table.REMOVAL_DETAILS.getTableName() + " table page size should be 5", 5 == getTablePageSize());

            assertEquals(Table.CAMPAIGNS.getTableName() + " table should show result from page 1\n", "1", getTableDisplayPage());
        } else {
            throw new ElementNotVisibleException("Campaigns table is not displayed");
        }
    }

    private void check_removals_by_status() {
        List<WebElement> status = driver.findElements(factory.cssContainingText("p", "Removals By Status"));
        assertTrue("Removals by Status text is not displayed", !status.isEmpty());
        assertTrue("Removals by Status graph is not displayed", isChartDisplayed(driver.findElement(By.cssSelector("muso-removals-by-status-and-high-level-type > div"))));
    }

    private void check_removals_by_type() {
        List<WebElement> status = driver.findElements(factory.cssContainingText("p", "Removals By Type"));
        assertTrue("Removals by Status is not displayed", !status.isEmpty());
        assertTrue("Removals by Status graph is not displayed", isChartDisplayed(driver.findElement(By.cssSelector("muso-infringements-by-high-level-type-chart > div"))));
    }

    private HashMap<String, Double> getRemovalsMapFromElement(List<WebElement> holder) {

        double totalPercentage = 0;
        HashMap<String, Double> removalsByStatusMap = new HashMap<String, Double>();

        for (WebElement element : holder) {
            final String percentageString = element.findElement(By.cssSelector("g:nth-child(1)")).getText().replaceAll("%", "");
            final String typeName = element.findElement(By.cssSelector("g:nth-child(3)")).getText();

            LOGGER.debug("Found {} type with {} percentage", typeName, percentageString);

            final Double typePercentage = Double.parseDouble(percentageString);

            removalsByStatusMap.put(typeName, typePercentage);
            totalPercentage += typePercentage;

        }

        assertEquals("The sum of all Types should be 100%", totalPercentage, 100.0, 0);

        return removalsByStatusMap;
    }

    public Long getInfringementsByCampaignNameAndPeriod(String campaignName, String period) {

        if (!isCampaignTableDisplayed()) {
            LOGGER.warn("Campaign table is not displayed");
            throw new MissingCampaignException("Campaign table is not displayed");
        }

        final int campaignColumnIndex = Table.CAMPAIGNS.getTableColumns().indexOf("Campaign");
        final int columnIndex = Table.CAMPAIGNS.getTableColumns().indexOf(period);

        if (columnIndex == -1) {
            throw new InvalidCampaignTableColumnException(period + " column is not displayed in Campaign table");
        }

        List<WebElement> tableRows = campaignTableHolder.findElements(By.cssSelector("tr"));

        for (WebElement row : tableRows) {
            String campaign = row.findElement(By.cssSelector("td:nth-child(" + (campaignColumnIndex + 1) + ")")).getText();
            if (campaign.equals(campaignName)) {
                String infringements = row.findElement(By.cssSelector("td:nth-child(" + (columnIndex + 1) + ")")).getText();
                LOGGER.debug("Found '{}' with {} during {} period", campaign, infringements, period);
                return Long.parseLong(infringements.replaceAll(",", ""));
            }
        }

        throw new MissingCampaignException("Campaign '" + campaignName + "' could not be found in Campaign table");
    }

    public Long getInfringementsByProductNameAndPeriod(String productnName, String period) {

        if (!isProductTableDisplayed()) {
            LOGGER.warn("Product table is not displayed");
            throw new MissingProductException("Product table is not displayed");
        }

        final int campaignColumnIndex = Table.PRODUCTS.getTableColumns().indexOf("Product");
        final int columnIndex = Table.PRODUCTS.getTableColumns().indexOf(period);

        if (columnIndex == -1) {
            throw new InvalidProductTableColumnException(period + " column is not displayed in Campaign table");
        }

        List<WebElement> tableRows = campaignOptionsHolder.findElements(By.cssSelector("tr"));

        for (WebElement row : tableRows) {
            String product = row.findElement(By.cssSelector("td:nth-child(" + (campaignColumnIndex + 1) + ")")).getText();
            if (product.equals(productnName)) {
                String infringements = row.findElement(By.cssSelector("td:nth-child(" + (columnIndex + 1) + ")")).getText();
                LOGGER.debug("Found '{}' with {} during {} period", product, infringements, period);
                return Long.parseLong(infringements);
            }
        }

        throw new MissingProductException("Product '" + productnName + "' could not be found in Product table");
    }

    public boolean isDateRangeFilterApplied(Date dateFilter) {

        final String dateRange = persistenceManager.getDateRange();

        if (dateRange.equals(DateRangeType.ALL_TIME.getText()) || dateRange.equals(DateRangeType.LAST_WEEK.getText())) {
            if (isCounterDisplayed(dateRange)) {
                LOGGER.warn("{} should not be displayed", dateRange);
                return false;
            }
        } else {
            isCounterDisplayed(dateRange);
        }

        if (removalDetailsFrame.isDateRangeFilterApplied(dateFilter)) {
            if (getTableHeader(Table.CAMPAIGNS).contains(dateRange))
                return true;
        }

        return false;
    }

    public boolean isCampaignFilterApplied(ArrayList<String> campaigns) {
        return removalDetailsFrame.isCampaignFilterApplied(campaigns);
    }

    public boolean isProductFilterApplied(ArrayList<String> products) {
        return removalDetailsFrame.isProductFilterApplied(products);
    }

    public boolean isTypeFilterApplied(ArrayList<String> types) {
        return removalDetailsFrame.isTypeFilterApplied(types);
    }

    public boolean isMembersFilterApplied(String filter) {
        removalDetailsFrame.isMembersFilterApplied(filter);
        return false;
    }

    public boolean areCampaignsFromCategoryDisabled(String categoryName) {

        List<WebElement> categoryElements = campaignOptionsHolder.findElements(By.cssSelector("li a.dropdown-header"));

        boolean areElementsDisabled = false;
        int startIndex = 0;
        int endIndex = 0;

        for (int i = 0; i < categoryElements.size(); i++) {
            WebElement parentElement = categoryElements.get(i).findElement(By.xpath(".."));
            if (parentElement.getText().equals(categoryName)) {
                startIndex = Integer.valueOf(parentElement.getAttribute("data-original-index")) + 1;
                if (i == categoryElements.size() - 1) {
                    endIndex = campaignOptionsHolder.findElements(By.cssSelector("li")).size() - 1;
                } else {
                    final WebElement nextCategory = categoryElements.get(i + 1).findElement(By.xpath(".."));
                    endIndex = Integer.valueOf(nextCategory.getAttribute("data-original-index"));
                }
                break;
            }
        }

        List<WebElement> categoryItems = campaignOptionsHolder.findElements(By.cssSelector("li"));

        for (int i = startIndex; i < endIndex; i++) {
            if (!categoryItems.get(i).getAttribute("class").equals("disabled")) {
                LOGGER.error("{} should be disabled when {} category is selected", categoryItems.get(i).getText(), categoryName);
                areElementsDisabled = false;
                break;
            }
            areElementsDisabled = true;
        }
        return areElementsDisabled;
    }

    public boolean areTypesFromCategoryDisabled(String categoryName) {
        List<WebElement> categoryElements = typeOptionsHolder.findElements(By.cssSelector("li a.dropdown-header"));

        boolean areElementsDisabled = false;
        int startIndex = 0;
        int endIndex = 0;

        for (int i = 0; i < categoryElements.size(); i++) {
            WebElement parentElement = categoryElements.get(i).findElement(By.xpath(".."));
            if (parentElement.getText().equals(categoryName)) {
                startIndex = Integer.valueOf(parentElement.getAttribute("data-original-index")) + 1;
                if (i == categoryElements.size() - 1) {
                    endIndex = campaignOptionsHolder.findElements(By.cssSelector("li")).size() - 1;
                } else {
                    final WebElement nextCategory = categoryElements.get(i + 1).findElement(By.xpath(".."));
                    endIndex = Integer.valueOf(nextCategory.getAttribute("data-original-index"));
                }
                break;
            }
        }

        List<WebElement> categoryItems = typeOptionsHolder.findElements(By.cssSelector("li"));

        for (int i = startIndex; i < endIndex; i++) {
            if (!categoryItems.get(i).getAttribute("class").equals("disabled")) {
                LOGGER.error("{} should be disabled when {} category is selected", categoryItems.get(i).getText(), categoryName);
                areElementsDisabled = false;
                break;
            }
            areElementsDisabled = true;
        }

        return areElementsDisabled;
    }

}
