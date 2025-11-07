package ua.gov.court.supreme.contractwork.service;

import ua.gov.court.supreme.contractwork.dao.EstimateDAO;
import ua.gov.court.supreme.contractwork.dto.EstimateTotalAmounts;
import ua.gov.court.supreme.contractwork.model.Estimate;

import java.util.List;

public class WorkInspector {
    private final EstimateDAO estimateDAO;
    private final  EstimateExcelGenerator estimateExcelGenerator;

    public WorkInspector() {
        this.estimateDAO = new EstimateDAO();
        this.estimateExcelGenerator = new EstimateExcelGenerator();
    }

    public List<Estimate> getProjectsFromEstimateByKekv(int kekv) {
        return estimateDAO.getProjectsByKekv(kekv);
    }

    public EstimateTotalAmounts getTotalAmountsForEstimate(List<Estimate> projectsFromEstimate) {
        if (projectsFromEstimate == null) {
            return new EstimateTotalAmounts(0, 0, 0, 0); // або обробіть null іншим чином
        }

        double quantity = projectsFromEstimate.stream().mapToDouble(Estimate::getQuantity).sum();
        double priceSum = projectsFromEstimate.stream().mapToDouble(Estimate::getTotalPrice).sum();
        double generalFund = projectsFromEstimate.stream().mapToDouble(Estimate::getGeneralFund).sum();
        double specialFund = projectsFromEstimate.stream().mapToDouble(Estimate::getSpecialFund).sum();

        return new EstimateTotalAmounts(quantity, priceSum, generalFund, specialFund);
    }

    public void deleteProjectFromEstimate(long projectId) {
        estimateDAO.deleteProjectFromEstimateById(projectId);
    }

    public Estimate getProjectFromEstimateById(long id) {
        return estimateDAO.getProjectById(id);
    }

    public void updateProjectToEstimate(Estimate updatedProject) {
        estimateDAO.updateProjectToEstimate(updatedProject);
    }

    public void generateEstimateExcelFile() {
        estimateExcelGenerator.generate("C:/temp/koshtorys.xlsx");
    }
}
