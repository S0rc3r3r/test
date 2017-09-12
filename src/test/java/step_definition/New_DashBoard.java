package step_definition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.DateRangeType;
import com.muso.enums.MenuType;
import com.muso.enums.Table;
import com.muso.pages.AntiPiracyLinksPage;
import com.muso.pages.DashboardPage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class New_DashBoard {

    protected WebDriver driver = Hooks.driver;
    private String activePage;
    private static final Logger LOGGER = LoggerFactory.getLogger(New_DashBoard.class);
    private DashboardPage dashboardPage;
    private AntiPiracyLinksPage antiPiracyLinksPage;

    @Given("^I'm located on new dashboard page as '(.*)' user$")
    public void I_am_located_on_dashboard_page(String user) throws Throwable {

        dashboardPage = new DashboardPage(driver, user);
        assertEquals("MUSO Dashboard", dashboardPage.getPageTitle());

    }

    @When("^I expand '(Report|Date Range|Campaign|Type)' menu$")
    public void I_expand_menu(String menuName) {
        this.activePage = menuName;

        switch (menuName) {
        case "Report":
            dashboardPage.expandReportMenu();
            break;
        case "Date Range":
            dashboardPage.expandDateRangeMenu();
            break;
        case "Campaign":
            dashboardPage.expandCampaignMenu();
            break;
        case "Type":
            dashboardPage.expandTypeMenu();
            break;
        default:
            throw new InvalidArgumentException("Unknown button label:" + menuName);
        }
    }

    @Then("^I should see (\\d+) available options: '(.*)'$")
    public void I_should_see_available_options(int i, List<String> options) {

        final List<String> availableOptions = dashboardPage.getMenuAvailableOptions(MenuType.fromString(activePage));

        assertEquals(activePage + " should have " + i + " available options but found: " + options.toString() + "\n", i, availableOptions.size());

        switch (activePage) {
        case "Report":
            for (String option : options) {
                LOGGER.debug("Verifying that {} is a valid {} option", option, activePage);
                // assertEquals(option + " is not a valid Report option.", true, ReportType.isReportTypeValid(option));
                assertEquals(option + " is not a valid Report option.", true, MenuType.isOptionValid(activePage, option));
                assertEquals(option + " is not displayed on UI", true, dashboardPage.isOptionDisplayed(option, activePage));
            }
            break;
        case "Date Range":
            for (String option : options) {
                LOGGER.debug("Verifying that {} is a valid {} option", option, activePage);
                assertEquals(option + " is not a valid Date Range option.", true, DateRangeType.isDateRangeTypeValid(option));
                assertEquals(option + " is not displayed on UI", true, dashboardPage.isOptionDisplayed(option, activePage));
            }
            break;
        }

    }

    @And("^'(.+)' option is selected?$")
    public void option_is_selected(String option) {
        LOGGER.debug("Verifying selection state for option {}", option);
        assertEquals(option + " is not selected.", true, dashboardPage.isOptionSelected(option));

    }

    @When("^I select '(.+)' option from '(Report|Date Range|Campaign|Product|Type)' menu$")
    public void i_select_option_from_menu(String optionName, String menuName) {

        assertTrue(optionName + " is not a valid option for " + menuName + " or not available for the current user", MenuType.isOptionValid(menuName,
                optionName));

        switch (MenuType.fromString(menuName)) {
        case REPORT:
            dashboardPage.expandReportMenu().setReport(optionName);
            break;
        case DATE_RANGE:
            dashboardPage.expandDateRangeMenu().setDateRange(optionName);
            break;
        case CAMPAIGN:
            dashboardPage.expandCampaignMenu().setCampaign(optionName);
        }
    }

    @When("^I count '(.+)' I get '(\\d+)'$")
    public void i_count_i_get(String optionName, long infringements) {

        switch (optionName) {
        case "Total Removals":
            assertEquals(optionName + " value should be " + infringements, infringements, dashboardPage.getTotalRemovals());
            break;
        case "Removals Last Week":
            assertEquals(optionName + " value should be " + infringements, infringements, dashboardPage.getLastWeekRemovals());
            break;
        default:
            assertEquals(optionName + " value should be " + infringements, infringements, dashboardPage.getCustomRemovals());
        }
    }

    @When("^I change the number of rows to be displayed for '(Campaigns|Removal Details)' table to (5|20|50|100)$")
    public void i_change_the_number_of_rows_to_be_displayed_for_table(String tableName, int rowsToDisplay) {

        switch (tableName) {
        case "Removal Details":
            antiPiracyLinksPage.setRowsToDisplay(rowsToDisplay);
            break;
        default:
            throw new InvalidArgumentException(tableName + " was not found.");
        }

    }

    @Then("^'(.+)' report is displayed and has all elements displayed correctly$")
    public void report_view_is_displayed(String pageName) {

        antiPiracyLinksPage = new AntiPiracyLinksPage(driver);

    }

    @Then("^Only the results that match '(Date Range|Campaign|Product|Type)' filter are displayed in '(Campaigns|Removal Details)' table")
    public void only_results_that_match_filter_are_displayed_in_table(String menuName, String tableName) {

        assertTrue(menuName + " filter is not applied correctly.", dashboardPage.isFilterApplied(MenuType.fromString(menuName), tableName));

    }

    @Then("^'(Removal Details|Campaigns)' results are displayed on (\\d+) pages?$")
    public void results_are_displayed_in_pages(String tableName, int pagesToDisplay) {
        final Table table = Table.fromString(tableName);

        switch (table) {
        case REMOVAL_DETAILS:
            final int actualPages = dashboardPage.getTablePages(table);
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
            final int actualResults = dashboardPage.getTableRows(table);
            assertEquals("Results displayed on table " + tableName + " should be " + results + " \n", results, actualResults);
            break;
        default:
            throw new InvalidArgumentException(tableName + " table was not found.");
        }

    }
}
