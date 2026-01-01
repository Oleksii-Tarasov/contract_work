package ua.gov.court.supreme.contractwork.service;

import org.apache.poi.ss.usermodel.Workbook;
import ua.gov.court.supreme.contractwork.dao.EstimateDAO;
import ua.gov.court.supreme.contractwork.dao.PurchaseDAO;
import ua.gov.court.supreme.contractwork.dao.UserDAO;
import ua.gov.court.supreme.contractwork.dto.ProjectsTotalsDTO;
import ua.gov.court.supreme.contractwork.enums.ProjectStatus;
import ua.gov.court.supreme.contractwork.model.Estimate;
import ua.gov.court.supreme.contractwork.model.Purchase;
import ua.gov.court.supreme.contractwork.model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ContractWorkService {
    private final EstimateDAO estimateDAO;
    private final PurchaseDAO purchaseDAO;
    private final UserDAO userDAO;
    private final EstimateExcelConstructor estimateExcelConstructor;

    public ContractWorkService() {
        this.estimateDAO = new EstimateDAO();
        this.purchaseDAO = new PurchaseDAO();
        this.userDAO = new UserDAO();
        this.estimateExcelConstructor = new EstimateExcelConstructor();
    }

    // --- ESTIMATE ---

    public int createEstimateProject(Estimate newEstimateProject) {
        return estimateDAO.save(newEstimateProject); // Returns the ID of the created record
    }

    public List<Estimate> getEstimateProjectsByKekv(int kekv) {
        return estimateDAO.findAllByKekv(kekv);
    }

    public ProjectsTotalsDTO calculateEstimateTotals(List<Estimate> estimateProjects) {
        if (estimateProjects == null) {
            return new ProjectsTotalsDTO(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        int quantity = estimateProjects.stream().mapToInt(Estimate::getQuantity).sum();
        BigDecimal priceSum = estimateProjects.stream().map(Estimate::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal generalFund = estimateProjects.stream().map(Estimate::getGeneralFund).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal specialFund = estimateProjects.stream().map(Estimate::getSpecialFund).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ProjectsTotalsDTO(quantity, priceSum, generalFund, specialFund);
    }

    public void deleteEstimateProject(long id) {
        estimateDAO.delete(id);
    }

    public Estimate getEstimateProjectById(long id) {
        return estimateDAO.findById(id);
    }

    public void updateEstimateProject(Estimate projectToUpdate) {
        estimateDAO.update(projectToUpdate);
    }

    public byte[] createEstimateExcelFile() {
        try (Workbook workbook = estimateExcelConstructor.createWorkbook(estimateDAO);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            workbook.write(baos);
            return baos.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error creating Excel file", e);
        }
    }

    // --- PURCHASES ---

    public void createPurchasesFromEstimate() {
        purchaseDAO.importFromEstimates();
    }

    public List<Purchase> getPurchaseProjectsByKekv(int kekv) {
        return purchaseDAO.findAllByKekv(kekv);
    }

    public Purchase getPurchaseProjectById(long id) {
        return purchaseDAO.findById(id);
    }

    public void updatePurchaseProject(Purchase projectToUpdate) {
        purchaseDAO.update(projectToUpdate);
    }
    
    public void deletePurchaseProject(long id) {
        purchaseDAO.delete(id);
    }

    public ProjectsTotalsDTO calculatePurchaseTotals(List<Purchase> purchaseProjects) {
        if (purchaseProjects == null) {
            return new ProjectsTotalsDTO(0, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        int quantity = purchaseProjects.stream().mapToInt(Purchase::getQuantity).sum();
        BigDecimal priceSum = purchaseProjects.stream().map(Purchase::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal remainingBalance = purchaseProjects.stream().map(Purchase::getRemainingBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal generalFund = purchaseProjects.stream().map(Purchase::getGeneralFund).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal specialFund = purchaseProjects.stream().map(Purchase::getSpecialFund).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ProjectsTotalsDTO(quantity, priceSum, remainingBalance, generalFund, specialFund);
    }

    public ProjectStatus[] getProjectStatuses() {
        return ProjectStatus.values();
    }

    public void updateProjectStatus(long projectId, ProjectStatus projectStatus) {
        purchaseDAO.updateStatus(projectId, projectStatus);
    }

    public void updateJustification(long projectId, String justification) {
        purchaseDAO.updateJustification(projectId, justification);
    }

    public void updateContractPrice(long projectId, BigDecimal contractPrice) {
        Purchase purchase = purchaseDAO.findById(projectId);
        BigDecimal remainingBalance = purchase.getTotalPrice().subtract(contractPrice);

        purchaseDAO.updateContractPrice(projectId, contractPrice, remainingBalance);
    }

    public void updatePaymentDueDate(long projectId, LocalDate paymentDate) {
        purchaseDAO.updatePaymentDueDate(projectId, paymentDate);
    }

    // --- USERS ---

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public Long findExecutorIdByProjectId(long id) {
        return purchaseDAO.findExecutorIdByProjectId(id);
    }

    public void updateProjectExecutor(long projectId, Long executorId) {
        purchaseDAO.updateExecutor(projectId, executorId);
    }
}
