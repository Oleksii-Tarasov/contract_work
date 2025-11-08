package ua.gov.court.supreme.contractwork.service;

import org.apache.poi.ss.usermodel.Workbook;
import ua.gov.court.supreme.contractwork.dao.EstimateDAO;
import ua.gov.court.supreme.contractwork.dto.EstimateTotalAmounts;
import ua.gov.court.supreme.contractwork.model.Estimate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class WorkInspector {
    private final EstimateDAO estimateDAO;
    private final EstimateExcelConstructor estimateExcelConstructor;

    public WorkInspector() {
        this.estimateDAO = new EstimateDAO();
        this.estimateExcelConstructor = new EstimateExcelConstructor();
    }

    public void insertProjectToEstimate(Estimate projectFoEstimate) {
        estimateDAO.insertProject(projectFoEstimate);
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

    public byte[] createEstimateExcelFile() {
        try (Workbook workbook = estimateExcelConstructor.createWorkbook(estimateDAO);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            workbook.write(baos);
            return baos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Помилка при формуванні Excel файлу", e);
        }
    }
}
