package ua.gov.court.supreme.contractwork.service;

import org.apache.poi.ss.usermodel.Workbook;
import ua.gov.court.supreme.contractwork.dao.EstimateDAO;
import ua.gov.court.supreme.contractwork.dao.PurchasesDAO;
import ua.gov.court.supreme.contractwork.dao.UserDAO;
import ua.gov.court.supreme.contractwork.dto.ProjectsTotalAmounts;
import ua.gov.court.supreme.contractwork.enums.ProjectStatus;
import ua.gov.court.supreme.contractwork.model.Estimate;
import ua.gov.court.supreme.contractwork.model.Purchases;
import ua.gov.court.supreme.contractwork.model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class WorkInspector {
    private final EstimateDAO estimateDAO;
    private final PurchasesDAO purchasesDAO;
    private final UserDAO userDAO;
    private final EstimateExcelConstructor estimateExcelConstructor;

    public WorkInspector() {
        this.estimateDAO = new EstimateDAO();
        this.purchasesDAO = new PurchasesDAO();
        this.userDAO = new UserDAO();
        this.estimateExcelConstructor = new EstimateExcelConstructor();
    }

    // Estimate

    public int insertProjectToEstimate(Estimate projectFoEstimate) {
        return estimateDAO.insertProject(projectFoEstimate); // Повертає id створеного запису
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

    // PURCHASES

    public void createPurchasesFromEstimate() {
        purchasesDAO.insertProjectsFromEstimate();
    }

    public List<Purchases> getProjectsFromPurchasesByKekv(int kekv) {
        return purchasesDAO.getProjectsByKekv(kekv);
    }

    public ProjectsTotalAmounts getPurchasesTotalAmounts(List<Purchases> projectsFromPurchases) {
        if (projectsFromPurchases == null) {
            return new ProjectsTotalAmounts(0, 0, 0, 0, 0);
        }

        double quantity = projectsFromPurchases.stream().mapToDouble(Purchases::getQuantity).sum();
        double priceSum = projectsFromPurchases.stream().mapToDouble(Purchases::getTotalPrice).sum();
        double remainingBalance = projectsFromPurchases.stream().mapToDouble(Purchases::getRemainingBalance).sum();
        double generalFund = projectsFromPurchases.stream().mapToDouble(Purchases::getGeneralFund).sum();
        double specialFund = projectsFromPurchases.stream().mapToDouble(Purchases::getSpecialFund).sum();


        return new ProjectsTotalAmounts(quantity, priceSum, remainingBalance, generalFund, specialFund);
    }

    public ProjectStatus[] getProjectStatuses() {
        return ProjectStatus.values();
    }

    public void updateProjectStatus(long projectId, ProjectStatus projectStatus) {
        purchasesDAO.updateProjectStatus(projectId, projectStatus);
    }

    public void updateJustification(long projectId, String justification) {
        purchasesDAO.updateJustification(projectId, justification);
    }

    public void updateContractPrice(long projectId, double contractPrice) {
        purchasesDAO.updateContractPrice(projectId, contractPrice);
    }

    // Робота із користувачами

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public long getExecutorIdFromProject(long projectId) {
        return purchasesDAO.getExecutorIdFromProject(projectId);
    }

    public void updateProjectExecutor(long projectId, Long executorId) {
        purchasesDAO.updateProjectExecutor(projectId, executorId);
    }
}
