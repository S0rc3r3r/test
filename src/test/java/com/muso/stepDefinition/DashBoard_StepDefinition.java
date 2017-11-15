package com.muso.stepDefinition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.MenuType;
import com.muso.enums.Table;
import com.muso.enums.TypeType;
import com.muso.testers.CampaignTester;
import com.muso.testers.DashboardTester;
import com.muso.testers.DateRangeTester;
import com.muso.testers.MemberTester;
import com.muso.testers.ProductTester;
import com.muso.testers.ReportTester;
import com.muso.testers.TypeTester;

import cucumber.api.Delimiter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DashBoard_StepDefinition {

    protected WebDriver driver = Hooks.driver;
    private String expandedMenu;
    private static final Logger LOGGER = LoggerFactory.getLogger(DashBoard_StepDefinition.class);
    private DashboardTester dashboardTester = BaseStepDefinition.dashboardTester;
    private CampaignTester campaignTester = BaseStepDefinition.campaignTester;
    private ProductTester productTester = BaseStepDefinition.productTester;
    private TypeTester typeTester = BaseStepDefinition.typeTester;
    private ReportTester reportTester = BaseStepDefinition.reportTester;
    private MemberTester memberTester = BaseStepDefinition.memberTester;
    private DateRangeTester dateRangeTester = BaseStepDefinition.dateRangeTester;

    @When("^I expand '(Report|Date Range|Campaign|Type|Members)' menu$")
    public void I_expand_menu(String menuName) {
        this.expandedMenu = menuName;
        LOGGER.info("Expanding menu: {}", menuName);

        switch ((MenuType.fromString(menuName))) {
        case REPORT:
            reportTester.expandReportMenu(true);
            break;
        case DATE_RANGE:
            dateRangeTester.expandDateRangeMenu(true);
            break;
        case CAMPAIGN:
            campaignTester.expandCampaignMenu(true);
            break;
        case TYPE:
            typeTester.expandTypeMenu(true);
            break;
        case MEMBERS:
            memberTester.expandMembersMenu(true);
            break;
        default:
            throw new InvalidArgumentException("Unknown button label:" + menuName);
        }
    }

    @Then("^I see (\\d+) available options in '(Report,Date Range,Members,Campaign|Product|Type)' menu filter:$")
    public void I_should_see_available_options(int i, String menuName, List<String> options) {
        List<String> availableOptions;

        switch (MenuType.fromString(menuName)) {
        case REPORT:
            availableOptions = reportTester.getReportOptions();
            assertEquals(menuName + " should have " + i + " available options but found: " + availableOptions.toString() + "\n", i, availableOptions.size());

            for (String option : options) {
                LOGGER.debug("Verifying that {} is a valid {} option", option, menuName);
                assertTrue(option + " is not a valid Report option.", MenuType.REPORT.isOptionValid(option));
                assertTrue(option + " is not displayed on UI", availableOptions.contains(option));
            }
            break;
        case DATE_RANGE:
            availableOptions = dateRangeTester.getDateRangeOptions();
            assertEquals(menuName + " should have " + i + " available options but found: " + availableOptions.toString() + "\n", i, availableOptions.size());

            for (String option : options) {
                LOGGER.debug("Verifying that {} is a valid {} option", option, menuName);
                assertTrue(option + " is not a valid Date Range option.", MenuType.DATE_RANGE.isOptionValid(option));
                assertTrue(option + " is not displayed on UI", availableOptions.contains(option));
            }
            break;
        case MEMBERS:
            availableOptions = memberTester.getMembersOptions();
            assertEquals(menuName + " should have " + i + " available options but found: " + availableOptions.toString() + "\n", i, availableOptions.size());
            for (String option : options) {
                LOGGER.debug("Verifying that {} is displayed under {} option", option, menuName);
                assertTrue(option + " is not displayed on UI", availableOptions.contains(option));
            }
            break;
        case TYPE:
            availableOptions = typeTester.getTypeOptions();
            assertEquals(menuName + " should have " + i + " available options but found: " + availableOptions.toString() + "\n", i, availableOptions.size());
            for (String option : options) {
                LOGGER.debug("Verifying that {} is displayed under {} option", option, menuName);
                assertTrue(option + " is not displayed on UI", availableOptions.contains(option));
            }
            break;
        default:
            throw new InvalidArgumentException(" CASE NOT IMPLEMENTED");
        }

    }

    @When("^No '(Campaign|Product|Type)' option is selected$")
    public void no_option_is_selected(String menuName) {

        switch (MenuType.fromString(menuName)) {
        case CAMPAIGN:
            campaignTester.clearCampaignSelection();
            break;
        case TYPE:
            typeTester.clearTypeSelection();
            break;
        case PRODUCT:
            productTester.clearProductSelection();
            break;
        default:
            throw new InvalidArgumentException(menuName + " is not implemented");
        }
    }

    @Then("^'(Campaign|Product|Type)' menu filter is (enabled|disabled)$")
    public void filter_is_enabled(String menuName, String status) {
        boolean expectedStatus = true;

        if (status.equals("disabled"))
            expectedStatus = false;

        LOGGER.debug("Verifying if {} menu filter is {}", menuName, status);

        switch (MenuType.fromString(menuName)) {
        case PRODUCT:
            if (expectedStatus) {
                assertTrue("Product menu should be enabled", productTester.isFilterEnabled());
            } else {
                assertFalse("Product menu should be disabled", productTester.isFilterEnabled());
            }
            break;
        case CAMPAIGN:
            if (expectedStatus) {
                assertTrue("Campaign menu should be enabled", campaignTester.isFilterEnabled());
            } else {
                assertFalse("Campaign menu should be disabled", campaignTester.isFilterEnabled());
            }
            break;
        default:
            throw new InvalidArgumentException(menuName + " is not implemented");
        }
    }

    @Then("^'(Campaign|Product|Type|Members)' menu filter is (visible|hidden)$")
    public void filter_is_visible(String menuName, String status) {
        boolean expectedStatus = true;

        if (status.equals("hidden"))
            expectedStatus = false;

        switch (MenuType.fromString(menuName)) {
        case MEMBERS:
            if (expectedStatus) {
                assertTrue("Members menu should be visible", memberTester.isFilterDisplayed());
            } else {
                assertFalse("Members menu should be hidden", memberTester.isFilterDisplayed());
            }

            break;
        default:
            throw new InvalidArgumentException(menuName + " is not implemented");
        }
    }

    @And("^'(.+)' options? from '(Report|Date Range|Campaign|Product|Type)' menu (?:is|are)(| not) selected$")
    public void option_is_selected(@Delimiter("; ") List<String> optionNames, String menuName, String optionSelected) {

        for (String optionName : optionNames) {
            optionName = StringEscapeUtils.unescapeEcmaScript(optionName);

            LOGGER.debug("Verifying selection state for option {} under menu {}", optionName, menuName);

            boolean selectionState;

            if (optionSelected.isEmpty()) {
                selectionState = true;
            } else {
                selectionState = false;
            }

            Boolean isOptionSelected = null;

            switch (MenuType.fromString(menuName)) {
            case CAMPAIGN:
                isOptionSelected = campaignTester.isCampaignOptionSelected(optionName, selectionState);
                break;
            case TYPE:
                isOptionSelected = typeTester.isTypeOptionSelected(optionName, selectionState);
                break;
            case PRODUCT:
                isOptionSelected = productTester.isPorductOptionSelected(optionName, selectionState);
                break;
            default:
                throw new InvalidArgumentException(menuName + " is not implemented");
            }

            if (selectionState) {
                assertTrue(optionName + " is not selected.", isOptionSelected);
                if (MenuType.fromString(menuName) == MenuType.CAMPAIGN || MenuType.fromString(menuName) == MenuType.PRODUCT) {
                    if (productTester.getNumberOfSelectedProducts() < 6)
                        assertTrue(optionName + " is selected but not displayed in header", campaignTester.isCampaignDisplayedInHeader(optionName));
                }
            } else {
                assertFalse(optionName + " is selected.", !isOptionSelected);
                if (MenuType.fromString(menuName) == MenuType.CAMPAIGN || MenuType.fromString(menuName) == MenuType.PRODUCT) {
                    if (productTester.getNumberOfSelectedProducts() < 6)
                        assertFalse(optionName + " is not selected but displayed in header", campaignTester.isCampaignDisplayedInHeader(optionName));
                }
            }
        }
    }

    @Then("^All '(.+)' subcategories from '(Campaign|Type)' menu are disabled$")
    public void all_subcategories_from_menu_are_disabled(ArrayList<String> categories, String menuName) {

        switch (MenuType.fromString(menuName)) {
        case CAMPAIGN:
            for (String category : categories) {
                assertTrue(category + " is not a selected option under Campaign filter.", campaignTester.isCampaignOptionSelected(category, true));
                assertTrue("All campaigns from the selected category should be disabled", campaignTester.isCampaignCategoryElementsDisabled(category));
            }

            break;
        case TYPE:
            for (String category : categories) {
                assertTrue(category + " is not a selected option under Type filter.", typeTester.isTypeOptionSelected(category, true));
                assertTrue("All types from the selected category should be disabled", typeTester.isTypeCategoryElementsDisabled(category));
            }
            break;
        default:
            throw new InvalidArgumentException(menuName + " is not implemented");
        }
    }

    @When("^I search for '(.+)' '(Campaign|Product|Type)'(| and select the results)$")
    public void i_search_for(String searchString, String menuName, String selectResultsString) {
        boolean selectResults = (selectResultsString.isEmpty()) ? false : true;

        switch (MenuType.fromString(menuName)) {
        case CAMPAIGN:
            if (selectResults) {
                campaignTester.searchForCampaignAndSelect(searchString);
            } else {
                campaignTester.searchForCampaign(searchString);
            }
            break;
        case TYPE:
            if (selectResults) {
                typeTester.searchForTypeAndSelect(searchString);
            } else {
                typeTester.searchForType(searchString);
            }
            break;
        case PRODUCT:
            if (selectResults) {
                productTester.searchForProductAndSelect(searchString);
            } else {
                productTester.searchForProduct(searchString);
            }

            break;
        default:
            throw new InvalidArgumentException(menuName + " is not implemented");
        }
    }

    @When("^'(\\d+)' results? (?:are|is) displayed in '(Campaign|Product|Type)' filter$")
    public void results_are_displayed(int results, String menuName) {
        switch (MenuType.fromString(menuName)) {
        case CAMPAIGN:
            ArrayList<String> resultCampaigns = campaignTester.getCampaignOptions();
            assertEquals("Expected " + results + " results but found " + resultCampaigns.size(), results, resultCampaigns.size());
            break;
        case PRODUCT:
            ArrayList<String> resultProduct = productTester.getProductOptions();
            assertEquals("Expected " + results + " results but found " + resultProduct.size() + ": " + resultProduct.toString(), results, resultProduct.size());
            break;
        case TYPE:
            ArrayList<String> resultType = typeTester.getTypeOptions();
            assertEquals("Expected " + results + " results but found " + resultType.size() + ": " + resultType.toString(), results, resultType.size());
            break;
        default:
            throw new InvalidArgumentException(menuName + " is not implemented");
        }

    }

    @When("^I (select|remove) '(.+)' options? from '(Report|Date Range|Campaign|Product|Type|Members)' menu$")
    public void i_select_option_from_menu(String action, @Delimiter("; ") List<String> optionNames, String menuName) {

        for (String optionName : optionNames) {
            switch (MenuType.fromString(menuName)) {

            case REPORT:
                assertTrue(optionName + " is not a valid option for " + menuName + " or not available for the current user", MenuType.REPORT.isOptionValid(optionName));
                reportTester.setReport(optionName);
                break;
            case DATE_RANGE:
                assertTrue(optionName + " is not a valid option for " + menuName + " or not available for the current user", MenuType.DATE_RANGE.isOptionValid(optionName));
                dateRangeTester.setDateRange(optionName);
                break;
            case CAMPAIGN:
                campaignTester.setCampaign(action, optionName);

                if (action.equals("select")) {
                    assertTrue(optionName + " was not sleected.", campaignTester.isCampaignOptionSelected(optionName, true));
                    if (campaignTester.getNumberOfSelectedCampaigns() < 6)
                        assertTrue(optionName + "is selected but not displayed in header", campaignTester.isCampaignDisplayedInHeader(optionName));

                } else {
                    assertTrue(optionName + " was not removed.", campaignTester.isCampaignOptionSelected(optionName, false));
                    if (campaignTester.getNumberOfSelectedCampaigns() < 6)
                        assertFalse(optionName + "is not selected but displayed in header", campaignTester.isCampaignDisplayedInHeader(optionName));
                }

                break;
            case PRODUCT:
                productTester.setProduct(action, optionName);

                if (action.equals("select")) {
                    assertTrue(optionName + " was not sleected.", productTester.isPorductOptionSelected(optionName, true));
                    if (productTester.getNumberOfSelectedProducts() < 6)
                        assertTrue(optionName + "is selected but not displayed in header", campaignTester.isCampaignDisplayedInHeader(optionName));
                } else {
                    assertTrue(optionName + " was not removed.", productTester.isPorductOptionSelected(optionName, false));
                    if (productTester.getNumberOfSelectedProducts() < 6)
                        assertFalse(optionName + "is not selected but displayed in header", campaignTester.isCampaignDisplayedInHeader(optionName));
                }

                break;
            case TYPE:
                assertTrue(optionName + " is not a valid option for " + menuName + " or not available for the current user", MenuType.TYPE.isOptionValid(optionName));

                typeTester.setType(action, optionName);

                if (action.equals("select")) {
                    assertTrue(optionName + " was not selected.", typeTester.isTypeOptionSelected(optionName, true));
                } else {
                    assertTrue(optionName + " was not removed.", typeTester.isTypeOptionSelected(optionName, false));
                }

                break;
            case MEMBERS:
                if (action.equals("select")) {
                    memberTester.setMember(optionName);
                    assertTrue(optionName + " was not selected under Members menu filter", memberTester.isMemberOptionSelected(optionName));
                } else {
                    throw new InvalidArgumentException(menuName + " does not supports REMOVE operations");
                }
                break;
            default:
                throw new InvalidArgumentException(menuName + " is not implemented");
            }
        }

    }

    @When("^I count '(Total Removals|Last (?:12|6|2)?\\s?Months?|(?:Removals )?Last 4?\\s?Weeks?|Custom Range)' I get '(\\d+)'$")
    public void i_count_header_info_i_get(String optionName, int infringements) {
        int compareResult = -1;
        int actualResult = -1;

        switch (optionName) {
        case "Total Removals":
            actualResult = dashboardTester.getInfringementsTotalRemovals();
            compareResult = Double.compare(infringements, actualResult);
            assertTrue(optionName + " value should be " + infringements + " but was " + actualResult, compareResult == 0);
            break;
        case "Last Week":
        case "Removals Last Week":
            actualResult = dashboardTester.getInfringementsLastWeekRemovals();
            compareResult = Double.compare(infringements, actualResult);
            assertTrue(optionName + " value should be " + infringements + " but was " + actualResult, compareResult == 0);
            break;
        case "Custom Range":
        case "Last 12 Months":
        case "Last 6 Months":
        case "Last 2 Months":
        case "Last Month":
        case "Last 4 Weeks":
            actualResult = dashboardTester.getInfringementsCustomRemovals();
            compareResult = Double.compare(infringements, actualResult);
            assertTrue(optionName + " value should be " + infringements + " but was " + actualResult, compareResult == 0);
            break;

        default:
            throw new InvalidArgumentException("Unknown option name " + optionName);
        }
    }

    @When("^I count '(Removals By Type|Removals By Status)' - '(.+)' I get '([0-9]+.?[0-9]+)'$")
    public void i_count_removals_i_get(String optionType, String optionName, double infringements) {

        int compareResult = -1;
        double actualResult = -1.0;

        switch (optionType) {
        case "Removals By Type":
            assertTrue(optionName + " is not a valid option for TYPE filter", TypeType.isTypeValid(optionName));

            actualResult = dashboardTester.getRemovalByTypeValue(TypeType.fromString(optionName));
            compareResult = Double.compare(infringements, actualResult);

            if (actualResult == -1.00)
                assertTrue(optionName + " type has has no value or is not displayed in Removals By Type chart", false);

            assertTrue(optionName + " value should be " + infringements + "% but was " + actualResult + "%", compareResult == 0);
            break;
        case "Removals By Status":
            switch (optionName) {
            case "Complete":
            case "In Progress":
            case "Aborted":
                actualResult = dashboardTester.getRemovalByStatusValue(optionName);
                compareResult = Double.compare(infringements, actualResult);

                if (actualResult == -1.00)
                    assertTrue(optionName + " type has has no value or is not displayed in Removals By Status chart", false);

                assertTrue(optionName + " value should be " + infringements + "% but was " + actualResult + "%", compareResult == 0);
                break;
            default:
                throw new InvalidArgumentException(optionName + " is not a valid Status for Removals By Status");
            }
            break;
        default:
            throw new InvalidArgumentException("Unknown option Type name " + optionType);

        }
    }

    @When("^I count '(All Time|Last (?:12|6|2)?\\s?Months?|Last 4?\\s?Weeks?|Custom Range)' for (campaign|product) '(.+)' I get '(\\d+)'$")
    public void i_count_campaigns_or_products_i_get(String period, String optionType, String optionName, long infringements) {

        switch (MenuType.fromString(optionType)) {
        case CAMPAIGN:
            Long actualInfringements = dashboardTester.getCampaignInfringements(optionName, period);
            assertTrue("There should be " + infringements + " infringements for campaign " + optionName + " using " + period + " filter. But was " + actualInfringements, infringements == actualInfringements);
            break;
        case PRODUCT:
            dashboardTester.getProductInfringements(optionName, period);
            break;
        default:
            break;

        }

    }

    @Then("^'(.+)' counter is(| not) displayed$")
    public void counter_is_not_displayed(String counterName, String isDisplayed) {

        if (isDisplayed.isEmpty()) {
            assertTrue(counterName + " is not displayed", dashboardTester.isCounterDisplayed(counterName));
        } else {
            assertTrue(counterName + " is displayed", !dashboardTester.isCounterDisplayed(counterName));
        }

    }

    @When("^I change the number of rows to be displayed for '(Campaigns|Removal Details)' table to (5|20|50|100)$")
    public void i_change_the_number_of_rows_to_be_displayed_for_table(String tableName, int rowsToDisplay) {

        switch (tableName) {
        case "Removal Details":
            dashboardTester.setRemovalDetailRowsToDisplay(rowsToDisplay);
            break;
        default:
            throw new InvalidArgumentException(tableName + " was not found.");
        }

    }

    @Then("^'(.+)' report is displayed and has all elements displayed correctly$")
    public void report_view_is_displayed(String reportName) {

        reportTester.checkReportIsDisplayedCorrectly(reportName);

    }

    @Then("^Only the results that match '(Date Range|Campaign|Product|Type|Members)' filter are displayed$")
    public void only_results_that_match_filter_are_displayed_in_table(String menuName) {
        boolean isFilgerApplied = false;

        switch (MenuType.fromString(menuName)) {
        case CAMPAIGN:
            isFilgerApplied = campaignTester.isCampaignFilterApplied();
            break;
        case PRODUCT:
            isFilgerApplied = productTester.isProductFilterApplied();
            break;
        case DATE_RANGE:
            isFilgerApplied = dateRangeTester.isDateRangeFilterApplied();
            break;
        case TYPE:
            isFilgerApplied = typeTester.isTypeFilterApplied();
            break;
        case MEMBERS:
            isFilgerApplied = memberTester.isMembersFilterApplied();
            break;
        default:
            throw new RuntimeException("NOT IMPLEMENTED");
        }

        assertTrue(menuName + " filter is not applied correctly.", isFilgerApplied);
    }

    @Then("^'(Removal Details|Campaigns)' results are displayed on (\\d+) pages?$")
    public void results_are_displayed_in_pages(String tableName, int pagesToDisplay) {
        final Table table = Table.fromString(tableName);

        switch (table) {
        case REMOVAL_DETAILS:
            final int actualPages = dashboardTester.getTablePages(Table.fromString(tableName));
            assertEquals("Results for table " + tableName + " should be displayed on " + pagesToDisplay + " pages\n", pagesToDisplay, actualPages);
            break;
        default:
            throw new InvalidArgumentException(tableName + " table was not found.");
        }
    }

    @And("^The number of results displayed in table '(Removal Details|Campaigns)' is (\\d+)$")
    public void results_are_displayed_in_table(String tableName, int results) {
        final Table table = Table.fromString(tableName);

        switch (table) {
        case REMOVAL_DETAILS:
            final int actualResults = dashboardTester.getTableRows(Table.REMOVAL_DETAILS);
            assertEquals("Results displayed on table " + tableName + " should be " + results + " \n", results, actualResults);
            break;
        default:
            throw new InvalidArgumentException(tableName + " table was not found.");
        }

    }
}
