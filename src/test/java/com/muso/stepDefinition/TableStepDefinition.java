package com.muso.stepDefinition;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.WebDriver;

import com.muso.enums.Table;
import com.muso.testers.CampaignTester;
import com.muso.testers.DashboardTester;
import com.muso.testers.DateRangeTester;
import com.muso.testers.MemberTester;
import com.muso.testers.ProductTester;
import com.muso.testers.ReportTester;
import com.muso.testers.TypeTester;

import cucumber.api.Delimiter;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TableStepDefinition {

    protected WebDriver driver;
    protected static DashboardTester dashboardTester;
    protected static CampaignTester campaignTester;
    protected static ProductTester productTester;
    protected static TypeTester typeTester;
    protected static ReportTester reportTester;
    protected static MemberTester memberTester;
    protected static DateRangeTester dateRangeTester;

    public TableStepDefinition() {
        driver = Hooks.driver;
        dashboardTester = new DashboardTester(driver);

        initTesters();
    }

    @When("^I select '(.+)' from '(Campaigns)' table$")
    public void i_select_from_table(String campaignName, String tableName) {
        campaignName = StringEscapeUtils.unescapeEcmaScript(campaignName);

        switch (Table.fromString(tableName)) {
        case CAMPAIGNS:
            campaignTester.setCampaignFromTable(campaignName);
            break;
        default:
            throw new RuntimeException("NOT IMPLEMENTED");

        }
    }

    @Then("^'(.+)' (?:is|are) displayed in '(Campaigns|Products)' table$")
    public void option_is_displayed_in_table(@Delimiter("; ") List<String> optionNames, String tableName) {

        for (String optionName : optionNames) {
            optionName = StringEscapeUtils.unescapeEcmaScript(optionName);

            switch (Table.fromString(tableName)) {
            case PRODUCTS:
                productTester.isProductFilterApplied();
                break;
            default:
                throw new RuntimeException("NOT IMPLEMENTED");

            }

        }
    }

    private void initTesters() {
        campaignTester = new CampaignTester(driver);
        productTester = new ProductTester(driver);
        typeTester = new TypeTester(driver);
        reportTester = new ReportTester(driver);
        memberTester = new MemberTester(driver);
        dateRangeTester = new DateRangeTester(driver);
    }

}
