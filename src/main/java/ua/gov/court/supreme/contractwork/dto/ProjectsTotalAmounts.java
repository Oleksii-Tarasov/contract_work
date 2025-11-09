package ua.gov.court.supreme.contractwork.dto;

public class ProjectsTotalAmounts {
    private double totalQuantity;
    private double totalPriceSum;
    private double totalGeneralFund;
    private double totalSpecialFund;

    public ProjectsTotalAmounts(double totalQuantity, double totalPriceSum, double totalGeneralFund, double totalSpecialFund) {
        this.totalQuantity = totalQuantity;
        this.totalPriceSum = totalPriceSum;
        this.totalGeneralFund = totalGeneralFund;
        this.totalSpecialFund = totalSpecialFund;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public double getTotalPriceSum() {
        return totalPriceSum;
    }

    public double getTotalGeneralFund() {
        return totalGeneralFund;
    }

    public double getTotalSpecialFund() {
        return totalSpecialFund;
    }
}
