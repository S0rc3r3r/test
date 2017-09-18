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
    REGION("Region", OPTIONS.REGION_OPTIONS),
    CATEGORY("Piracy Category", OPTIONS.CATEGORY_OPTIONS),
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

    public boolean isOptionValid(String optionName) {

        for (String option : this.getOptions()) {
            if (option.equals(optionName)) {
                return true;
            }
        }
        LOGGER.warn("{} is not a valid option for report {}. Available options: {}", optionName, this.getName(), this.getOptions().toString());
        return false;
    }

    public static MenuType getMenuFromOption(String optionName) {
        LOGGER.debug("Searching for filter with {} option", optionName);
        for (MenuType type : MenuType.values()) {
            for (String option : type.getOptions()) {
                if (option.equals(optionName)) {
                    LOGGER.debug("Found {} filter", type.getName());
                    return type;
                }

            }
        }

        return MenuType.UNKNOWN;

        // throw new IllegalArgumentException(optionName + " could not be found in any Menu");
    }

    public void setMenuOptions(List<String> options) {
        LOGGER.debug("Saving {} options: {}", this.getName(), options.toString());
        this.setOptions(options);
    }

    private static class OPTIONS {
        private static final List<String> REPORT_OPTIONS = ReportType.getAvailableOptions();

        private static final List<String> DATERAGENE_OPTIONS = DateRangeType.getAvailableOptions();

        private static final List<String> CAMPAIGN_OPTIONS = Arrays.asList("");

        private static final List<String> PRODUCT_OPTIONS = Arrays.asList("");

        private static final List<String> UNKNOWN_OPTIONS = Arrays.asList("");

        private static final List<String> TYPE_OPTIONS = TypeType.getAvailableOptions();

        private static final List<String> REGION_OPTIONS = RegionType.getAvailableOptions();

        private static final List<String> CATEGORY_OPTIONS = CategoryType.getAvailableOptions();

    }

}
