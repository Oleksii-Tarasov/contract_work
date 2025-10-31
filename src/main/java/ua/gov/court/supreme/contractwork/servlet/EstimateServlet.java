package ua.gov.court.supreme.contractwork.servlet;

import ua.gov.court.supreme.contractwork.dao.EstimateDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/estimate")
public class EstimateServlet extends HttpServlet {
    private EstimateDAO estimateDAO;

    public void init() {
        estimateDAO = new EstimateDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("estimateKekv2210", estimateDAO.getProjectsByKekv(2210));
        req.setAttribute("estimateKekv2240", estimateDAO.getProjectsByKekv(2240));
        req.setAttribute("estimateKekv3110", estimateDAO.getProjectsByKekv(3110));

        getServletContext().getRequestDispatcher("//WEB-INF/views/estimate.jsp").forward(req,resp);
    }
}
