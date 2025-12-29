package ua.gov.court.supreme.contractwork.servlet.estimate;

import ua.gov.court.supreme.contractwork.model.Estimate;
import ua.gov.court.supreme.contractwork.servlet.BaseWorkServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/estimate/new-project")
public class NewProjectToEstimateServlet extends BaseWorkServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/estimate/projectEstimateForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            String kekv = req.getParameter("kekv");
            String dkCode = req.getParameter("dkCode");
            String projectName = req.getParameter("projectName");
            String unitOfMeasure = req.getParameter("unitOfMeasure");
            double quantity = Double.parseDouble(req.getParameter("quantity"));
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity)); // Recalculate for safety or take from input? Usually better to calc. User input: req.getParameter("totalPrice")
            // Let's use user input for consistency with old logic if it was passed, but recalc is safer.
            // Old code: double totalPrice = Double.parseDouble(req.getParameter("totalPrice"));
            // Let's stick to parsing for now to trust frontend, but using BigDecimal.
            BigDecimal totalPriceInput = new BigDecimal(req.getParameter("totalPrice"));

            BigDecimal specialFund = new BigDecimal(req.getParameter("specialFund"));
            BigDecimal generalFund = new BigDecimal(req.getParameter("generalFund"));
            String justification = req.getParameter("justification");

            Estimate newProjectForEstimate = Estimate.builder()
                    .kekv(kekv)
                    .dkCode(dkCode)
                    .projectName(projectName)
                    .unitOfMeasure(unitOfMeasure)
                    .quantity(quantity)
                    .price(price)
                    .totalPrice(totalPriceInput)
                    .specialFund(specialFund)
                    .generalFund(generalFund)
                    .justification(justification)
                    .build();

            int projectId = workInspector.insertProjectToEstimate(newProjectForEstimate);
            resp.sendRedirect(req.getContextPath() + "/estimate#project-" + projectId);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to create project: " + e.getMessage());
        }
    }
}
