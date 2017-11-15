package com.muso.selenium.base.waits.conditions;

import javax.management.RuntimeErrorException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.muso.selenium.base.waits.conditions.campaigns.ConditionWaitUntilCampaignDeselected;
import com.muso.selenium.base.waits.conditions.campaigns.ConditionWaitUntilCampaignRemovedFromSelectionArea;
import com.muso.selenium.base.waits.conditions.campaigns.ConditionWaitUntilCampaignSelected;
import com.muso.selenium.base.waits.conditions.campaigns.ConditionWaitUntilCampaignVisibleInFilter;
import com.muso.selenium.base.waits.conditions.campaigns.ConditionWaitUntilCampaignVisibleInSelectionArea;
import com.muso.selenium.base.waits.conditions.dateRanges.ConditionWaitUntilDateRangeSelected;
import com.muso.selenium.base.waits.conditions.dateRanges.ConditionWaitUntilDateRangeVisibleInFilter;
import com.muso.selenium.base.waits.conditions.dateRanges.ConditionalWaitUntilDateRangeRemovedFromSelectionArea;
import com.muso.selenium.base.waits.conditions.dateRanges.ConditionalWaitUntilDateRangeVisibleInSelectionArea;
import com.muso.selenium.base.waits.conditions.members.ConditionWaitUntilMemberSelected;
import com.muso.selenium.base.waits.conditions.members.ConditionWaitUntilMemberVisibleInFilter;
import com.muso.selenium.base.waits.conditions.members.ConditionWaitUntilMemberVisibleInSelectionArea;
import com.muso.selenium.base.waits.conditions.products.ConditionWaitUntilProductDeselected;
import com.muso.selenium.base.waits.conditions.products.ConditionWaitUntilProductRemovedFromSelectionArea;
import com.muso.selenium.base.waits.conditions.products.ConditionWaitUntilProductSelected;
import com.muso.selenium.base.waits.conditions.products.ConditionalWaitUntilProductVisibleInFilter;
import com.muso.selenium.base.waits.conditions.products.ConditionalWaitUntilProductVisibleInSelectionArea;
import com.muso.selenium.base.waits.conditions.reports.ConditionWaitUntilReportSelected;
import com.muso.selenium.base.waits.conditions.reports.ConditionWaitUntilReportVisibleInFilter;
import com.muso.selenium.base.waits.conditions.reports.ConditionalWaitUntilReportVisibleInSelectionArea;
import com.muso.selenium.base.waits.conditions.types.ConditionWaitUntilTypeDeselected;
import com.muso.selenium.base.waits.conditions.types.ConditionWaitUntilTypeRemovedFromSelectionArea;
import com.muso.selenium.base.waits.conditions.types.ConditionWaitUntilTypeSelected;
import com.muso.selenium.base.waits.conditions.types.ConditionalWaitUntilTypeVisibleInFilter;
import com.muso.selenium.base.waits.conditions.types.ConditionalWaitUntilTypeVisibleInSelectionArea;

/**
 * @author bogdantanasoiu
 *
 */
public class ExtExpectedConditions {

    //REPORT METHODS
    public static ExpectedCondition<WebElement> visibilityOfReportInFilter(String reportName) {
        return new ConditionWaitUntilReportVisibleInFilter(reportName);
    }

    public static ExpectedCondition<Boolean> visibilityOfReportInSelectionArea(String reportName, boolean visible) {

        if (visible) {
            return new ConditionalWaitUntilReportVisibleInSelectionArea(reportName);
        } else {
            throw new RuntimeErrorException(null, "Not Implemented");
        }
    }

    public static ExpectedCondition<Boolean> selectionOfReportInFilter(String reportName, boolean selected) {

        if (selected) {
            return new ConditionWaitUntilReportSelected(reportName);
        } else {
            throw new RuntimeErrorException(null, "Not Implemented");
        }

    }

