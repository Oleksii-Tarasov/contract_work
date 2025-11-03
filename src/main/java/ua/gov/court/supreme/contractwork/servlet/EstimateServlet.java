package ua.gov.court.supreme.contractwork.servlet;

import ua.gov.court.supreme.contractwork.dao.EstimateDAO;
import ua.gov.court.supreme.contractwork.model.Estimate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/estimate")
public class EstimateServlet extends HttpServlet {
    private EstimateDAO estimateDAO;

    public void init() {
        estimateDAO = new EstimateDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Estimate> projectsForEstimate2210 = estimateDAO.getProjectsByKekv(2210);
        double totalQuantity2210 = projectsForEstimate2210.stream()
                .mapToDouble(Estimate::getQuantity)
                .sum();
        double totalPriceSum2210 = projectsForEstimate2210.stream()
                .mapToDouble(Estimate::getTotalPrice)
                .sum();
        double totalGeneralFund2210 = projectsForEstimate2210.stream()
                .mapToDouble(Estimate::getGeneralFund)
                .sum();
        double totalSpecialFund2210 = projectsForEstimate2210.stream()
                .mapToDouble(Estimate::getSpecialFund)
                .sum();
        req.setAttribute("projectsForEstimate2210", projectsForEstimate2210);
        req.setAttribute("totalQuantity2210", totalQuantity2210);
        req.setAttribute("totalPriceSum2210", totalPriceSum2210);
        req.setAttribute("totalGeneralFund2210", totalGeneralFund2210);
        req.setAttribute("totalSpecialFund2210", totalSpecialFund2210);

        List<Estimate> projectsForEstimate2240 = estimateDAO.getProjectsByKekv(2240);
        double totalQuantity2240 = projectsForEstimate2240.stream()
                .mapToDouble(Estimate::getQuantity)
                .sum();
        double totalPriceSum2240 = projectsForEstimate2240.stream()
                .mapToDouble(Estimate::getTotalPrice)
                .sum();
        double totalGeneralFund2240 = projectsForEstimate2240.stream()
                .mapToDouble(Estimate::getGeneralFund)
                .sum();
        double totalSpecialFund2240 = projectsForEstimate2240.stream()
                .mapToDouble(Estimate::getSpecialFund)
                .sum();
        req.setAttribute("projectsForEstimate2240", projectsForEstimate2240);
        req.setAttribute("totalQuantity2240", totalQuantity2240);
        req.setAttribute("totalPriceSum2240", totalPriceSum2240);
        req.setAttribute("totalGeneralFund2240", totalGeneralFund2240);
        req.setAttribute("totalSpecialFund2240", totalSpecialFund2240);

        List<Estimate> projectsForEstimate3110 = estimateDAO.getProjectsByKekv(3110);
        double totalQuantity3110 = projectsForEstimate3110.stream()
                .mapToDouble(Estimate::getQuantity)
                .sum();
        double totalPriceSum3110 = projectsForEstimate3110.stream()
                .mapToDouble(Estimate::getTotalPrice)
                .sum();
        double totalGeneralFund3110 = projectsForEstimate3110.stream()
                .mapToDouble(Estimate::getGeneralFund)
                .sum();
        double totalSpecialFund3110 = projectsForEstimate3110.stream()
                .mapToDouble(Estimate::getSpecialFund)
                .sum();
        req.setAttribute("projectsForEstimate3110", projectsForEstimate3110);
        req.setAttribute("totalQuantity3110", totalQuantity3110);
        req.setAttribute("totalPriceSum3110", totalPriceSum3110);
        req.setAttribute("totalGeneralFund3110", totalGeneralFund3110);
        req.setAttribute("totalSpecialFund3110", totalSpecialFund3110);

        getServletContext().getRequestDispatcher("/WEB-INF/views/estimate.jsp").forward(req,resp);
    }
}
