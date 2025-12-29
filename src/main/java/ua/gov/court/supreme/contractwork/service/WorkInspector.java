package ua.gov.court.supreme.contractwork.service;

import org.apache.poi.ss.usermodel.Workbook;
import ua.gov.court.supreme.contractwork.dao.EstimateDAO;
import ua.gov.court.supreme.contractwork.dao.PurchaseDAO;
import ua.gov.court.supreme.contractwork.dao.UserDAO;
import ua.gov.court.supreme.contractwork.dto.ProjectsTotalAmounts;
import ua.gov.court.supreme.contractwork.enums.ProjectStatus;
import ua.gov.court.supreme.contractwork.model.Estimate;
import ua.gov.court.supreme.contractwork.model.Purchase;
import ua.gov.court.supreme.contractwork.model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class WorkInspector {
    private final EstimateDAO estimateDAO;
    private final PurchaseDAO purchaseDAO;
    private final UserDAO userDAO;
    private final EstimateExcelConstructor estimateExcelConstructor;

    public WorkInspector() {
        this.estimateDAO = new EstimateDAO();
        this.purchaseDAO = new PurchaseDAO();
        this.userDAO = new UserDAO();
        this.estimateExcelConstructor = new EstimateExcelConstructor();
    }

    // ESTIMATE

    public int insertProjectToEstimate(Estimate projectFoEstimate) {
        return estimateDAO.insertProject(projectFoEstimate); // Повертає id створеного запису
    }

    public List<Estimate> getProjectsFromEstimateByKekv(int kekv) {
        return estimateDAO.getProjectsByKekv(kekv);
    }

    public ProjectsTotalAmounts getEstimateTotalAmounts(List<Estimate> projectsFromEstimate) {
        if (projectsFromEstimate == null) {
            return new ProjectsTotalAmounts(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        int quantity = projectsFromEstimate.stream().mapToInt(Estimate::getQuantity).sum();
        BigDecimal priceSum = projectsFromEstimate.stream().map(Estimate::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal generalFund = projectsFromEstimate.stream().map(Estimate::getGeneralFund).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal specialFund = projectsFromEstimate.stream().map(Estimate::getSpecialFund).reduce(BigDecimal.ZERO, BigDecimal::add);

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

    // EXCEL FILE

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
        purchaseDAO.insertProjectsFromEstimate();
    }

    public List<Purchase> getProjectsFromPurchasesByKekv(int kekv) {
        return purchaseDAO.getProjectsByKekv(kekv);
    }

    public Purchase getProjectFromPurchasesById(long id) {
        return purchaseDAO.getProjectById(id);
    }

    public void updateProjectToPurchases(Purchase updatedProject) {
        purchaseDAO.updateProjectToPurchases(updatedProject);
    }

    public ProjectsTotalAmounts getPurchasesTotalAmounts(List<Purchase> projectsFromPurchases) {
        if (projectsFromPurchases == null) {
            return new ProjectsTotalAmounts(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        int quantity = projectsFromPurchases.stream().mapToInt(Purchase::getQuantity).sum();
        BigDecimal priceSum = projectsFromPurchases.stream().map(Purchase::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal remainingBalance = projectsFromPurchases.stream().map(Purchase::getRemainingBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal generalFund = projectsFromPurchases.stream().map(Purchase::getGeneralFund).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal specialFund = projectsFromPurchases.stream().map(Purchase::getSpecialFund).reduce(BigDecimal.ZERO, BigDecimal::add);


        return new ProjectsTotalAmounts(quantity, priceSum, remainingBalance, generalFund, specialFund);
    }

    public ProjectStatus[] getProjectStatuses() {
        return ProjectStatus.values();
    }

    public void updateProjectStatus(long projectId, ProjectStatus projectStatus) {
        purchaseDAO.updateProjectStatus(projectId, projectStatus);
    }

    public void updateJustification(long projectId, String justification) {
        purchaseDAO.updateJustification(projectId, justification);
    }

    public void updateContractPrice(long projectId, BigDecimal contractPrice) {
        Purchase purchase = purchaseDAO.getProjectById(projectId);
        BigDecimal remainingBalance = purchase.getTotalPrice().subtract(contractPrice);

        purchaseDAO.updateContractPrice(projectId, contractPrice, remainingBalance);
    }

    public void updatePaymentDueDate(long projectId, LocalDate paymentDate) {
        purchaseDAO.updatePaymentDueDate(projectId, paymentDate);
    }

    // USERS

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public long getExecutorIdFromProject(long projectId) {
        return purchaseDAO.getExecutorIdFromProject(projectId);
    }

    public void updateProjectExecutor(long projectId, Long executorId) {
        purchaseDAO.updateProjectExecutor(projectId, executorId);
    }
}
