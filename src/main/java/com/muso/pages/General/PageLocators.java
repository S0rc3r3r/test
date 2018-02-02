package com.muso.pages.General;

public abstract class PageLocators {

    //GENERAL

    // HEADER
    protected final String reportHeaderElement_CSS = "#header h4";
    protected final String campaignHeaderElement_CSS = "div h2.allCampaigns";
    protected final String dateRangeHeaderElement_CSS = "h2.dateRange";
    protected final String logoHeaderElement_CSS = "#header img";

    protected final String totalRemovals_CSS = "counterAllTime";
    protected final String lastWeekRemoval_CSS = "counterLastWeek";
    protected final String customRemoval_CSS = "counterCustomPeriod";
    protected final String removalsPerDayTitle_CSS = "muso-line-chart-per-day p";
    protected final String removalsPerDayChart_CSS = "chart_divEvolution";

    // PRODUCT TABLE
    protected final String productTable_CSS = "muso-products .row.top-module-space";

    // CAMPAIGN TABLE
    protected final String campaignTable_CSS = "muso-campaigns-data-table div.row";
    protected final String campaignTableHeader_CSS = "muso-campaigns-data-table thead";
    protected final String campaignTableBody_CSS = "muso-campaigns-data-table tbody";
    protected final String campaignTableDisplayPage_CSS = "muso-campaigns-data-table span[class='pages']";
    protected final String campaignTableshowNoOfRowsButton_CSS = "muso-campaigns-data-table button.btn.dropdown-toggle.pager";
    protected final String campaignTableshowNoOfRowsOptions_CSS = "div#data-table-page-size-picker-container ul.dropdown-menu.inner";

    // REMOVALS BY STATUS / TYPE
    protected final String removalsByTypeHolder_CSS = "muso-infringements-by-high-level-type-chart muso-pie-chart-legend svg > g > g";
    protected final String removalsByStatusHolder_CSS = "muso-removals-by-status-and-high-level-type muso-infringements-per-status-chart svg > g > g";

    // MENU BUTTONS
    protected final String Sidebar_CSS = "div#sidebar";
    protected final String ToggleButton_ID = "toggleButton";
    protected final String ReportMenuButton_CSS = "button[title='Report']";
    protected final String MembersMenuButton_CSS = "button[title='Members']";
    protected final String DateRangeMenuButton_CSS = "button[title='Date range']";
    protected final String CampaignMenuButton_CSS = "button[title='Campaign']";
    protected final String TypeMenuButton_CSS = "button[title='Type']";
    protected final String ProductMenuButton_CSS = "button[title='Product']";
    protected final String SupportButton_CSS = "#links span";
    protected final String AccountButton_CSS = "#links a:nth-child(1)";
    protected final String LogoutButton_CSS = "#links a:nth-child(2)";

    // MENU OPTIONS HOLDERS
    protected final String reportHolder_CSS = "div#report-picker-container .dropdown-menu.inner";
    protected final String reportHolderItems_CSS = "div#report-picker-container .dropdown-menu.inner li";
    protected final String reportSelectedItem_CSS = "muso-report-filter div.selectedOptions span";

    protected final String dateRangeOptionsHolder_CSS = "muso-period-filter div.daterangepicker.dropdown-menu.opensleft";

    protected final String campaignOptionsHolder_CSS = "#campaign-picker-container ul.dropdown-menu.inner";
    protected final String campaignSearchBox_CSS = "div#campaign-picker-container input.form-control";
    protected final String campaignItemHolder_CSS = "muso-filter-campaign-item .row";
    protected final String campaignCategoryHolder_CSS = "muso-filter-campaign-type-item .row";

    protected final String typeHolder_CSS = "div#type-picker-container ul.dropdown-menu.inner";
    protected final String typeHolderItems_CSS = "div#type-picker-container ul.dropdown-menu.inner li";
    protected final String typeSearchBox_CSS = "div#type-picker-container input.form-control";

    protected final String productOptionsHolder_CSS = "#product-picker-container ul.dropdown-menu.inner";
    protected final String productOptionsItems_CSS = "#product-picker-container ul.dropdown-menu.inner li";
    protected final String productOptionsSelectionHolder_CSS = "muso-product-filter muso-filter-product-item";
    protected final String productSelectionItems_CSS = "muso-product-filter muso-filter-product-item span";
    protected final String productSearchBox_CSS = "div#product-picker-container input.form-control";

    protected final String memberHolder_CSS = "div#user-picker-container ul.dropdown-menu.inner";
    protected final String memberHolderItems_CSS = "div#user-picker-container ul.dropdown-menu.inner li";
    protected final String memberSelectedItem_CSS = "muso-filter-user .selectedOptions span";

}
