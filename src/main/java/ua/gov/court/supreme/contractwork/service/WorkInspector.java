package ua.gov.court.supreme.contractwork.service;

import org.apache.poi.ss.usermodel.Workbook;
import ua.gov.court.supreme.contractwork.dao.EstimateDAO;
import ua.gov.court.supreme.contractwork.dao.PurchasesDAO;
import ua.gov.court.supreme.contractwork.dto.ProjectsTotalAmounts;
import ua.gov.court.supreme.contractwork.model.Estimate;
import ua.gov.court.supreme.contractwork.model.Purchases;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class WorkInspector {
    private final EstimateDAO estimateDAO;
    private final PurchasesDAO purchasesDAO;
    private final EstimateExcelConstructor estimateExcelConstructor;

    public WorkInspector() {
        this.estimateDAO = new EstimateDAO();
        this.purchasesDAO = new PurchasesDAO();
        this.estimateExcelConstructor = new EstimateExcelConstructor();
    }

    // Робота із Кошторисом

    public void insertProjectToEstimate(Estimate projectFoEstimate) {
        estimateDAO.insertProject(projectFoEstimate);
    }

    public List<Estimate> getProjectsFromEstimateByKekv(int kekv) {
        return estimateDAO.getProjectsByKekv(kekv);
    }

    public ProjectsTotalAmounts getEstimateTotalAmounts(List<Estimate> projectsFromEstimate) {
        if (projectsFromEstimate == null) {
            return new ProjectsTotalAmounts(0, 0, 0, 0);
        }

        double quantity = projectsFromEstimate.stream().mapToDouble(Estimate::getQuantity).sum();
        double priceSum = projectsFromEstimate.stream().mapToDouble(Estimate::getTotalPrice).sum();
        double generalFund = projectsFromEstimate.stream().mapToDouble(Estimate::getGeneralFund).sum();
        double specialFund = projectsFromEstimate.stream().mapToDouble(Estimate::getSpecialFund).sum();

        return new ProjectsTotalAmounts(quantity, priceSum, generalFund, specialFund);
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

    // Робота із Закупівлями

    public void createPurchasesFromEstimate() {
        purchasesDAO.insertProjectsFromEstimate();
    }

    public List<Purchases> getProjectsFromPurchasesByKekv(int kekv) {
        return purchasesDAO.getProjectsByKekv(kekv);
    }

    public ProjectsTotalAmounts getPurchasesTotalAmounts(List<Purchases> projectsFromPurchases) {
        if (projectsFromPurchases == null) {
            return new ProjectsTotalAmounts(0, 0, 0, 0);
        }

        double quantity = projectsFromPurchases.stream().mapToDouble(Purchases::getQuantity).sum();
        double priceSum = projectsFromPurchases.stream().mapToDouble(Purchases::getTotalPrice).sum();
        double generalFund = projectsFromPurchases.stream().mapToDouble(Purchases::getGeneralFund).sum();
        double specialFund = projectsFromPurchases.stream().mapToDouble(Purchases::getSpecialFund).sum();

        return new ProjectsTotalAmounts(quantity, priceSum, generalFund, specialFund);
    }
}
