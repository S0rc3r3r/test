package com.muso.pages.AntiPiracyLinksPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.Table;
import com.muso.pages.InfringementSummaryPage.InfringementSummaryPageBase;
import com.muso.persistence.PersistenceManager;
import com.muso.utils.list.ListUtils;
import com.muso.utils.regexTools.RegExpTools;

public class AntiPiracyLinksPage extends InfringementSummaryPageBase {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AntiPiracyLinksPage.class);
    private final static String REPORT_NAME = "Anti Piracy Links";
    private PersistenceManager persistenceManager;

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

    public AntiPiracyLinksPage(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);

        persistenceManager = PersistenceManager.getInstance();

        checkUIElements();
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

    public boolean isTableHeaderValid(Table table) {
        ArrayList<String> tableHeaders = getTableHeader(table);
        return Table.REMOVAL_DETAILS.areTableHeadersValid(tableHeaders);
    }

    private ArrayList<String> getTableHeader(Table table) {
        ArrayList<String> tableHeader = new ArrayList<>();
        switch (table) {
        case REMOVAL_DETAILS:
            for (WebElement element : antiPiracy_tableHeader.findElements(By.cssSelector("th")))
                tableHeader.add(element.getText());
        default:
            return tableHeader;
        }
    }

    public int getRemovalCount() {
        List<WebElement> tableRows = antiPiracy_tableBody.findElements(By.cssSelector("tr"));
        return tableRows.size();
    }

    public AntiPiracyLinksPage setRowsToDisplay(int rows) {
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

    private void verifyRemovalDetailsTable() {
        LOGGER.debug("Verifying {} table", Table.REMOVAL_DETAILS.getTableName());
        assertTrue(Table.REMOVAL_DETAILS.getTableName() + " table is not displayed on " + REPORT_NAME + " page.", getTableName().equals(
                Table.REMOVAL_DETAILS.getTableName()));
        assertTrue(Table.REMOVAL_DETAILS.getTableName() + " table headers are invalid or incomplete", isTableHeaderValid(Table.REMOVAL_DETAILS));
        // TODO remove the comment when MKV-289 is fixed
        // assertTrue(Table.REMOVAL_DETAILS.getTableName() + " table page size should be 5", 5 == getTablePageSize());
        assertEquals(Table.REMOVAL_DETAILS.getTableName() + " table should show result from page 1\n", "1", getTableDisplayPage());
    }

    private void verifyCampaigns() {
        ArrayList<String> selectedCampaigns = getCampaignSelection();
        assertTrue("The number of selected campaign is incorrect.", persistenceManager.getCampaigns().size() == selectedCampaigns.size());

        if (persistenceManager.getCampaigns().size() > 6) {
            assertEquals("'Multiple campaigns' is not displayed in header", "Multiple campaigns", getCampaignFromHeader());
        } else {
            if (persistenceManager.getCampaigns().size() > 0) {
                for (String expectedCampaign : persistenceManager.getCampaigns()) {
                    boolean isCampaignFound = false;
                    for (String selectedCampaign : selectedCampaigns) {
                        if (expectedCampaign.equals(selectedCampaign)) {
                            isCampaignFound = true;
                            break;
                        }
                    }

                    assertTrue(expectedCampaign + " should be a selected option under Campaign filter", isCampaignFound);
                }
            }
        }
    }

    public void checkUIElements() {
        LOGGER.info("{} report is displayed. Checking UI Elements", REPORT_NAME);

        assertEquals(persistenceManager.getReport().getText() + " is not the default selection for Report.", persistenceManager.getReport().getText(),
                getReportSelection());
        assertEquals(persistenceManager.getDateRange() + " is not the default selection for DateRange.", persistenceManager.getDateRange(),
                getDateRangeSelection());
        assertTrue(persistenceManager.getType() + " is not the default selection for Type.", ListUtils.conpareArrays(getTypeSelection(),
                persistenceManager.getType()));

        assertTrue("MUSO Logo is not displayed in page header", isLogoDisplayed());

        verifyRemovalDetailsTable();
        verifyCampaigns();

    }

}
