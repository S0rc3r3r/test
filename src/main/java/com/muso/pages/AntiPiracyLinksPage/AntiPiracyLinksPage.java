package com.muso.pages.AntiPiracyLinksPage;

import java.util.ArrayList;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.ReportType;
import com.muso.pages.General.RemovalDetailsFrame;
import com.muso.pages.InfringementSummaryPage.InfringementSummaryPageBase;

public class AntiPiracyLinksPage extends InfringementSummaryPageBase {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AntiPiracyLinksPage.class);
    private final static String REPORT_NAME = ReportType.Anti_Piracy_Links.getText();
    private RemovalDetailsFrame removalDetailsFrame;

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

        removalDetailsFrame = new RemovalDetailsFrame(driver);

        checkUIElements();
    }

    @Override
    public void checkUIElements() {
        LOGGER.info("{} report is displayed. Checking UI Elements", REPORT_NAME);
        super.checkUIElements();

        removalDetailsFrame.verifyRemovalDetailsTable();
    }

    public AntiPiracyLinksPage setRowsToDisplay(int rows) {
        removalDetailsFrame.setRowsToDisplay(rows);
        return this;
    }

    public int getPages() {
        return removalDetailsFrame.getPages();
    }

    public int getTableRows() {
        return removalDetailsFrame.getTableRows();
    }

    public boolean isDateRangeFilterApplied(Date dateFilter) {
        return removalDetailsFrame.isDateRangeFilterApplied(dateFilter);
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

}
