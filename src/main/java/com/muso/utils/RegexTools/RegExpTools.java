package com.muso.utils.RegexTools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpTools {

    /**
     * Returns the first group matched using the provided regular expression or
     * "" if no match found. Group means using "()" to define the group inside
     * the pattern. If successful it will return whatever was matched inside
     * "()"
     * 
     * @param text
     *            : the string the regular expression is applied to
     * @param regularExpression
     *            : the regular expression to use. Make sure you define at least
     *            one group inside the regular expression. Defining more than
     *            one group is fine but only the first matched group will be
     *            returned
     */
    public static String regExpExtractor(final String text, final String regularExpression) {
        final Pattern pattern = Pattern.compile(regularExpression);
        final Matcher matcher = pattern.matcher(text);
        String result = "";

        if (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    /**
     * Method returns first group of all matches
     * 
     * @param text
     * @param regularExpression
     * @return
     */
    public static List<String> getFirstGroupAllMatches(final String text,
            final String regularExpression) {

        return RegExpTools.getCustomGroupAllMatches(text, regularExpression, 1);
    }

    /**
     * Method returns a custom group number of all matches
     * 
     * @param text
     * @param regularExpression
     * @param groupIndex
     * @return
     */
    public static List<String> getCustomGroupAllMatches(final String text,
            final String regularExpression, final int groupIndex) {
        final List<String> allMatches = new ArrayList<String>();

        final Pattern pattern = Pattern.compile(regularExpression);
        final Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            allMatches.add(matcher.group(groupIndex));
            // group(0) is always the entire string that matched
        }

        return allMatches;
    }
}
