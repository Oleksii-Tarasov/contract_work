package ua.gov.court.supreme.contractwork.servlet.purchases;

import ua.gov.court.supreme.contractwork.model.Purchase;
import ua.gov.court.supreme.contractwork.model.User;
import ua.gov.court.supreme.contractwork.servlet.BaseWorkServlet;
import ua.gov.court.supreme.contractwork.enums.ProjectStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/purchases/update-project")
public class UpdatePurchaseProjectServlet extends BaseWorkServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id =  Long.parseLong(req.getParameter("id"));

        req.setAttribute("projectForUpdate", contractWorkService.getPurchaseProjectById(id));
        req.setAttribute("projectStatuses", ProjectStatus.values());
        req.setAttribute("users", contractWorkService.getAllUsers());

        req.getRequestDispatcher("/WEB-INF/views/purchases/project-purchases-edit-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            long id = Long.parseLong(req.getParameter("id"));
            String kekv = req.getParameter("kekv");
            String dkCode = req.getParameter("dkCode");
            String projectName = req.getParameter("projectName");
            String justification = req.getParameter("justification");
            String unitOfMeasure = req.getParameter("unitOfMeasure");
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            BigDecimal price = parseBigDecimalSafe(req.getParameter("price"));
            BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));
            BigDecimal generalFund = parseBigDecimalSafe(req.getParameter("generalFund"));
            BigDecimal specialFund = totalPrice.subtract(generalFund);
            if (specialFund.compareTo(BigDecimal.ZERO) < 0) specialFund = BigDecimal.ZERO;

            // Status
            int statusInt = Integer.parseInt(req.getParameter("projectStatus"));
            ProjectStatus projectStatus = ProjectStatus.fromInt(statusInt);
            // Executor
            String executorIdParam = req.getParameter("executorId");
            User responsibleExecutor = null;
            if (executorIdParam != null && !executorIdParam.isEmpty()) {
                long userId = Long.parseLong(executorIdParam);
                responsibleExecutor = User.builder().id(userId).build();
            }
            BigDecimal contractPrice = parseBigDecimalSafe(req.getParameter("contractPrice"));
            String paymentToParam = req.getParameter("paymentTo");
            LocalDate paymentTo = null;
            if (paymentToParam != null && !paymentToParam.isEmpty()) {
                paymentTo = LocalDate.parse(paymentToParam);
            }
            // Calculate Remaining Balance
            BigDecimal remainingBalance = totalPrice.subtract(contractPrice);

            Purchase projectToUpdate = Purchase.builder()
                    .id(id)
                    .kekv(kekv)
                    .dkCode(dkCode)
                    .projectName(projectName)
                    .unitOfMeasure(unitOfMeasure)
                    .quantity(quantity)
                    .price(price)
                    .totalPrice(totalPrice)
                    .contractPrice(contractPrice)
                    .remainingBalance(remainingBalance)
                    .paymentTo(paymentTo)
                    .specialFund(specialFund)
                    .generalFund(generalFund)
                    .justification(justification)
                    .informatization(false)
                    .responsibleExecutor(responsibleExecutor)
                    .projectStatus(projectStatus)
                    .build();

            contractWorkService.updatePurchaseProject(projectToUpdate);

            resp.sendRedirect(req.getContextPath() + "/purchases?updatedId=" + id + "#project-" + id);

        } catch (NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data: " + e.getMessage());
        }
    }

    private BigDecimal parseBigDecimalSafe(String value) {
        if (value == null || value.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value.replace(",", "."));
    }
}
