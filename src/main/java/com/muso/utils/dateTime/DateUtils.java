package com.muso.utils.dateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateUtils {

    private static final DateUtils INSTANCE = new DateUtils();

    public static final String STD_EU_FORMAT = "yyyy-MM-dd";
    public static final String COMPLETE_FORMAT = "yyyy MMM dd hh:mm:ss";

    protected static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    private DateUtils() {
    };

    public static DateUtils getInstance() {
        return INSTANCE;
    }

    // Get date

    /**
     * 
     * @return Date object with current date
     */
    public Date getDateNow() {

        return new Date();
    }

    /**
     * 
     * @param addedDays
     *            number of days to add to current date
     * @return Date object with current date plus number of addedDays
     */
    public Date getDateNowAddDays(int addedDays) {

        final Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(TimeUtils.getInstance().getTimeNowLong());
        calendar.add(Calendar.DATE, addedDays);

        return calendar.getTime();
    }

    // Convert dates

    public String dateToString(String format, Date dateConverted) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.UK);
        final String dateString = dateFormat.format(dateConverted);

        return dateString;
    }

    public static Date dateFromString(String format, String dateString) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
            date = dateFormat.parse(dateString);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    // Modify dates

    /**
     * 
     * @param dateString
     * @param format
     *            String containing the format of the date/time (example:
     *            dd/mm/yy)
     * @param addedDays
     *            number of days added to the current date
     * @return input date plus the added days
     */
    public String addDays(String dateString, String format, int addedDays) {

        final SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.UK);
        String date_out = null;
        final Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(dateFormat.parse(dateString));
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
        }

        calendar.add(Calendar.DATE, addedDays);
        date_out = dateFormat.format(calendar.getTime());

        return date_out;
    }

    public static String getYearAndMonth(int years, int months) {

        final Date date = new Date();
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if (years != 0) {
            cal.add(Calendar.YEAR, years);
        }
        if (months != 0) {
            cal.add(Calendar.MONTH, months);
        }

        final int year = cal.get(Calendar.YEAR) % 100;
        final int month = cal.get(Calendar.MONTH);

        return Integer.toString(year) + "-" + Integer.toString(month + 1);

    }

    /**
     * Get today's date/time with 10 years added on.
     * 
     * @return futureDate
     *         The date in with last password change field will be changed to.
     */
    public static String getFutureDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.YEAR, 10);

        final String futureDate = dateFormat.format(cal.getTime());
        LOGGER.debug("Future date = {}", futureDate);

        return futureDate;
    }

    public static String getDaysInPast(int minusDaysBack, int mins) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, minusDaysBack);
        cal.set(Calendar.MINUTE, mins);
        final String dateBefore30Days = dateFormat.format(cal.getTime());

        return dateBefore30Days;
    }

    public static Date getMonthsInPast(int minusMonthsBack) {
        DateFormat dateFormat = new SimpleDateFormat(STD_EU_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -minusMonthsBack);
        final String dateBefore = dateFormat.format(cal.getTime());

        return dateFromString(STD_EU_FORMAT, dateBefore);

    }

    public static Date getWeeksInPast(int minusWeeksBack) {
        final int weeksBack = minusWeeksBack * 7;
        DateFormat dateFormat = new SimpleDateFormat(STD_EU_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -weeksBack);
        final String dateBefore = dateFormat.format(cal.getTime());

        return dateFromString(STD_EU_FORMAT, dateBefore);

    }

    public static SimpleDateFormat getDateFormat(String language) {
        SimpleDateFormat dateFormat = null;

        if (language.equalsIgnoreCase("de")) {
            dateFormat = new SimpleDateFormat("dd.MM.yy");
        } else {
            dateFormat = new SimpleDateFormat("M/d/yy");
        }

        return dateFormat;
    }
}
