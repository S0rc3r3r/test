package com.muso.testers;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.enums.MenuType;
import com.muso.enums.ReportType;
import com.muso.persistence.PersistenceManager;
import com.muso.selenium.base.waits.WebDriverWaitManager;
import com.muso.selenium.base.waits.conditions.ExtExpectedConditions;

public class MemberTester extends BaseTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberTester.class);

    public MemberTester(WebDriver driver) {
        super(driver);
    }

    public void expandMembersMenu(boolean expand) {

        if (expand) {
            if (!infringementSummaryPage.isMenuExpanded(MenuType.MEMBERS)) {
                infringementSummaryPage.collapseAllMenus();
                infringementSummaryPage.clickOnMembersMenuButton();
            }
        } else {
            if (infringementSummaryPage.isMenuExpanded(MenuType.MEMBERS)) {
                infringementSummaryPage.clickOnMembersMenuButton();
            }
        }

    }

    public ArrayList<String> getMembersOptions() {
        expandMembersMenu(true);

        ArrayList<String> options = new ArrayList<String>();

        for (WebElement element : infringementSummaryPage.getMembersOptions()) {
            options.add(element.getText());
        }
        return options;
    }

    public void setMember(String member) {
        expandMembersMenu(true);
        infringementSummaryPage.setMember(member);
    }

    public String getMembersSelectedOption() {
        expandMembersMenu(true);

        for (WebElement element : infringementSummaryPage.getMembersOptions()) {
            if (infringementSummaryPage.isElementSelected(element))
                return element.getText();
        }
        return "N/A";
    }

    public boolean isMemberOptionSelected(String optionName) {
        expandMembersMenu(true);
        boolean isMemberSelected = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExtExpectedConditions.selectionOfMember(optionName));
        expandMembersMenu(false);
        boolean isMemberDisplayed = WebDriverWaitManager.getInstance().explicitShortWaitUntilTrue(driver, ExtExpectedConditions.visibilityOfMemberInSelectionArea(optionName));
        if (isMemberDisplayed != isMemberSelected) {
            LOGGER.error("isMemberDisplayed and isMemberSelected doesn't have the same value.");
            throw new RuntimeException("something wrong in members");
        }

        return isMemberDisplayed && isMemberSelected;
    }

    public boolean isFilterDisplayed() {
        return infringementSummaryPage.isMenuDisplayed(MenuType.MEMBERS);
    }

    public boolean isMembersFilterApplied() {
        String selectedMember = getMembersSelectedOption();
        return isMembersFilterApplied(selectedMember);
    }

    //PRIVATE METHODS
    private boolean isMembersFilterApplied(String filter) {

        ReportType reportType = PersistenceManager.getInstance().getReport();

        switch (reportType) {
        case Infringement_Summary:
            return infringementSummaryPage.isMembersFilterApplied(filter);
        case Anti_Piracy_Links:
            break;
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
