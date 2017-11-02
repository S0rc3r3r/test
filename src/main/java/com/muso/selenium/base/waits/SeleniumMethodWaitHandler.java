package com.muso.selenium.base.waits;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.utils.thread.ThreadHandler;

public class SeleniumMethodWaitHandler extends MethodWaitHandler {

    private long poolingInterval = 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumMethodWaitHandler.class);

    public long getPoolingInterval() {
        return poolingInterval;
    }

    public void setPoolingInterval(final long poolingInterval) {
        this.poolingInterval = poolingInterval;
    }

    /***
     * The method wait for a web element to be instantiated (not null) until
     * timeoutSeconds expires. The condition can be any object which implements
     * IMethodWaitElement interface
     * 
     * @param waitObject
     *            implementing IMethodWaitElement object
     * @param argument
     *            the argument (single argument) to pass for the
     *            waitObject.waitCondition(T argument)
     * @param timeoutSeconds
     *            how long to wait for the the waitObject.waitCondition to
     *            become true
     * @return
     */
    public <T> WebElement waitForWebElement(final IMethodWaitWebElement<T> waitObject,
        final T argument, final long timeoutSeconds) {
        WebElement webElement = null;

        final long startTime = System.currentTimeMillis() / 1000;
        long currentTime = System.currentTimeMillis() / 1000;
        while (((currentTime - startTime) < timeoutSeconds) && (webElement == null)) {
            LOGGER.debug("web element null..waiting");
            webElement = waitObject.waitCondition(argument);
            ThreadHandler.shortSleep();
            currentTime = System.currentTimeMillis() / 1000;
        }
        return webElement;
    }

}
