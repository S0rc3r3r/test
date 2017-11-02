package com.muso.selenium.base.waits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.utils.thread.ThreadHandler;

public class MethodWaitHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodWaitHandler.class);

    /**
     * The method wait for a condition to become true until timeoutSeconds
     * expires. The condition can be any object which implements
     * InterfaceMethodWait interface
     * 
     * @param waitObject
     *            implementing InterfaceMethodWait object
     * @param argument
     *            the argument (single argument) to pass for the
     *            waitObject.waitCondition(T argument)
     * @param timeoutSeconds
     *            how long to wait for the the waitObject.waitCondition to
     *            become true
     * @return
     */
    public <T> boolean waitForConditionTrue(final IMethodWaitBoolean<T> waitObject,
        final T argument, final long timeoutSeconds) {
        boolean found = false;

        final long startTime = System.currentTimeMillis() / 1000;
        long currentTime = System.currentTimeMillis() / 1000;
        while (((currentTime - startTime) < timeoutSeconds) && !found) {
            LOGGER.debug("waiting for condition true");
            found = waitObject.waitCondition(argument);
            ThreadHandler.shortSleep();
            currentTime = System.currentTimeMillis() / 1000;
        }
        return found;
    }

    /**
     * The method wait for a simple boolean condition to become true until
     * timeoutSeconds expires.
     * 
     * @param waitObject
     *            implementing IMethodWaitSimpleBoolean object
     * @param timeoutSeconds
     *            how long to wait for the the waitObject.waitCondition to
     *            become true
     * @return
     */
    public boolean waitForSimpleConditionTrue(final IMethodWaitSimpleBoolean waitObject,
        final long pollingDelay, final long timeoutSeconds) {
        boolean found = false;

        final long startTime = System.currentTimeMillis() / 1000;
        long currentTime = System.currentTimeMillis() / 1000;
        while (((currentTime - startTime) < timeoutSeconds) && !found) {
            LOGGER.debug("waiting for condition true");
            found = waitObject.waitCondition();
            ThreadHandler.sleep(pollingDelay);
            currentTime = System.currentTimeMillis() / 1000;
        }
        return found;
    }
}
