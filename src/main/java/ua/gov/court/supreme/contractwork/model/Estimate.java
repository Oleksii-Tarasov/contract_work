package ua.gov.court.supreme.contractwork.model;

public class Estimate extends BaseEstimateItem {

    public Estimate() {}

    public Estimate(long id, String kekv, String dkCode, String projectName,
                    String unitOfMeasure, double quantity, double price, double totalPrice,
                    double specialFund, double generalFund, String justification, boolean informatization) {
        super(id, kekv, dkCode, projectName, unitOfMeasure, quantity, price,
                totalPrice, specialFund, generalFund, justification, informatization);
    }

    //    without informatization
    public Estimate(long id, String kekv, String dkCode, String projectName,
                    String unitOfMeasure, double quantity, double price, double totalPrice,
                    double specialFund, double generalFund, String justification) {
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
    }

    //    without id for new inserts
    public Estimate(String kekv, String dkCode, String projectName,
                    String unitOfMeasure, double quantity, double price, double totalPrice,
                    double specialFund, double generalFund, String justification) {
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
