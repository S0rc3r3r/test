package com.muso.utils.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadHandler {

    private final static String DEFAULT_ERR = "Exception while sleeping thread";
    private final static int SHORT_SLEEP = 1000;
    private final static int MINI_SLEEP = 10;

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ThreadHandler.class);

    public static void sleep(final long milliseconds, final String errorMessage) {

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            LOGGER.error(errorMessage);
        }
    }

    public static void sleep(final long milliseconds) {
        sleep(milliseconds, DEFAULT_ERR);
    }

    public static void shortSleep() {
        sleep(SHORT_SLEEP, DEFAULT_ERR);
    }

    public static void miniSleep() {
        sleep(MINI_SLEEP, DEFAULT_ERR);
    }

}
