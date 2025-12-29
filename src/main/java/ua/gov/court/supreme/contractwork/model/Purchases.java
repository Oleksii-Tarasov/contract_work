package ua.gov.court.supreme.contractwork.model;

import ua.gov.court.supreme.contractwork.enums.ProjectStatus;

import java.time.LocalDate;

public class Purchases extends BaseEstimateItem {
    private double contractPrice;
    private double remainingBalance;
    private LocalDate paymentTo;
    private User responsibleExecutor;
    private ProjectStatus projectStatus;

    public Purchases(long id, String kekv, String dkCode, String projectName, String unitOfMeasure,
                     double quantity, double price, double totalPrice, double contractPrice,
                     double remainingBalance, LocalDate paymentTo, double specialFund, double generalFund,
                     String justification, boolean informatization, User responsibleExecutor, ProjectStatus projectStatus) {
        super(id, kekv, dkCode, projectName, unitOfMeasure, quantity, price,
                totalPrice, specialFund, generalFund, justification, informatization);
        this.contractPrice = contractPrice;
        this.remainingBalance = remainingBalance;
        this.paymentTo = paymentTo;
        this.responsibleExecutor = responsibleExecutor;
        this.projectStatus = projectStatus;
    }

    //    without id for new inserts
    public Purchases(String kekv, String dkCode, String projectName, String unitOfMeasure,
                     double quantity, double price, double totalPrice, double contractPrice,
                     double remainingBalance, double specialFund, double generalFund, String justification) {
        this.kekv = kekv;
        this.dkCode = dkCode;
        this.projectName = projectName;
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

    public double getContractPrice() {
        return contractPrice;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public LocalDate getPaymentTo() {
        return paymentTo;
    }

    public User getResponsibleExecutor() {
        return responsibleExecutor;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }
}
