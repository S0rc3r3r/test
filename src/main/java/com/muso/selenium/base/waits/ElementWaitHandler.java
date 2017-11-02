package com.muso.selenium.base.waits;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class ElementWaitHandler {

    /**
     * Waiting for the proper text to appear inside webElement
     * 
     * @param webElement
     * @param name
     * @return
     */
    public static boolean waitForTextToAppear(final WebElement webElement, String name,
        int timeout) {
        final MethodWaitHandler methodWaitHandler = new MethodWaitHandler();

        return methodWaitHandler.waitForConditionTrue(new IMethodWaitBoolean<String>() {
            @Override
            public boolean waitCondition(final String name) {
                return webElement.getText().toUpperCase().contains(name.toUpperCase());
            }
        }, name, timeout);
    }

    /**
     * Waiting for the proper text to disappear
     * 
     * @param webElement
     * @param name
     * @return
     */
    public static boolean waitForTextToDisappear(final WebElement webElement, String name,
        int timeout) {
        final MethodWaitHandler methodWaitHandler = new MethodWaitHandler();

        return methodWaitHandler.waitForConditionTrue(new IMethodWaitBoolean<String>() {
            @Override
            public boolean waitCondition(final String name) {
                return (!webElement.getText().toUpperCase().contains(name.toUpperCase()));
            }
        }, name, timeout);
    }

    public static boolean waitForElementToHide(final WebElement webElement, int timeout) {
        final MethodWaitHandler methodWaitHandler = new MethodWaitHandler();

        return methodWaitHandler.waitForConditionTrue(new IMethodWaitBoolean<String>() {
            @Override
            public boolean waitCondition(final String name) {
                boolean isElementNotVisible = false;

                /*
                 * if you navigate away from the page the element will not be
                 * available anymore - in this case treat the exception
                 * (StaleElementReference and consider the list was successfully
                 * closed
                 */
                try {
                    isElementNotVisible ^= webElement.isDisplayed();
                } catch (StaleElementReferenceException e) {
                    isElementNotVisible = true;
                }
                return isElementNotVisible;
            }
        }, null, timeout);

    }
}
