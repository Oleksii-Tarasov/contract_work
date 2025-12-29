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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/purchases/update-project")
public class UpdateProjectFromPurchasesServlet extends BaseWorkServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id =  Long.parseLong(req.getParameter("id"));

        req.setAttribute("projectForUpdate", workInspector.getProjectFromPurchasesById(id));
        req.setAttribute("projectStatuses", ProjectStatus.values());
        req.setAttribute("users", workInspector.getAllUsers());

        req.getRequestDispatcher("/WEB-INF/views/purchases/projectPurchasesEditForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            
            // Статус
            int statusInt = Integer.parseInt(req.getParameter("projectStatus"));
            ProjectStatus projectStatus = ProjectStatus.fromInt(statusInt);
            // Виконавець
            String executorIdStr = req.getParameter("executorId");
            User responsibleExecutor = null;
            if (executorIdStr != null && !executorIdStr.isEmpty()) {
                long userId = Long.parseLong(executorIdStr);
                responsibleExecutor = User.builder().id(userId).build();
            }
            // Сума договору
            BigDecimal contractPrice = parseBigDecimalSafe(req.getParameter("contractPrice"));
            // Дата оплати
            String paymentToStr = req.getParameter("paymentTo");
            LocalDate paymentTo = null;
            if (paymentToStr != null && !paymentToStr.isEmpty()) {
                paymentTo = LocalDate.parse(paymentToStr);
            }
            // Розрахунок залишку
            BigDecimal remainingBalance = totalPrice.subtract(contractPrice);

            // 4. Створення об'єкта
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

            workInspector.updateProjectToPurchases(projectToUpdate);

            resp.sendRedirect(req.getContextPath() + "/purchases?updatedId=" + id + "#project-" + id);

        } catch (NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Помилка у вхідних даних: " + e.getMessage());
        }
    }

    private BigDecimal parseBigDecimalSafe(String value) {
        if (value == null || value.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(value.replace(",", "."));
    }
}
