package com.muso.selenium.base.waits.conditions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ExtExpectedConditions {

    // Presence methods
    public static ExpectedCondition<WebElement> presenceOfCampaign(String elemId) {
        return new ConditionWaitUntilCampaignVisible(elemId);
    }

    public static ExpectedCondition<WebElement> selectionOfCampaign(String elemId) {
        return new ConditionWaitUntilCampaignSelected(elemId);
    }

    public static ExpectedCondition<WebElement> presenceOfId(String elemId) {
        return new ConditionElementPresent(By.id(elemId));
    }

    public static ExpectedCondition<Boolean> absenceOfId(String elemId) {
        return new ConditionLocatorNotPresent(new Locator(elemId, LocatorTypes.ID), By.id(elemId));
    }

    public static ExpectedCondition<WebElement> presenceOfElementLocated(By byLoc) {
        return new ConditionElementPresent(byLoc);
    }

    public static ExpectedCondition<WebElement> presenceOfIdUnderElement(WebElement rootElement,
        String elemId) {
        return new ConditionLocatorUnderElementPresent(new Locator(elemId, LocatorTypes.ID),
                By.id(elemId), rootElement);
    }

    public static ExpectedCondition<WebElement> presenceOfName(String elemName) {
        return new ConditionLocatorPresent(new Locator(elemName, LocatorTypes.NAME),
                By.name(elemName));
    }

    public static ExpectedCondition<WebElement> presenceOfNameUnderElement(WebElement rootElement,
        String elemName) {
        return new ConditionLocatorUnderElementPresent(new Locator(elemName, LocatorTypes.NAME),
                By.name(elemName), rootElement);
    }

    public static ExpectedCondition<WebElement> presenceOfXPath(String xPath) {
        return new ConditionLocatorPresent(new Locator(xPath, LocatorTypes.XPATH),
                By.xpath(xPath));
    }

    public static ExpectedCondition<WebElement> presenceOfXPathUnderElement(WebElement rootElement,
        String xPath) {
        return new ConditionLocatorUnderElementPresent(new Locator(xPath, LocatorTypes.XPATH),
                By.xpath(xPath), rootElement);
    }

    public static ExpectedCondition<WebElement> presenceOfCSS(String css) {
        return new ConditionLocatorPresent(new Locator(css, LocatorTypes.CSS),
                By.cssSelector(css));
    }

    public static ExpectedCondition<WebElement> presenceOfClassName(String className) {
        return new ConditionLocatorPresent(new Locator(className, LocatorTypes.CLASSNAME),
                By.className(className));
    }

    public static ExpectedCondition<WebElement> presenceOfClassNameUnderElement(
        WebElement rootElement, String className) {
        return new ConditionLocatorUnderElementPresent(new Locator(className,
                LocatorTypes.CLASSNAME), By.name(className), rootElement);
    }

    // Visibility methods

    public static ExpectedCondition<WebElement> visibilityOfId(String elemId) {
        return new ConditionElementVisible(By.id(elemId));
    }

    public static ExpectedCondition<WebElement> visibilityOfElementLocated(By byLoc) {
        return new ConditionElementVisible(byLoc);
    }

    public static ExpectedCondition<WebElement> visibilityOfIdUnderElement(WebElement rootElement,
        String elemId) {
        return new ConditionLocatorUnderElementVisible(new Locator(elemId, LocatorTypes.ID),
                By.id(elemId), rootElement);
    }

    public static ExpectedCondition<WebElement> visibilityOfName(String elemName) {
        return new ConditionLocatorVisible(new Locator(elemName, LocatorTypes.NAME),
                By.name(elemName));
    }

    public static ExpectedCondition<WebElement> visibilityOfNameUnderElement(
        WebElement rootElement, String elemName) {
        return new ConditionLocatorUnderElementVisible(new Locator(elemName, LocatorTypes.NAME),
                By.name(elemName), rootElement);
    }

    public static ExpectedCondition<WebElement> visibilityOfXPath(String xPath) {
        return new ConditionLocatorVisible(new Locator(xPath, LocatorTypes.XPATH),
                By.xpath(xPath));
    }

    public static ExpectedCondition<WebElement> visibilityOfXPathUnderElement(
        WebElement rootElement, String xPath) {
        return new ConditionLocatorUnderElementVisible(new Locator(xPath, LocatorTypes.XPATH),
                By.xpath(xPath), rootElement);
    }

    public static ExpectedCondition<WebElement> visibilityOfCSS(String css) {
        return new ConditionLocatorVisible(new Locator(css, LocatorTypes.CSS),
                By.cssSelector(css));
    }

    public static ExpectedCondition<WebElement> visibilityOfClassName(String className) {
        return new ConditionLocatorVisible(new Locator(className, LocatorTypes.CLASSNAME),
                By.className(className));
    }

    public static ExpectedCondition<WebElement> visibilityOfClassNameUnderElement(
        WebElement rootElement, String className) {
        return new ConditionLocatorUnderElementVisible(new Locator(className,
                LocatorTypes.CLASSNAME), By.name(className), rootElement);
    }

    // Clickability methods

    public static ExpectedCondition<WebElement> clickabilityOfId(String elemId) {
        return new ConditionElementClickable(By.id(elemId));
    }

    public static ExpectedCondition<WebElement> clicabilityOfElementLocated(By byLoc) {
        return new ConditionElementClickable(byLoc);
    }

    public static ExpectedCondition<WebElement> clickabilityOfIdUnderElement(
        WebElement rootElement, String elemId) {
        return new ConditionLocatorUnderElementClickable(new Locator(elemId, LocatorTypes.ID),
                By.id(elemId), rootElement);
    }

    public static ExpectedCondition<WebElement> clickabilityOfName(String elemName) {
        return new ConditionLocatorClickable(new Locator(elemName, LocatorTypes.NAME),
                By.name(elemName));
    }

    public static ExpectedCondition<WebElement> clickabilityOfNameUnderElement(
        WebElement rootElement, String elemName) {
        return new ConditionLocatorUnderElementClickable(new Locator(elemName, LocatorTypes.NAME),
                By.name(elemName), rootElement);
    }

    public static ExpectedCondition<WebElement> clickabilityOfXPath(String xPath) {
        return new ConditionLocatorClickable(new Locator(xPath, LocatorTypes.XPATH),
                By.xpath(xPath));
    }

    public static ExpectedCondition<WebElement> clickabilityOfXPathUnderElement(
        WebElement rootElement, String xPath) {
        return new ConditionLocatorUnderElementClickable(new Locator(xPath, LocatorTypes.XPATH),
                By.xpath(xPath), rootElement);
    }

    public static ExpectedCondition<WebElement> clickabilityOfCSS(String css) {
        return new ConditionLocatorClickable(new Locator(css, LocatorTypes.CSS),
                By.cssSelector(css));
    }

    public static ExpectedCondition<WebElement> clickabilityOfClassName(String className) {
        return new ConditionLocatorClickable(new Locator(className, LocatorTypes.CLASSNAME),
                By.className(className));
    }

    public static ExpectedCondition<WebElement> clickabilityOfClassNameUnderElement(
        WebElement rootElement, String className) {
        return new ConditionLocatorUnderElementClickable(new Locator(className,
                LocatorTypes.CLASSNAME), By.name(className), rootElement);
    }
}
