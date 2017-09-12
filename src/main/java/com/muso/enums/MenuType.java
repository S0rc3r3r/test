package com.muso.enums;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MenuType {

    REPORT("Report", OPTIONS.REPORT_OPTIONS),
    DATE_RANGE("Date Range", OPTIONS.DATERAGENE_OPTIONS),
    CAMPAIGN("Campaign", OPTIONS.CAMPAIGN_OPTIONS),
    PRODUCT("Product", OPTIONS.PRODUCT_OPTIONS),
    TYPE("Type", OPTIONS.TYPE_OPTIONS),
    UNKNOWN("Unknown", OPTIONS.UNKNOWN_OPTIONS);

    private MenuType(String name, List<String> options) {
        this.name = name;
        this.optionsList = options;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuType.class);

    private String name;
    private List<String> optionsList;

    public String getName() {
        return this.name;
    }

    public List<String> getOptions() {
        return this.optionsList;
    }

    public void setOptions(List<String> options) {
        this.optionsList = options;
    }

    public static MenuType fromString(String menuName) {
        if (menuName != null) {
            for (MenuType type : MenuType.values()) {
                if (menuName.equalsIgnoreCase(type.getName())) {
                    return type;
                }
            }
        }
        throw new IllegalArgumentException(menuName + " menu is not a valid name or not available for the current user");
    }

    public static boolean isOptionValid(String menuName, String optionName) {

        MenuType menu = fromString(menuName);

        for (String option : menu.getOptions()) {
            if (option.equals(optionName)) {
                return true;
            }
        }
        LOGGER.warn("{} is not a valid option for report {}. Available options: {}", optionName, menuName, menu.getOptions().toString());
        return false;
    }

    public static MenuType getMenuFromOption(String optionName) {
        for (MenuType type : MenuType.values()) {
            for (String option : type.getOptions()) {
                if (option.equals(optionName))
                    return type;
            }
        }

        return MenuType.UNKNOWN;

        // throw new IllegalArgumentException(optionName + " could not be found in any Menu");
    }

    public static void setMenuOptions(MenuType menuName, List<String> options) {

        menuName.setOptions(options);
    }

    private static class OPTIONS {
        private static final List<String> REPORT_OPTIONS = ReportType.getAvailableOptions();

        private static final List<String> DATERAGENE_OPTIONS = DateRangeType.getAvailableOptions();

        private static final List<String> CAMPAIGN_OPTIONS = Arrays.asList("");

        private static final List<String> PRODUCT_OPTIONS = Arrays.asList("");

        private static final List<String> UNKNOWN_OPTIONS = Arrays.asList("");

        private static final List<String> TYPE_OPTIONS = TypeType.getAvailableOptions();

    }

}
