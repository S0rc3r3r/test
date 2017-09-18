package com.muso.persistence;

import java.util.ArrayList;
import java.util.List;

import com.muso.enums.CategoryType;
import com.muso.enums.DateRangeType;
import com.muso.enums.RegionType;
import com.muso.enums.ReportType;
import com.muso.enums.TypeType;

public class PersistenceManager {

    private ReportType report;
    private DateRangeType dateRange;
    private ArrayList<RegionType> region;
    private ArrayList<CategoryType> category;
    private List<String> campaign = new ArrayList<String>();
    private ArrayList<String> product = new ArrayList<String>();
    private ArrayList<String> type = new ArrayList<String>();
    private static volatile PersistenceManager instance = null;

    private PersistenceManager() {
        this.report = ReportType.Infringement_Summary;
        this.dateRange = DateRangeType.LAST_2_MONTHS;
        this.type.add(TypeType.ALL.getText());
        this.campaign.add("All campaigns");
        this.product.add("All products");

    }

    public static PersistenceManager getInstance() {
        if (instance == null) {
            synchronized (PersistenceManager.class) {
                if (instance == null) {
                    instance = new PersistenceManager();
                }
            }
        }
        return instance;
    }

    public void clear() {
        instance = new PersistenceManager();
    }

    public ArrayList<RegionType> getRegions() {
        return region;
    }

    public ArrayList<CategoryType> getCategories() {
        return category;
    }

    public void setRegion(RegionType region) {
        updateRegions(region);
    }

    public void setCategory(CategoryType category) {
        updateCategories(category);
    }

    public ReportType getReport() {
        return report;
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
        updateCampaign(true);
    }

    public void addCampaign(String camp) {
        campaign.add(camp);
        if (campaign.size() > 1)
            updateCampaign(true);
    }

    public void removeCampaign(String camp) {
        campaign.remove(camp);
        if (campaign.size() < 1)
            updateCampaign(false);
    }

    public void addCampaigns(List<String> campaigns) {
        this.campaign.addAll(campaigns);
    }

    public ArrayList<String> getProduct() {
        return product;
    }

    public void setProducts(ArrayList<String> product) {
        this.product = product;
        updateProduct(true);
    }

    public void addProduct(String product) {
        this.product.add(product);
        if (this.product.size() > 1)
            updateProduct(true);
    }

    public void removeProduct(String product) {
        this.product.remove(product);
        if (this.product.size() < 1)
            updateProduct(false);
    }

    public ArrayList<String> getType() {
        return type;
    }

    public void setTypes(ArrayList<String> types) {
        this.type = types;
        if (this.type.size() > 1)
            updateType(true);
    }

    public void setType(String type) {
        this.type.add(type);
        if (this.type.size() > 1)
            updateType(true);
    }

    public void removeType(String type) {
        this.type.remove(type);
        if (this.type.size() < 1)
            updateType(false);
    }

    private void updateCampaign(boolean isAnyCampaignSelected) {
        if (isAnyCampaignSelected) {
            campaign.remove("All campaigns");
        } else {
            campaign.add("All campaigns");
        }
    }

    private void updateProduct(boolean isAnyProductSelected) {
        if (isAnyProductSelected) {
            product.remove("All products");
        } else {
            product.add("All products");
        }
    }

    private void updateType(boolean isAnyTypeSelected) {
        if (isAnyTypeSelected) {
            type.remove(TypeType.ALL.getText());
        } else {
            type.add(TypeType.ALL.getText());
        }
    }

    private void updateRegions(RegionType region) {

    }

    private void updateCategories(CategoryType category) {

    }

}
