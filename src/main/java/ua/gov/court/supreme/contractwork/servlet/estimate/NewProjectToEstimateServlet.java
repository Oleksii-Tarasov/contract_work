package ua.gov.court.supreme.contractwork.servlet.estimate;

import ua.gov.court.supreme.contractwork.model.Estimate;
import ua.gov.court.supreme.contractwork.servlet.BaseWorkServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/estimate/new-project")
public class NewProjectToEstimateServlet extends BaseWorkServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/estimate/projectEstimateForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String kekv = req.getParameter("kekv");
        String dkCode = req.getParameter("dkCode");
        String nameProject = req.getParameter("nameProject");
        String unitOfMeasure = req.getParameter("unitOfMeasure");
        double quantity = Double.parseDouble(req.getParameter("quantity"));
        double price = Double.parseDouble(req.getParameter("price"));
        double totalPrice = Double.parseDouble(req.getParameter("totalPrice"));
        double specialFund = Double.parseDouble(req.getParameter("specialFund"));
        double generalFund = Double.parseDouble(req.getParameter("generalFund"));
        String justification = req.getParameter("justification");

        Estimate newProjectForEstimate = new Estimate(kekv, dkCode, nameProject, unitOfMeasure,
                quantity, price, totalPrice, specialFund, generalFund, justification);

        try {
            int projectId = workInspector.insertProjectToEstimate(newProjectForEstimate);
            resp.sendRedirect(req.getContextPath() + "/estimate#project-" + projectId);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to create project");
        }
    }
}
