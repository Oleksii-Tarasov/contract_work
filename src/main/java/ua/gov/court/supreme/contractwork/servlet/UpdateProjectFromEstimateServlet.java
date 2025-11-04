package ua.gov.court.supreme.contractwork.servlet;

import ua.gov.court.supreme.contractwork.model.Estimate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update-project")
public class UpdateProjectFromEstimateServlet extends BaseWorkServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));

        req.setAttribute("projectForUpdate", workInspector.getProjectFromEstimateById(id));

        req.getRequestDispatcher("/WEB-INF/views/editProjectEstimate.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        long id = Long.parseLong(req.getParameter("id"));
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

        Estimate updatedProject = new Estimate(id, kekv, dkCode, nameProject, unitOfMeasure,
                quantity, price, totalPrice, specialFund, generalFund, justification);


        workInspector.updateProjectToEstimate(updatedProject);

        resp.sendRedirect(req.getContextPath() + "/estimate");
    }
}
