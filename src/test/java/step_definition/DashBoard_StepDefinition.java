package step_definition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.MenuType;
import com.muso.enums.Table;
import com.muso.testers.DashboardTester;

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
    public void I_should_see_available_optionsTEST(int i, List<String> options) {
        List<String> availableOptions;

        switch (MenuType.fromString(expandedMenu)) {
        case REPORT:
            availableOptions = dashboardTester.getReportOptions();
            assertEquals(expandedMenu + " should have " + i + " available options but found: " + availableOptions.toString() + "\n", i,
                    availableOptions.size());

            for (String option : options) {
                LOGGER.debug("Verifying that {} is a valid {} option", option, expandedMenu);
                assertTrue(option + " is not a valid Report option.", MenuType.REPORT.isOptionValid(option));
                assertTrue(option + " is not displayed on UI", availableOptions.contains(option));
            }
            break;
        case DATE_RANGE:
            availableOptions = dashboardTester.getDateRangeOptions();
            assertEquals(expandedMenu + " should have " + i + " available options but found: " + availableOptions.toString() + "\n", i,
                    availableOptions.size());

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

    @And("^'(.+)' option is selected?$")
    public void option_is_selected(String option) {
        LOGGER.debug("Verifying selection state for option {}", option);
        assertEquals(option + " is not selected.", true, dashboardTester.isOptionSelected(option));

    }

    @When("^I select '(.+)' option from '(Report|Date Range|Campaign|Product|Type)' menu$")
    public void i_select_option_from_menu(String optionName, String menuName) {

        switch (MenuType.fromString(menuName)) {
        case REPORT:
            assertTrue(optionName + " is not a valid option for " + menuName + " or not available for the current user", MenuType.REPORT
                    .isOptionValid(optionName));
            dashboardTester.setReport(optionName);
            break;
        case DATE_RANGE:
            assertTrue(optionName + " is not a valid option for " + menuName + " or not available for the current user", MenuType.DATE_RANGE
                    .isOptionValid(optionName));
            dashboardTester.setDateRange(optionName);
            break;
        case CAMPAIGN:
            assertTrue(optionName + " is not a valid option for " + menuName + " or not available for the current user", MenuType.CAMPAIGN
                    .isOptionValid(optionName));
            dashboardTester.setCampaign(optionName);
            break;
        case PRODUCT:
            assertTrue(optionName + " is not a valid option for " + menuName + " or not available for the current user", MenuType.PRODUCT
                    .isOptionValid(optionName));
            dashboardTester.setProduct(optionName);
        case TYPE:
            assertTrue(optionName + " is not a valid option for " + menuName + " or not available for the current user", MenuType.TYPE.isOptionValid(
                    optionName));
            dashboardTester.setType(optionName);
        default:
            throw new InvalidArgumentException(menuName + " is not implemented");
        }
    }

    @When("^I count '(.+)' I get '(\\d+)'$")
    public void i_count_i_get(String optionName, long infringements) {

        switch (optionName) {
        case "Total Removals":
            assertEquals(optionName + " value should be " + infringements, infringements, dashboardTester.getInfringementsTotalRemovals());
            break;
        case "Removals Last Week":
            assertEquals(optionName + " value should be " + infringements, infringements, dashboardTester.getInfringementsLastWeekRemovals());
            break;
        default:
            assertEquals(optionName + " value should be " + infringements, infringements, dashboardTester.getInfringementsCustomRemovals());
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