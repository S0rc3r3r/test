package com.muso.pages.General;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.ReportType;
import com.muso.enums.Table;
import com.muso.utils.dateTime.DateUtils;
import com.muso.utils.regexTools.RegExpTools;

public class RemovalDetailsFrame {

    protected static final Logger LOGGER = LoggerFactory.getLogger(RemovalDetailsFrame.class);
    private final static String REPORT_NAME = ReportType.Anti_Piracy_Links.getText();
    private WebDriver driver;

    // ANTI PIRACY LINKS
    @FindBy(css = "muso-infringements p")
    protected WebElement antiPiracy_tableTitle;

    @FindBy(css = "muso-infringements thead")
    protected WebElement antiPiracy_tableHeader;

    @FindBy(css = "muso-infringements tbody")
    protected WebElement antiPiracy_tableBody;

    @FindBy(css = "muso-infringements button.btn.dropdown-toggle.pager")
    protected WebElement antiPiracy_showNoOfRowsButton;

    @FindBy(css = "muso-infringements ul.dropdown-menu.inner")
    protected WebElement antiPiracy_showNoOfRowsOptions;

    @FindBy(css = "muso-infringements span.caret.caret-right")
    protected WebElement antiPiracy_nextPageButton;

    @FindBy(css = "muso-infringements span.caret.caret-left")
    protected WebElement antiPiracy_previousPageButton;

    @FindBy(css = "muso-infringements span[class='pages']")
    protected WebElement antiPiracy_currentPage;

    public RemovalDetailsFrame(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    public boolean isDateRangeFilterApplied(Date dateFilter) {
        List<String> tableValues = new ArrayList<String>();

        tableValues = getTableValues("Takedown sent");
        for (String tableValue : tableValues) {
            DateUtils.getInstance();
            Date tableDate = DateUtils.dateFromString(tableValue);
            if (dateFilter.after(tableDate) || (tableDate.after(DateUtils.getInstance().getDateNow()))) {
                LOGGER.warn(tableValue + " is not in the Date Range filter range");
                return false;
            }
        }
        return true;
    }

    public boolean isCampaignFilterApplied(ArrayList<String> campaigns) {
        List<String> tableValues = new ArrayList<String>();

        tableValues = getTableValues("Campaign");
        for (String tableValue : tableValues) {
            if (!campaigns.contains(tableValue)) {
                LOGGER.warn("{} campaign is displayed in Removal Details table but filter is {}", tableValue, campaigns.toString());
                return false;
            }
        }
        return true;
    }

    public boolean isProductFilterApplied(ArrayList<String> products) {
        List<String> tableValues = new ArrayList<String>();

        tableValues = getTableValues("Product");
        for (String tableValue : tableValues) {
            if (!products.contains(tableValue)) {
                LOGGER.warn("{} product is displayed in Removal Details table but filter is {}", tableValue, products.toString());
                return false;
            }
        }
        return true;
    }

    public boolean isTypeFilterApplied(ArrayList<String> types) {
        List<String> tableValues = new ArrayList<String>();

        tableValues = getTableValues("Type");
        for (String tableValue : tableValues) {
            if (!types.contains(tableValue)) {
                LOGGER.warn("{} type is displayed in Removal Details table but filter is {}", tableValue, types.toString());
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> getTableValues(String columnName) {
        final int columnIndex = Table.REMOVAL_DETAILS.getTableColumns().indexOf(columnName);

        ArrayList<String> columnValues = new ArrayList<String>();

        List<WebElement> tableRows = antiPiracy_tableBody.findElements(By.cssSelector("tr"));

        for (WebElement row : tableRows) {
            String columnValue = row.findElement(By.cssSelector("td:nth-child(" + (columnIndex + 1) + ")")).getText();
            LOGGER.debug("Found '{}' in {} from {} table", columnValue, columnName, Table.REMOVAL_DETAILS.getTableName());
            columnValues.add(columnValue);
        }

        return columnValues;

    }

    public String getTableName() {
        return antiPiracy_tableTitle.getText();
    }

    public int getTableRows() {
        return antiPiracy_tableBody.findElements(By.cssSelector("tr")).size();
    }

    public Integer getTablePageSize() {

        final String pageSizeDisplayedText = antiPiracy_showNoOfRowsButton.getText();
        String selectedPageSizeText = "N/A";
        antiPiracy_showNoOfRowsButton.click();

        for (WebElement pageSizeElement : antiPiracy_showNoOfRowsOptions.findElements(By.cssSelector("li"))) {
            if (pageSizeElement.getAttribute("class").equals("selected")) {
                selectedPageSizeText = pageSizeElement.getText();
                break;
            }
        }
        final String pageSize = RegExpTools.regExpExtractor(pageSizeDisplayedText, "(\\d+)");
        assertEquals(pageSize + " should be the selected option from pageSize.", pageSize, selectedPageSizeText);
        return Integer.valueOf(pageSize);
    }

    public String getTableDisplayPage() {
        return antiPiracy_currentPage.getText();
    }

    public int getPages() {
        return (driver.findElements(By.className("pages"))).size();
    }

    public boolean isTableHeaderValid() {
        ArrayList<String> tableHeaders = getTableHeader();
        return Table.REMOVAL_DETAILS.areTableHeadersValid(tableHeaders);
    }

    public ArrayList<String> getTableHeader() {
        ArrayList<String> tableHeader = new ArrayList<>();

        for (WebElement element : antiPiracy_tableHeader.findElements(By.cssSelector("th")))
            tableHeader.add(element.getText());

        LOGGER.debug("Returning Removal Details table headers {}", tableHeader.toString());
        return tableHeader;

    }

    public int getRemovalCount() {
        List<WebElement> tableRows = antiPiracy_tableBody.findElements(By.cssSelector("tr"));
        return tableRows.size();
    }

    public RemovalDetailsFrame setRowsToDisplay(int rows) {
        if (!isMenuExpanded(antiPiracy_showNoOfRowsButton)) {
            antiPiracy_showNoOfRowsButton.click();
        }
        setRowsToDisplayOption(rows);
        return this;
    }

    // PRIVATE METHODS
    private void setRowsToDisplayOption(int rows) {

        List<WebElement> rowsToDisplayOptions = antiPiracy_showNoOfRowsOptions.findElements(By.cssSelector("li"));

        for (WebElement option : rowsToDisplayOptions) {
            if (option.getText().equals(String.valueOf(rows))) {
                option.click();
            }
        }
    }

    public void verifyRemovalDetailsTable() {
        LOGGER.debug("Verifying {} table", Table.REMOVAL_DETAILS.getTableName());
        assertTrue(Table.REMOVAL_DETAILS.getTableName() + " table is not displayed on " + REPORT_NAME + " page.", getTableName().equals(
                Table.REMOVAL_DETAILS.getTableName()));
        assertTrue(Table.REMOVAL_DETAILS.getTableName() + " table headers are invalid or incomplete", isTableHeaderValid());
        // TODO remove the comment when MKV-289 is fixed
        // assertTrue(Table.REMOVAL_DETAILS.getTableName() + " table page size should be 5", 5 == getTablePageSize());
        assertEquals(Table.REMOVAL_DETAILS.getTableName() + " table should show result from page 1\n", "1", getTableDisplayPage());
    }

    private boolean isMenuExpanded(WebElement menuElement) {

        if (Boolean.valueOf(menuElement.getAttribute("aria-expanded")) || Boolean.valueOf(menuElement.getAttribute("style").contains(
                "display: block"))) {
            return true;
        }
        return false;
    }

}
