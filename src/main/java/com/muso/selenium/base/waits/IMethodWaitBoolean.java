package com.muso.selenium.base.waits;

/**
 * General purpose interface used in conjunction with MethodWaitHandler class
 * 
 * 
 * @param <T>
 *            an object to pass to the waitCondition method
 */
public interface IMethodWaitBoolean<T> {

    boolean waitCondition(T argument);
}
