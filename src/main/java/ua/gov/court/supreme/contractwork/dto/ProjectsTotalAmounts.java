package ua.gov.court.supreme.contractwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectsTotalAmounts {
    private int totalQuantity;
    private BigDecimal totalPriceSum;
    private BigDecimal totalRemainingBalance;
    private BigDecimal totalGeneralFund;
    private BigDecimal totalSpecialFund;

    public ProjectsTotalAmounts(int totalQuantity, BigDecimal totalPriceSum, BigDecimal totalGeneralFund, BigDecimal totalSpecialFund) {
        this.totalQuantity = totalQuantity;
        this.totalPriceSum = totalPriceSum;
        this.totalGeneralFund = totalGeneralFund;
        this.totalSpecialFund = totalSpecialFund;
    }
}
