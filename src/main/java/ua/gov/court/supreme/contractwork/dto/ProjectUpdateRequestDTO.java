package ua.gov.court.supreme.contractwork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ua.gov.court.supreme.contractwork.enums.ProjectStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProjectUpdateRequestDTO {
    private long id;
    private String justification;
    private BigDecimal contractPrice;
    private LocalDate paymentTo;
    private Long executorId;
    private ProjectStatus projectStatus;
}
