package com.muso.selenium.base.waits;

/**
 * General purpose interface used in conjunction with MethodWaitHandler class. Used to wait for a web element
 * 
 * 
 * @param <T>
 *            an object to pass to the waitCondition method
 */

import org.openqa.selenium.WebElement;

public interface IMethodWaitWebElement<T> {
    WebElement waitCondition(T argument);
}
