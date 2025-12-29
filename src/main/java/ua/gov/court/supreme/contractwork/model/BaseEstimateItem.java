package ua.gov.court.supreme.contractwork.model;

public abstract class BaseEstimateItem {
    protected long id;
    protected String kekv;
    protected String dkCode;
    protected String projectName;
    protected String unitOfMeasure;
    protected double quantity;
    protected double price;
    protected double totalPrice;
    protected double specialFund;
    protected double generalFund;
    protected String justification;
    protected boolean informatization;

    public BaseEstimateItem() {}

    public BaseEstimateItem(long id, String kekv, String dkCode, String projectName, String unitOfMeasure, double quantity, double price, double totalPrice, double specialFund, double generalFund, String justification, boolean informatization) {
        this.id = id;
        this.kekv = kekv;
        this.dkCode = dkCode;
        this.projectName = projectName;
        this.unitOfMeasure = unitOfMeasure;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.specialFund = specialFund;
        this.generalFund = generalFund;
        this.justification = justification;
        this.informatization = informatization;
    }

    public long getId() {
        return id;
    }

    public String getKekv() {
        return kekv;
    }

    public String getDkCode() {
        return dkCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getSpecialFund() {
        return specialFund;
    }

    public double getGeneralFund() {
        return generalFund;
    }

    public String getJustification() {
        return justification;
    }

    public boolean isInformatization() {
        return informatization;
    }
}
