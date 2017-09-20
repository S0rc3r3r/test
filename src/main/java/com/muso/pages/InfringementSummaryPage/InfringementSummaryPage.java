package com.muso.pages.InfringementSummaryPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
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
import com.muso.persistence.PersistenceManager;
import com.muso.utils.regexTools.RegExpTools;

public class InfringementSummaryPage extends InfringementSummaryPageBase {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AntiPiracyLinksPage.class);
    private final static String REPORT_NAME = ReportType.Anti_Piracy_Links.getText();
    private PersistenceManager persistenceManager;
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

    @FindBy(css = "muso-removals-per-day p")
    protected WebElement removalsPerDayTitle;

    @FindBy(id = "chart_divEvolution")
    protected WebElement removalsPerDayChart;

    // PRODUCT TABLE
    @FindBy(css = "muso-products .row.top-module-space")
    protected WebElement productTable;

    // CAMPAIGN Table
    @FindBy(css = "muso-campaigns .row.top-module-space")
    protected WebElement campaignTable;

    @FindBy(css = "muso-campaigns thead")
    protected WebElement campaignTableHeader;

    @FindBy(css = "muso-campaigns .row.top-module-space tbody")
    protected WebElement campaignTableHolder;

    @FindBy(css = "muso-campaigns span[class='pages']")
    protected WebElement campaignTableDisplayPage;

    @FindBy(css = "muso-campaigns button.btn.dropdown-toggle.pager")
    protected WebElement campaignTableshowNoOfRowsButton;

    @FindBy(css = "muso-campaigns ul.dropdown-menu.inner")
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
    public Double getTotalRemovals() {
        return Double.valueOf(totalRemovals.getText());
    }

    public Double getLastWeekRemovals() {
        return Double.valueOf(lastWeekRemoval.getText());
    }

    public Double getCustomRemovals() {
        return Double.valueOf(customRemoval.getText());
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
        return campaignTableDisplayPage.getText();
    }

    public Integer getTablePageSize() {

        final String pageSizeDisplayedText = campaignTableshowNoOfRowsButton.getText();
        String selectedPageSizeText = "N/A";
        campaignTableshowNoOfRowsButton.click();

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

    private void check_infringement_removals_grouped_by_period() {
        final String dateRangeText = getDateRangeSelection();
        final DateRangeType dateRange = DateRangeType.fromString(dateRangeText);

        switch (dateRange) {
        case LAST_12_MONTHS:
        case LAST_6_MONTHS:
        case LAST_2_MONTHS:
        case LAST_MONTH:
        case LAST_4_WEEKS:
            assertTrue("Custom removal text is not displayed correctly when date range filter is " + dateRangeText, customRemovalPeriodText.getText()
                    .contains(dateRangeText));
        default:
            assertTrue("Total Removals text is not displayed.", totalRemovalsText.getText().contains("Total Removals"));
            assertTrue("Removals Last Week text is not displayed.", lastWeekRemovalText.getText().contains("Removals Last Week"));
        }

    }

    private void check_removal_per_day() {
        assertEquals("Removals Per Day table title is not displayed", "Removals Per Day", removalsPerDayTitle.getText());
        // TODO also check chart
    }

    // PRODUCT TABLE
    private boolean isProductTableDisplayed() {
        if (productTable.getAttribute("style").contains("display: block"))
            return true;
        return false;
    }

    // CAMPAIGN TABLE
    private boolean isCampaignTableDisplayed() {
        if (campaignTable.getAttribute("style").contains("display: block"))
            return true;
        return false;
    }

    private void check_campaign_table_info() {
        if (isCampaignTableDisplayed()) {
            assertEquals(Table.CAMPAIGNS.getTableName() + " table title should be displayed.", "Campaigns", getCampaignTableTitle());
            // TODO Fix this
            assertTrue(Table.CAMPAIGNS.getTableName() + " table header are invalid or incomplete", isTableHeaderValid(Table.CAMPAIGNS));

            // TODO remove the comment when MKV-289 is fixed
            // assertTrue(Table.REMOVAL_DETAILS.getTableName() + " table page size should be 5", 5 ==
            // getTablePageSize());

            // TODO remove this when MKV-302 is fixed
            // assertEquals(Table.CAMPAIGNS.getTableName() + " table should show result from page 1\n", "1",
            // getTableDisplayPage());
        }
    }

    private void check_removals_by_status() {
        // TODO Auto-generated method stub

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

        assertTrue("The sum of all Types should be 100%", totalPercentage == 100);

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

}
