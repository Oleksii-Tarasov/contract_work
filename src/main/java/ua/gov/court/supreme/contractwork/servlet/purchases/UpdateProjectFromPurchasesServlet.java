package ua.gov.court.supreme.contractwork.servlet.purchases;

import ua.gov.court.supreme.contractwork.model.Purchases;
import ua.gov.court.supreme.contractwork.model.User;
import ua.gov.court.supreme.contractwork.servlet.BaseWorkServlet;
import ua.gov.court.supreme.contractwork.enums.ProjectStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
            double quantity = Double.parseDouble(req.getParameter("quantity"));
            double price = Double.parseDouble(req.getParameter("price"));
            double totalPrice = quantity * price;
            double generalFund = parseDoubleSafe(req.getParameter("generalFund"));
            double specialFund = totalPrice - generalFund;
            if (specialFund < 0) specialFund = 0;
            // Статус
            int statusInt = Integer.parseInt(req.getParameter("projectStatus"));
            ProjectStatus projectStatus = ProjectStatus.fromInt(statusInt);
            // Виконавець
            String executorIdStr = req.getParameter("executorId");
            User responsibleExecutor = null;
            if (executorIdStr != null && !executorIdStr.isEmpty()) {
                long userId = Long.parseLong(executorIdStr);
                responsibleExecutor = new User(userId, null, null, null, null, null);
            }
            // Сума договору
            double contractPrice = parseDoubleSafe(req.getParameter("contractPrice"));
            // Дата оплати
            String paymentToStr = req.getParameter("paymentTo");
            LocalDate paymentTo = null;
            if (paymentToStr != null && !paymentToStr.isEmpty()) {
                paymentTo = LocalDate.parse(paymentToStr);
            }
            // Розрахунок залишку
            double remainingBalance = totalPrice - contractPrice;

            // 4. Створення об'єкта
            Purchases projectToUpdate = new Purchases(
                    id,
                    kekv,
                    dkCode,
                    projectName,
                    unitOfMeasure,
                    quantity,
                    price,
                    totalPrice,
                    contractPrice,
                    remainingBalance,
                    paymentTo,
                    specialFund,
                    generalFund,
                    justification,
                    false,
                    responsibleExecutor,
                    projectStatus
            );

            workInspector.updateProjectToPurchases(projectToUpdate);

            resp.sendRedirect(req.getContextPath() + "/purchases?updatedId=" + id + "#project-" + id);

        } catch (NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Помилка у вхідних даних: " + e.getMessage());
        }
    }

    private double parseDoubleSafe(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(value.replace(",", "."));
    }
}
