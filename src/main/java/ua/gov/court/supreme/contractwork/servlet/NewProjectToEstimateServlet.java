package ua.gov.court.supreme.contractwork.servlet;

import ua.gov.court.supreme.contractwork.dao.EstimateDAO;
import ua.gov.court.supreme.contractwork.model.Estimate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/new-project")
public class NewProjectToEstimateServlet extends HttpServlet {
    private EstimateDAO estimateDAO;

    public void init() {
        estimateDAO = new EstimateDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/newProjectEstimateForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            estimateDAO.insertProject(newProjectForEstimate);
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
