package ua.gov.court.supreme.contractwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEstimateItem {
    protected long id;
    protected String kekv;
    protected String dkCode;
    protected String projectName;
    protected String unitOfMeasure;
    protected double quantity; // Quantity remains double as discussed
    protected BigDecimal price;
    protected BigDecimal totalPrice;
    protected BigDecimal specialFund;
    protected BigDecimal generalFund;
    protected String justification;
    protected boolean informatization;
}
