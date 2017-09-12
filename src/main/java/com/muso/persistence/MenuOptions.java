package com.muso.persistence;

import java.util.ArrayList;
import java.util.List;

import com.muso.enums.DateRangeType;
import com.muso.enums.ReportType;
import com.muso.enums.TypeType;

public class MenuOptions {

    private ReportType report;
    private DateRangeType dateRange;
    private List<String> campaign = new ArrayList<String>();
    private ArrayList<String> product;
    private TypeType type;
    private static volatile MenuOptions instance = null;

    private MenuOptions() {
        this.report = ReportType.Infringement_Summary;
        this.dateRange = DateRangeType.LAST_2_MONTHS;
        this.type = TypeType.ALL;
        this.campaign.add("All campaigns");

    }

    public static MenuOptions getInstance() {
        if (instance == null) {
            synchronized (MenuOptions.class) {
                if (instance == null) {
                    instance = new MenuOptions();
                }
            }
        }
        return instance;
    }

    public String getReport() {
        return report.getText();
    }

    public void setReport(ReportType report) {
        this.report = report;
    }

    public String getDateRange() {
        return dateRange.getText();
    }

    public void setDateRange(DateRangeType dateRange) {
        this.dateRange = dateRange;
    }

    public List<String> getCampaigns() {
        return campaign;
    }

    public void setCampaigns(List<String> campaign) {
        this.campaign = campaign;
        updateCampaign();
    }

    public void setCampaign(String camp) {
        campaign.add(camp);
        if (campaign.size() > 1)
            updateCampaign();
    }

    public void addCampaigns(List<String> campaigns) {
        this.campaign.addAll(campaigns);
    }

    public ArrayList<String> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<String> product) {
        this.product = product;
    }

    public String getType() {
        return type.getText();
    }

    public void setType(TypeType type) {
        this.type = type;
    }

    private void updateCampaign() {
        if (campaign.contains("All campaigns")) {
            campaign.remove("All campaigns");
        }
    }

}
