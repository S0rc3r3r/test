package step_definition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.MenuType;
import com.muso.enums.Table;
import com.muso.enums.TypeType;
import com.muso.testers.DashboardTester;

import cucumber.api.Delimiter;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class DashBoard_StepDefinition {

    protected WebDriver driver = Hooks.driver;
    private String expandedMenu;
    private static final Logger LOGGER = LoggerFactory.getLogger(DashBoard_StepDefinition.class);
    private DashboardTester dashboardTester;

    @Given("^I'm located on new dashboard page as '(.*)' user$")
    public void I_am_located_on_dashboard_page(String user) throws Throwable {

        dashboardTester = new DashboardTester();
        dashboardTester.navigateToDashboard(driver, user);

        assertEquals("MUSO Dashboard", dashboardTester.getPageTitle());
    }

    @When("^I expand '(Report|Date Range|Campaign|Type)' menu$")
    public void I_expand_menu(String menuName) {
        this.expandedMenu = menuName;
        LOGGER.info("Expanding menu: {}", menuName);

        switch (menuName) {
        case "Report":
            dashboardTester.expandReportMenu();
            break;
        case "Date Range":
            dashboardTester.expandDateRangeMenu();
            break;
        case "Campaign":
            dashboardTester.expandCampaignMenu();
            break;
        case "Type":
            dashboardTester.expandTypeMenu();
            break;
        default:
            throw new InvalidArgumentException("Unknown button label:" + menuName);
        }
    }

    @Then("^I should see (\\d+) available options:")
    public void I_should_see_available_options(int i, List<String> options) {
        List<String> availableOptions;

        switch (MenuType.fromString(expandedMenu)) {
        case REPORT:
            availableOptions = dashboardTester.getReportOptions();
            assertEquals(expandedMenu + " should have " + i + " available options but found: " + availableOptions.toString() + "\n", i, availableOptions.size());

            for (String option : options) {
                LOGGER.debug("Verifying that {} is a valid {} option", option, expandedMenu);
                assertTrue(option + " is not a valid Report option.", MenuType.REPORT.isOptionValid(option));
                assertTrue(option + " is not displayed on UI", availableOptions.contains(option));
            }
            break;
        case DATE_RANGE:
            availableOptions = dashboardTester.getDateRangeOptions();
            assertEquals(expandedMenu + " should have " + i + " available options but found: " + availableOptions.toString() + "\n", i, availableOptions.size());

            for (String option : options) {
                LOGGER.debug("Verifying that {} is a valid {} option", option, expandedMenu);
                assertTrue(option + " is not a valid Date Range option.", MenuType.DATE_RANGE.isOptionValid(option));
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
            dashboardTester.clearCampaignSelection();
            break;
        case TYPE:
            dashboardTester.clearTypeSelection();
            break;
        case PRODUCT:
            dashboardTester.clearProductSelection();
            break;
        default:
            throw new InvalidArgumentException(menuName + " is not implemented");
        }
    }

    @And("^'(.+)' option from '(Report|Date Range|Campaign|Product|Type)' menu is(| not) selected$")
    public void option_is_selected(String optionName, String menuName, String optionSelected) {
        LOGGER.debug("Verifying selection state for option {} under menu {}", optionName, menuName);

        Boolean isOptionSelected = null;

        switch (MenuType.fromString(menuName)) {
        case CAMPAIGN:
            isOptionSelected = dashboardTester.isCampaignOptionSelected(optionName);
            break;
        case TYPE:
            isOptionSelected = dashboardTester.isTypeOptionSelected(optionName);
            break;
        case PRODUCT:
            isOptionSelected = dashboardTester.isPorductOptionSelected(optionName);
            break;
        default:
            throw new InvalidArgumentException(menuName + " is not implemented");
        }

        if (optionSelected.isEmpty()) {
            assertTrue(optionName + " is not selected.", isOptionSelected);
            if (MenuType.fromString(menuName) == MenuType.CAMPAIGN || MenuType.fromString(menuName) == MenuType.PRODUCT) {
                if (dashboardTester.getNumberOfSelectedProducts() < 6)
                    assertTrue(optionName + " is selected but not displayed in header", dashboardTester.isCampaignDisplayedInHeader(optionName));
            }
        } else {
            assertFalse(optionName + " is selected.", isOptionSelected);
            if (MenuType.fromString(menuName) == MenuType.CAMPAIGN || MenuType.fromString(menuName) == MenuType.PRODUCT) {
                if (dashboardTester.getNumberOfSelectedProducts() < 6)
                    assertFalse(optionName + " is not selected but displayed in header", dashboardTester.isCampaignDisplayedInHeader(optionName));
            }
        }

    }

    @Then("^All '(.+)' subcategories from '(Campaign|Type)' menu are disabled$")
    public void all_subcategories_from_menu_are_disabled(ArrayList<String> categories, String menuName) {

        switch (MenuType.fromString(menuName)) {
        case CAMPAIGN:
            for (String category : categories) {
                assertTrue(category + " is not a selected option under Campaign filter.", dashboardTester.isCampaignOptionSelected(category));
                assertTrue("All campaigns from the selected category should be disabled", dashboardTester.isCampaignCategoryElementsDisabled(category));
            }

            break;
        case TYPE:
            for (String category : categories) {
                assertTrue(category + " is not a selected option under Type filter.", dashboardTester.isTypeOptionSelected(category));
                assertTrue("All types from the selected category should be disabled", dashboardTester.isTypeCategoryElementsDisabled(category));
            }
            break;
        default:
            throw new InvalidArgumentException(menuName + " is not implemented");
        }
    }

    @When("^I search for '(.+)' '(Campaign|Product|Type)'$")
    public void i_search_for(String searchString, String menuName) {
        switch (MenuType.fromString(menuName)) {
        case CAMPAIGN:
            dashboardTester.searchForCampaign(searchString);
            break;
        case TYPE:
            dashboardTester.searchForType(searchString);
            break;
        case PRODUCT:
            dashboardTester.searchForProduct(searchString);
            break;
        default:
            throw new InvalidArgumentException(menuName + " is not implemented");
        }

    }

    @When("^'(\\d+)' results? are displayed in '(Campaign|Product|Type)' filter$")
    public void results_are_displayed(int results, String menuName) {
        switch (MenuType.fromString(menuName)) {
        case CAMPAIGN:
            ArrayList<String> resultCampaigns = dashboardTester.getCampaignOptions();
            assertEquals("Expected " + results + " results but found " + resultCampaigns.size(), results, resultCampaigns.size());
            break;
        case PRODUCT:
            ArrayList<String> resultProduct = dashboardTester.getProductOptions();
            assertEquals("Expected " + results + " results but found " + resultProduct.size() + ": " + resultProduct.toString(), results, resultProduct.size());
            break;
        case TYPE:
            ArrayList<String> resultType = dashboardTester.getTypeOptions();
            assertEquals("Expected " + results + " results but found " + resultType.size() + ": " + resultType.toString(), results, resultType.size());
            break;
        default:
            throw new InvalidArgumentException(menuName + " is not implemented");
        }

    }

    @When("^I (select|remove) '(.+)' option from '(Report|Date Range|Campaign|Product|Type)' menu$")
    public void i_select_option_from_menu(String action, @Delimiter("; ") List<String> optionNames, String menuName) {

        for (String optionName : optionNames) {
            switch (MenuType.fromString(menuName)) {

            case REPORT:
                assertTrue(optionName + " is not a valid option for " + menuName + " or not available for the current user", MenuType.REPORT.isOptionValid(optionName));
                dashboardTester.setReport(optionName);
                break;
            case DATE_RANGE:
                assertTrue(optionName + " is not a valid option for " + menuName + " or not available for the current user", MenuType.DATE_RANGE.isOptionValid(optionName));
                dashboardTester.setDateRange(optionName);
                break;
            case CAMPAIGN:

                dashboardTester.setCampaign(action, optionName);

                if (action.equals("select")) {
                    assertTrue(optionName + " was not sleected.", dashboardTester.isCampaignOptionSelected(optionName));
                    if (dashboardTester.getNumberOfSelectedCampaigns() < 6)
                        assertTrue(optionName + "is selected but not displayed in header", dashboardTester.isCampaignDisplayedInHeader(optionName));

                } else {
                    assertTrue(optionName + " was not removed.", !dashboardTester.isCampaignOptionSelected(optionName));
                    if (dashboardTester.getNumberOfSelectedCampaigns() < 6)
                        assertFalse(optionName + "is not selected but displayed in header", dashboardTester.isCampaignDisplayedInHeader(optionName));
                }

                break;
            case PRODUCT:
                dashboardTester.setProduct(action, optionName);

                if (action.equals("select")) {
                    assertTrue(optionName + " was not sleected.", dashboardTester.isPorductOptionSelected(optionName));
                    if (dashboardTester.getNumberOfSelectedProducts() < 6)
                        assertTrue(optionName + "is selected but not displayed in header", dashboardTester.isCampaignDisplayedInHeader(optionName));
                } else {
                    assertTrue(optionName + " was not removed.", !dashboardTester.isPorductOptionSelected(optionName));
                    if (dashboardTester.getNumberOfSelectedProducts() < 6)
                        assertFalse(optionName + "is not selected but displayed in header", dashboardTester.isCampaignDisplayedInHeader(optionName));
                }

                break;
            case TYPE:
                assertTrue(optionName + " is not a valid option for " + menuName + " or not available for the current user", MenuType.TYPE.isOptionValid(optionName));
                dashboardTester.setType(optionName);
                dashboardTester.isTypeOptionSelected(optionName);
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

        switch (optionType) {
        case "campaign":
            Long actualInfringements = dashboardTester.getCampaignInfringements(optionName, period);
            assertTrue("There should be " + infringements + " infringements for campaign " + optionName + " using " + period + " filter. But was " + actualInfringements, infringements == actualInfringements);
            break;
        case "product":
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

        dashboardTester.checkReportIsDisplayedCorrectly(reportName);

    }

    @Then("^Only the results that match '(Date Range|Campaign|Product|Type)' filter are displayed$")
    public void only_results_that_match_filter_are_displayed_in_table(String menuName) {

        assertTrue(menuName + " filter is not applied correctly.", dashboardTester.isFilterApplied(MenuType.fromString(menuName)));

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
