package com.muso.testers;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.MenuType;
import com.muso.enums.ReportType;
import com.muso.persistence.PersistenceManager;
import com.muso.selenium.base.waits.WebDriverWaitManager;
import com.muso.selenium.base.waits.conditions.ExtExpectedConditions;

public class CampaignTester extends BaseTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampaignTester.class);

    public CampaignTester(WebDriver driver) {
        super(driver);
    }

    public void searchForCampaignAndSelect(String optionName) {
        expandCampaignMenu(true);
        infringementSummaryPage.searchForCampaignAndSelect(optionName);
    }

    public void searchForCampaign(String optionName) {
        expandCampaignMenu(true);
        infringementSummaryPage.searchForCampaign(optionName);
    }

    public void expandCampaignMenu(boolean expand) {

        if (expand) {
            LOGGER.debug("Expanding Campaign menu filter");
            if (!infringementSummaryPage.isMenuExpanded(MenuType.CAMPAIGN)) {
                infringementSummaryPage.collapseAllMenus();
                infringementSummaryPage.clickOnCampaignMenuButton();
            }
        } else {
            LOGGER.debug("Collapsing Campaign menu filter");
            if (infringementSummaryPage.isMenuExpanded(MenuType.CAMPAIGN)) {
                infringementSummaryPage.clickOnCampaignMenuButton();
            }
        }
    }

    public int getNumberOfSelectedCampaigns() {
        LOGGER.debug("Returning number of selected campaigns");
        return infringementSummaryPage.getCampaignSelectionNumber();
    }

    public void setCampaign(String action, String campaign) {

        switch (action) {
        case "remove":
            infringementSummaryPage.collapseAllMenus();
            infringementSummaryPage.removeCampaign(campaign);
            break;
        default:
            expandCampaignMenu(true);
            infringementSummaryPage.setCampaign(campaign);
            break;
        }

    }

    public boolean isCampaignOptionSelected(String optionName, boolean expectedSelection) {
        LOGGER.debug("Verifying Campaign: {} selection", optionName);

        expandCampaignMenu(true);
        final boolean isCampaignSelected = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExtExpectedConditions.selectionOfCampaignInFilter(optionName, expectedSelection));
        final String isSelectedInFilter = (isCampaignSelected == true) ? "is selected" : "is not selected";
        LOGGER.debug("Campaign option: {} {} in filter", optionName, isSelectedInFilter);

        expandCampaignMenu(false);
        final boolean isCampaignDisplayed = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExtExpectedConditions.visibilityOfCampaignInSelectionArea(optionName, expectedSelection));
        final String isDisbayedInSelectionArea = (isCampaignDisplayed == true) ? "is displayed" : "is not displayed";
        LOGGER.debug("Campaign option: {} {} in selection area", optionName, isDisbayedInSelectionArea);

        return isCampaignSelected && isCampaignDisplayed;
    }

    public boolean isCampaignDisplayedInHeader(String optionName) {
        LOGGER.debug("Verifying that campaign {} is displayed in header", optionName);

        final boolean isDisplayed = infringementSummaryPage.isCampaignDisplayedInHeader(optionName);
        final String isDisplayedInHeader = (isDisplayed == true) ? "is displayed" : "is not displayed";
        LOGGER.debug("Campaign {} {} in header", optionName, isDisplayedInHeader);

        return isDisplayed;
    }

    public boolean isCampaignCategoryElementsDisabled(String categoryName) {
        return isCategoryElementsDisabled(categoryName);
    }

    public ArrayList<String> getCampaignOptions() {

        ArrayList<String> campaignOptions = infringementSummaryPage.getCampaignOptions();
        LOGGER.debug("Retrieved Campaign filter availabe options {}", campaignOptions.toString());

        return campaignOptions;
    }

    public void clearCampaignSelection() {
        infringementSummaryPage.collapseAllMenus();
        infringementSummaryPage.clearCampaignSelections();
    }

    public ArrayList<String> getCampaignSelection() {
        return infringementSummaryPage.getCampaignFilterSelectedOptionsFromFilter();
    }

    public boolean isFilterEnabled() {
        final boolean isFilterEnabled = infringementSummaryPage.isMenuEnabled(MenuType.CAMPAIGN);
        final String buttonStatus = (isFilterEnabled == true) ? "enabled" : "disabled";

        LOGGER.debug("Campaign filter menu is {}", buttonStatus);
        return isFilterEnabled;
    }

    public boolean isCampaignFilterApplied() {
        ArrayList<String> selectedOptions = getCampaignSelection();
        return isCampaignFilterApplied(selectedOptions);
    }

    //PRIVATE METHODS
    private boolean isCategoryElementsDisabled(String categoryName) {

        expandCampaignMenu(true);
        return infringementSummaryPage.areCampaignsFromCategoryDisabled(categoryName);

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
}
