package ua.gov.court.supreme.contractwork.model;

public class Purchases {
    private long id;
    private String kekv;
    private String dkCode;
    private String nameProject;
    private String unitOfMeasure;
    private double quantity;
    private double price;
    private double totalPrice;
    private double contractPrice;
    private double remainingBalance;
    private double specialFund;
    private double generalFund;
    private String justification;
    private boolean informatization;

    public Purchases(long id, String kekv, String dkCode, String nameProject, String unitOfMeasure,
                     double quantity, double price, double totalPrice, double contractPrice, double remainingBalance,
                     double specialFund, double generalFund, String justification) {
        this.id = id;
        this.kekv = kekv;
        this.dkCode = dkCode;
        this.nameProject = nameProject;
        this.unitOfMeasure = unitOfMeasure;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.contractPrice = contractPrice;
        this.remainingBalance = remainingBalance;
        this.specialFund = specialFund;
        this.generalFund = generalFund;
        this.justification = justification;
    }

    // КОНСТРУКТОР БЕЗ id (для створення нових записів)
    public Purchases(String kekv, String dkCode, String nameProject, String unitOfMeasure,
                     double quantity, double price, double totalPrice, double contractPrice,
                     double remainingBalance, double specialFund, double generalFund, String justification) {
        this.kekv = kekv;
        this.dkCode = dkCode;
        this.nameProject = nameProject;
        this.unitOfMeasure = unitOfMeasure;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.contractPrice = contractPrice;
        this.remainingBalance = remainingBalance;
        this.specialFund = specialFund;
        this.generalFund = generalFund;
        this.justification = justification;
    }

    // КОНСТРУКТОР із полем інформатизації
    public Purchases(long id, String kekv, String dkCode, String nameProject, String unitOfMeasure,
                     double quantity, double price, double totalPrice, double contractPrice,
                     double remainingBalance, double specialFund, double generalFund, String justification, boolean informatization) {
        this.id = id;
        this.kekv = kekv;
        this.dkCode = dkCode;
        this.nameProject = nameProject;
        this.unitOfMeasure = unitOfMeasure;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.contractPrice = contractPrice;
        this.remainingBalance = remainingBalance;
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

    public String getNameProject() {
        return nameProject;
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

    public double getContractPrice() {
        return contractPrice;
    }

    public double getRemainingBalance() {
        return remainingBalance;
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