    // DATE RANGE METHODS
    public static ExpectedCondition<WebElement> visibilityOfDateRangeInFilter(String reportName) {
        return new ConditionWaitUntilDateRangeVisibleInFilter(reportName);
    }

    public static ExpectedCondition<Boolean> visibilityOfDateRangeInSelectionArea(String dateRange, boolean visible) {

        if (visible) {
            return new ConditionalWaitUntilDateRangeVisibleInSelectionArea(dateRange);
        } else {
            return new ConditionalWaitUntilDateRangeRemovedFromSelectionArea(dateRange);
        }
    }

    public static ExpectedCondition<Boolean> selectionOfDateRangeInFilter(String dateRange, boolean selected) {

        if (selected) {
            return new ConditionWaitUntilDateRangeSelected(dateRange);
        } else {
            throw new RuntimeErrorException(null, "Not Implemented");
        }

    }

    // CAMPAIGN METHODS
    public static ExpectedCondition<WebElement> visibilityOfCampaignInFilter(String campaignName) {
        return new ConditionWaitUntilCampaignVisibleInFilter(campaignName);
    }

    public static ExpectedCondition<Boolean> visibilityOfCampaignInSelectionArea(String campaignName, boolean visible) {

        if (visible) {
            return new ConditionWaitUntilCampaignVisibleInSelectionArea(campaignName);
        } else {
            return new ConditionWaitUntilCampaignRemovedFromSelectionArea(campaignName);
        }
    }

    public static ExpectedCondition<Boolean> selectionOfCampaignInFilter(String campaignName, boolean selected) {
        if (selected) {
            return new ConditionWaitUntilCampaignSelected(campaignName);
        } else {
            return new ConditionWaitUntilCampaignDeselected(campaignName);
        }

    }

    // PRODUCT METHODS
    public static ExpectedCondition<WebElement> visibilityOfProductInFilter(String productName) {
        return new ConditionalWaitUntilProductVisibleInFilter(productName);
    }

    public static ExpectedCondition<Boolean> visibilityOfProductInSelectionArea(String productName, boolean selected) {
        if (selected) {
            return new ConditionalWaitUntilProductVisibleInSelectionArea(productName);
        } else {
            return new ConditionWaitUntilProductRemovedFromSelectionArea(productName);
        }

    }

    public static ExpectedCondition<Boolean> selectionOfProductInFilter(String productName, boolean selected) {
        if (selected) {
            return new ConditionWaitUntilProductSelected(productName);
        } else {
            return new ConditionWaitUntilProductDeselected(productName);
        }
    }

    //TYPE METHODS
    public static ExpectedCondition<WebElement> visibilityOfTypeInFilter(String typeName) {
        return new ConditionalWaitUntilTypeVisibleInFilter(typeName);
    }

    public static ExpectedCondition<Boolean> visibilityOfTypeInSelectionArea(String typeName, boolean selected) {
        if (selected) {
            return new ConditionalWaitUntilTypeVisibleInSelectionArea(typeName);
        } else {
            return new ConditionWaitUntilTypeRemovedFromSelectionArea(typeName);
        }

    }

    public static ExpectedCondition<Boolean> selectionOfTypeInFilter(String typeName, boolean selected) {
        if (selected) {
            return new ConditionWaitUntilTypeSelected(typeName);
        } else {
            return new ConditionWaitUntilTypeDeselected(typeName);
        }
    }

    //MEMBERS METHODS
    public static ExpectedCondition<WebElement> visibilityOfMemberInFilter(String optionName) {
        return new ConditionWaitUntilMemberVisibleInFilter(optionName);
    }

    public static ExpectedCondition<Boolean> selectionOfMember(String memberName) {
        return new ConditionWaitUntilMemberSelected(memberName);
    }

    public static ExpectedCondition<Boolean> visibilityOfMemberInSelectionArea(String memberName) {
        return new ConditionWaitUntilMemberVisibleInSelectionArea(memberName);
    }

}
