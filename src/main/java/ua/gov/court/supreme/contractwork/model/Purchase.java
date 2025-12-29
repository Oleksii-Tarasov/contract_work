package ua.gov.court.supreme.contractwork.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.gov.court.supreme.contractwork.enums.ProjectStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase extends BaseEstimateItem {
    private BigDecimal contractPrice;
    private BigDecimal remainingBalance;
    private LocalDate paymentTo;
    private User responsibleExecutor;
    private ProjectStatus projectStatus;
}
