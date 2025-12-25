package ua.gov.court.supreme.contractwork.dto;

import ua.gov.court.supreme.contractwork.enums.ProjectStatus;

import java.time.LocalDate;

public class ProjectUpdateRequest {
    private long id;
    private String justification;
    private Double contractPrice;
    private LocalDate paymentTo;
    private Long executorId;
    private ProjectStatus projectStatus;

    public long getId() {
        return id;
    }

    public String getJustification() {
        return justification;
    }

    public Double getContractPrice() {
        return contractPrice;
    }

    public LocalDate getPaymentTo() {
        return paymentTo;
    }

    public Long getExecutorId() {
        return executorId;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }
}
