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
public class CreateEstimateProjectServlet extends BaseWorkServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/estimate/project-estimate-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            String kekv = req.getParameter("kekv");
            String dkCode = req.getParameter("dkCode");
            String projectName = req.getParameter("projectName");
            String unitOfMeasure = req.getParameter("unitOfMeasure");
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            BigDecimal price = new BigDecimal(req.getParameter("price"));
            BigDecimal totalPriceInput = new BigDecimal(req.getParameter("totalPrice"));
            BigDecimal specialFund = new BigDecimal(req.getParameter("specialFund"));
            BigDecimal generalFund = new BigDecimal(req.getParameter("generalFund"));
            String justification = req.getParameter("justification");

            Estimate newEstimateProject = Estimate.builder()
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

            int projectId = contractWorkService.createEstimateProject(newEstimateProject);
            resp.sendRedirect(req.getContextPath() + "/estimate#project-" + projectId);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to create project: " + e.getMessage());
        }
    }
}
