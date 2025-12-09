package ua.gov.court.supreme.contractwork.servlet.estimate;

import ua.gov.court.supreme.contractwork.model.Estimate;
import ua.gov.court.supreme.contractwork.servlet.BaseWorkServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/estimate")
public class EstimateServlet extends BaseWorkServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Estimate> projectsForEstimate2210 = workInspector.getProjectsFromEstimateByKekv(2210);
        req.setAttribute("projectsForEstimate2210", projectsForEstimate2210);
        req.setAttribute("totalQuantity2210", workInspector.getEstimateTotalAmounts(projectsForEstimate2210).getTotalQuantity());
        req.setAttribute("totalPriceSum2210", workInspector.getEstimateTotalAmounts(projectsForEstimate2210).getTotalPriceSum());
        req.setAttribute("totalGeneralFund2210", workInspector.getEstimateTotalAmounts(projectsForEstimate2210).getTotalGeneralFund());
        req.setAttribute("totalSpecialFund2210", workInspector.getEstimateTotalAmounts(projectsForEstimate2210).getTotalSpecialFund());

        List<Estimate> projectsForEstimate2240 = workInspector.getProjectsFromEstimateByKekv(2240);
        req.setAttribute("projectsForEstimate2240", projectsForEstimate2240);
        req.setAttribute("totalQuantity2240", workInspector.getEstimateTotalAmounts(projectsForEstimate2240).getTotalQuantity());
        req.setAttribute("totalPriceSum2240", workInspector.getEstimateTotalAmounts(projectsForEstimate2240).getTotalPriceSum());
        req.setAttribute("totalGeneralFund2240", workInspector.getEstimateTotalAmounts(projectsForEstimate2240).getTotalGeneralFund());
        req.setAttribute("totalSpecialFund2240", workInspector.getEstimateTotalAmounts(projectsForEstimate2240).getTotalSpecialFund());

        List<Estimate> projectsForEstimate3110 = workInspector.getProjectsFromEstimateByKekv(3110);
        req.setAttribute("projectsForEstimate3110", projectsForEstimate3110);
        req.setAttribute("totalQuantity3110", workInspector.getEstimateTotalAmounts(projectsForEstimate3110).getTotalQuantity());
        req.setAttribute("totalPriceSum3110", workInspector.getEstimateTotalAmounts(projectsForEstimate3110).getTotalPriceSum());
        req.setAttribute("totalGeneralFund3110", workInspector.getEstimateTotalAmounts(projectsForEstimate3110).getTotalGeneralFund());
        req.setAttribute("totalSpecialFund3110", workInspector.getEstimateTotalAmounts(projectsForEstimate3110).getTotalSpecialFund());

        getServletContext().getRequestDispatcher("/WEB-INF/views/estimate/estimate.jsp").forward(req,resp);
    }
}
