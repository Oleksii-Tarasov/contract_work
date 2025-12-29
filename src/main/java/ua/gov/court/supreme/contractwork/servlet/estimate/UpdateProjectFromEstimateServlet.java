package ua.gov.court.supreme.contractwork.servlet.estimate;

import ua.gov.court.supreme.contractwork.model.Estimate;
import ua.gov.court.supreme.contractwork.servlet.BaseWorkServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/estimate/update-project")
public class UpdateProjectFromEstimateServlet extends BaseWorkServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));

        req.setAttribute("projectForUpdate", workInspector.getProjectFromEstimateById(id));

        req.getRequestDispatcher("/WEB-INF/views/estimate/projectEstimateEditForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            long id = Long.parseLong(req.getParameter("id"));
            String kekv = req.getParameter("kekv");
            String dkCode = req.getParameter("dkCode");
            String projectName = req.getParameter("projectName");
            String unitOfMeasure = req.getParameter("unitOfMeasure");
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            BigDecimal totalPrice = new BigDecimal(req.getParameter("totalPrice"));
            BigDecimal specialFund = new BigDecimal(req.getParameter("specialFund"));
            BigDecimal generalFund = new BigDecimal(req.getParameter("generalFund"));
            String justification = req.getParameter("justification");

            Estimate updatedProject = Estimate.builder()
                    .id(id)
                    .kekv(kekv)
                    .dkCode(dkCode)
                    .projectName(projectName)
                    .unitOfMeasure(unitOfMeasure)
                    .quantity(quantity)
                    .price(price)
                    .totalPrice(totalPrice)
                    .specialFund(specialFund)
                    .generalFund(generalFund)
                    .justification(justification)
                    .build();

            workInspector.updateProjectToEstimate(updatedProject);

            resp.sendRedirect(req.getContextPath() + "/estimate#project-" + id);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format: " + e.getMessage());
        }
    }
}
