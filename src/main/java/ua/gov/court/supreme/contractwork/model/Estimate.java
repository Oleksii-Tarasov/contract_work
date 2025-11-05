package ua.gov.court.supreme.contractwork.model;

public class Estimate {
    private long id;
    private String kekv;
    private String dkCode;
    private String nameProject;
    private String unitOfMeasure;
    private double quantity;
    private double price;
    private double totalPrice;
    private double specialFund;
    private double generalFund;
    private String justification;
    private boolean informatization;

    public Estimate() {
    }

    public Estimate(long id, String kekv, String dkCode, String nameProject,
                    String unitOfMeasure, double quantity, double price, double totalPrice,
                    double specialFund, double generalFund, String justification) {
        this.id = id;
        this.kekv = kekv;
        this.dkCode = dkCode;
        this.nameProject = nameProject;
        this.unitOfMeasure = unitOfMeasure;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.specialFund = specialFund;
        this.generalFund = generalFund;
        this.justification = justification;
    }

    // КОНСТРУКТОР БЕЗ id (для створення нових записів)
    public Estimate(String kekv, String dkCode, String nameProject,
                    String unitOfMeasure, double quantity, double price, double totalPrice,
                    double specialFund, double generalFund, String justification) {
        this.kekv = kekv;
        this.dkCode = dkCode;
        this.nameProject = nameProject;
        this.unitOfMeasure = unitOfMeasure;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.specialFund = specialFund;
        this.generalFund = generalFund;
        this.justification = justification;
    }

    // КОНСТРУКТОР із полем для інформатизації
    public Estimate(long id, String kekv, String dkCode, String nameProject,
                    String unitOfMeasure, double quantity, double price, double totalPrice,
                    double specialFund, double generalFund, String justification, boolean informatization) {
        this.id = id;
        this.kekv = kekv;
        this.dkCode = dkCode;
        this.nameProject = nameProject;
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

    public void setId(long id) {
        this.id = id;
    }

    public String getKekv() {
        return kekv;
    }

    public void setKekv(String kekv) {
        this.kekv = kekv;
    }

    public String getDkCode() {
        return dkCode;
    }

    public void setDkCode(String dkCode) {
        this.dkCode = dkCode;
    }

    public String getNameProject() {
        return nameProject;
    }

    public void setNameProject(String nameProject) {
        this.nameProject = nameProject;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getSpecialFund() {
        return specialFund;
    }

    public void setSpecialFund(double specialFund) {
        this.specialFund = specialFund;
    }

    public double getGeneralFund() {
        return generalFund;
    }

    public void setGeneralFund(double generalFund) {
        this.generalFund = generalFund;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public boolean isInformatization() {
        return informatization;
    }
}
