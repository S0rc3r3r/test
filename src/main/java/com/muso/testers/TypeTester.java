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

public class TypeTester extends BaseTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(TypeTester.class);

    public TypeTester(WebDriver driver) {
        super(driver);
    }

    public void searchForTypeAndSelect(String optionName) {
        expandTypeMenu(true);
        infringementSummaryPage.searchForTypeAndSelect(optionName);
    }

    public void searchForType(String optionName) {
        expandTypeMenu(true);
        try {
            infringementSummaryPage.searchForType(optionName);
        } catch (InvalidElementStateException ex) {
            LOGGER.warn("Search BOX not available. Selecting {} type from list", optionName);
        }
    }

    public void expandTypeMenu(boolean expand) {
        infringementSummaryPage.expandSideBar();

        if (expand) {
            LOGGER.debug("Expanding Type menu filter");
            if (!infringementSummaryPage.isMenuExpanded(MenuType.TYPE)) {
                infringementSummaryPage.collapseAllMenus();
                infringementSummaryPage.clickOnTypeMenuButton();
            }
        } else {
            LOGGER.debug("Collapsing Type menu filter");
            if (infringementSummaryPage.isMenuExpanded(MenuType.TYPE)) {
                infringementSummaryPage.clickOnTypeMenuButton();
            }
        }
    }

    public void setType(String action, String type) {
        infringementSummaryPage.expandSideBar();

        switch (action) {
        case "remove":
            infringementSummaryPage.collapseAllMenus();
            infringementSummaryPage.removeType(type);
            break;
        default:
            expandTypeMenu(true);
            infringementSummaryPage.searchForTypeAndSelect(type);
            break;
        }
    }

    public ArrayList<String> getTypeSelection() {
        return infringementSummaryPage.getTypeSelection();
    }

    public ArrayList<String> getTypeOptions() {
        ArrayList<String> typeOptions = infringementSummaryPage.getTypeOptions();
        LOGGER.debug("Retrieved Type filter availabe options {}", typeOptions.toString());

        return typeOptions;
    }

    public boolean isTypeOptionSelected(String optionName, boolean expectedSelection) {
        expandTypeMenu(true);
        boolean isTypeSelected = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExtExpectedConditions.selectionOfTypeInFilter(optionName, expectedSelection));
        final String isSelectedInFilter = (isTypeSelected == true) ? "is selected" : "is not selected";
        LOGGER.debug("Type option:{} {} in filter", optionName, isSelectedInFilter);

        expandTypeMenu(false);
        boolean isTypeDisplayed = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExtExpectedConditions.visibilityOfTypeInSelectionArea(optionName, expectedSelection));
        final String isDisbayedInSelectionArea = (isTypeDisplayed == true) ? "is displayed" : "is not displayed";
        LOGGER.debug("Type option:{} {} in selection area", optionName, isDisbayedInSelectionArea);

        return isTypeSelected && isTypeDisplayed;
    }

    public boolean isTypeCategoryElementsDisabled(String categoryName) {
        expandTypeMenu(true);
        return infringementSummaryPage.areTypesFromCategoryDisabled(categoryName);
    }

    public void clearTypeSelection() {
        expandTypeMenu(true);
        infringementSummaryPage.clearTypeSelections();
    }

    public boolean isTypeFilterApplied() {
        ArrayList<String> selectedOptions = getTypeSelection();
        return isTypeFilterApplied(selectedOptions);
    }

    //PRIVATE METHODS
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

}
