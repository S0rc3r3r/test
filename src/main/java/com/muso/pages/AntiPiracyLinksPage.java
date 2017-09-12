package com.muso.pages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.Table;
import com.muso.persistence.MenuOptions;

public class AntiPiracyLinksPage extends AbstractPage {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AntiPiracyLinksPage.class);
    private final static String REPORT_NAME = "Anti Piracy Links";
    private MenuOptions menuOptions;

    public AntiPiracyLinksPage(WebDriver driver) {
        super(driver);

        PageFactory.initElements(driver, this);

        menuOptions = MenuOptions.getInstance();

        checkUIElements();
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

    @Override
    public void checkUIElements() {
        LOGGER.info("{} report is displayed. Checking UI Elements", REPORT_NAME);
        assertEquals(menuOptions.getReport() + " is not the default selection for Report.", true, isOptionSelected(menuOptions.getReport()));
        assertEquals(menuOptions.getDateRange() + " is not the default selection for DateRange.", true, isOptionSelected(menuOptions.getDateRange()));
        assertEquals(menuOptions.getType() + " is not the default selection for Type.", true, isOptionSelected(menuOptions.getType()));

        for (String campaign : menuOptions.getCampaigns()) {
            assertEquals(campaign + " is not the default selection for Campaign.", true, isOptionSelected(campaign));
        }
        assertTrue("MUSO Logo is not displayed in page header", isLogoDisplayed());

        // Verify Removal Details Table headers , body and footer
        assertEquals("Table displayd on " + REPORT_NAME + " report should be 'Removal Details'", Table.REMOVAL_DETAILS.getTableName(),
                antiPiracy_tableTitle.getText());
        assertTrue(Table.REMOVAL_DETAILS.getTableName() + " table headers are invalid or incomplete", isTableHeaderValid(Table.REMOVAL_DETAILS));

        // TODO remove the comment when MKV-289 is fixed
        // assertTrue("Removal Details' table page size should be 5", 5 == getTablePageSize(Table.REMOVAL_DETAILS));
        assertEquals("'Removal Details' table should show result from page 1\n", "1", String.valueOf(getTableDisplayPage(Table.REMOVAL_DETAILS)));
    }

}
