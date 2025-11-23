package ua.gov.court.supreme.contractwork.servlet;

import ua.gov.court.supreme.contractwork.model.Purchases;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/purchases")
public class PurchasesServlet extends BaseWorkServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Purchases> projectsForPurchases2210 = workInspector.getProjectsFromPurchasesByKekv(2210);
        req.setAttribute("projectsForPurchases2210", projectsForPurchases2210);
        req.setAttribute("totalQuantity2210", workInspector.getPurchasesTotalAmounts(projectsForPurchases2210).getTotalQuantity());
        req.setAttribute("totalPriceSum2210", workInspector.getPurchasesTotalAmounts(projectsForPurchases2210).getTotalPriceSum());
        req.setAttribute("totalRemainingBalance2210", workInspector.getPurchasesTotalAmounts(projectsForPurchases2210).getTotalRemainingBalance());

        List<Purchases> projectsForPurchases2240 = workInspector.getProjectsFromPurchasesByKekv(2240);
        req.setAttribute("projectsForPurchases2240", projectsForPurchases2240);
        req.setAttribute("totalQuantity2240", workInspector.getPurchasesTotalAmounts(projectsForPurchases2240).getTotalQuantity());
        req.setAttribute("totalPriceSum2240", workInspector.getPurchasesTotalAmounts(projectsForPurchases2240).getTotalPriceSum());
        req.setAttribute("totalRemainingBalance2240", workInspector.getPurchasesTotalAmounts(projectsForPurchases2240).getTotalRemainingBalance());

        List<Purchases> projectsForPurchases3110 = workInspector.getProjectsFromPurchasesByKekv(3110);
        req.setAttribute("projectsForPurchases3110", projectsForPurchases3110);
        req.setAttribute("totalQuantity3110", workInspector.getPurchasesTotalAmounts(projectsForPurchases3110).getTotalQuantity());
        req.setAttribute("totalPriceSum3110", workInspector.getPurchasesTotalAmounts(projectsForPurchases3110).getTotalPriceSum());
        req.setAttribute("totalRemainingBalance3110", workInspector.getPurchasesTotalAmounts(projectsForPurchases3110).getTotalRemainingBalance());

        req.setAttribute("users", workInspector.getAllUsers());

        req.setAttribute("projectStatuses", workInspector.getProjectStatuses());

        getServletContext().getRequestDispatcher("/WEB-INF/views/purchases.jsp").forward(req, resp);
    }
}
