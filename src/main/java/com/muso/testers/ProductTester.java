package com.muso.testers;

import java.util.ArrayList;

import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.MenuType;
import com.muso.enums.ReportType;
import com.muso.persistence.PersistenceManager;
import com.muso.selenium.base.waits.WebDriverWaitManager;
import com.muso.selenium.base.waits.conditions.ExtExpectedConditions;

public class ProductTester extends BaseTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductTester.class);

    public ProductTester(WebDriver driver) {
        super(driver);
    }

    public ArrayList<String> getProductOptions() {

        ArrayList<String> productOptions = infringementSummaryPage.getProductOptions();

        LOGGER.debug("Retrieved Product filter availabe options {}", productOptions.toString());
        return productOptions;
    }

    public void searchForProduct(String optionName) {
        expandProductMenu(true);
        try {
            infringementSummaryPage.searchForProduct(optionName);
        } catch (InvalidElementStateException ex) {
            LOGGER.warn("Search BOX not available on MOBIlE devices. Skipping search");
        }

    }

    public void searchForProductAndSelect(String searchString) {
        expandProductMenu(true);
        infringementSummaryPage.searchForProductAndSelect(searchString);
    }

    public void clearProductSelection() {
        expandProductMenu(true);
        infringementSummaryPage.clearProductSelections();
    }

    public void setProduct(String action, String product) {
        infringementSummaryPage.expandSideBar();

        switch (action) {
        case "remove":
            infringementSummaryPage.collapseAllMenus();
            infringementSummaryPage.removeProduct(product);
            break;
        default:
            expandProductMenu(true);
            infringementSummaryPage.searchForProductAndSelect(product);
            break;
        }
    }

    public ArrayList<String> getProductSelection() {
        expandProductMenu(true);
        return infringementSummaryPage.getProductSelection();
    }

    public int getNumberOfSelectedProducts() {
        expandProductMenu(true);
        return infringementSummaryPage.getProductSelection().size();
    }

    public void expandProductMenu(boolean expand) {
        if (expand) {
            LOGGER.debug("Expanding Product menu filter");
            if (!infringementSummaryPage.isMenuExpanded(MenuType.PRODUCT)) {
                infringementSummaryPage.collapseAllMenus();
                infringementSummaryPage.clickOnProductMenuButton();
            }
        } else {
            LOGGER.debug("Collapsing Product menu filter");
            if (infringementSummaryPage.isMenuExpanded(MenuType.PRODUCT)) {
                infringementSummaryPage.clickOnProductMenuButton();
            }
        }
    }

    public boolean isPorductOptionSelected(String optionName, boolean expectedSelection) {
        expandProductMenu(true);
        boolean isProductSelected = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExtExpectedConditions.selectionOfProductInFilter(optionName, expectedSelection));
        final String isSelectedInFilter = (isProductSelected == true) ? "is selected" : "is not selected";
        LOGGER.debug("Product option:{} {} in filter", optionName, isSelectedInFilter);

        expandProductMenu(false);
        boolean isProductDisplayed = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExtExpectedConditions.visibilityOfProductInSelectionArea(optionName, expectedSelection));
        final String isDisbayedInSelectionArea = (isProductDisplayed == true) ? "is displayed" : "is not displayed";
        LOGGER.debug("Product option: {} {} in selection area", optionName, isDisbayedInSelectionArea);

        return isProductSelected && isProductDisplayed;
    }

    public boolean isFilterEnabled() {
        final boolean isFilterEnabled = infringementSummaryPage.isMenuEnabled(MenuType.PRODUCT);
        final String buttonStatus = (isFilterEnabled == true) ? "enabled" : "disabled";

        LOGGER.debug("Product filter menu is {}", buttonStatus);
        return isFilterEnabled;
    }

    public boolean isProductFilterApplied() {
        ArrayList<String> selectedOptions = getProductSelection();
        return isProductFilterApplied(selectedOptions);
    }

    //PRIVATE METHODS
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

}
