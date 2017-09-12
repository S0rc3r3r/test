package com.muso.utils.dateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class TimeUtils {

    private static final TimeUtils INSTANCE = new TimeUtils();

    private static Date date = null;

    private TimeUtils() {
    };

    public static TimeUtils getInstance() {
        return INSTANCE;
    }

    // Get time

    /**
     * 
     * @return long current time in miliseconds
     */
    public long getTimeNowLong() {

        date = new Date();
        final long now = date.getTime();

        return now;
    }

    /**
     * 
     * @param format
     *            String containing the format of the date/time (example:
     *            dd/mm/yy)
     * @return String current date in the indicated format
     */
    public String getTimeNowString(final String format) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.UK);
        final String dateString = dateFormat.format(date.getTime());

        return dateString;
    }
}
