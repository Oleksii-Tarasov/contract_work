package ua.gov.court.supreme.contractwork.servlet.purchases;

import ua.gov.court.supreme.contractwork.model.Purchase;
import ua.gov.court.supreme.contractwork.servlet.BaseWorkServlet;

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
        List<Purchase> purchases2210 = contractWorkService.getPurchaseProjectsByKekv(2210);
        req.setAttribute("purchases2210", purchases2210);
        req.setAttribute("totalQuantity2210", contractWorkService.calculatePurchaseTotals(purchases2210).getTotalQuantity());
        req.setAttribute("totalPriceSum2210", contractWorkService.calculatePurchaseTotals(purchases2210).getTotalPriceSum());
        req.setAttribute("totalRemainingBalance2210", contractWorkService.calculatePurchaseTotals(purchases2210).getTotalRemainingBalance());
        req.setAttribute("totalGeneralFund2210", contractWorkService.calculatePurchaseTotals(purchases2210).getTotalGeneralFund());
        req.setAttribute("totalSpecialFund2210", contractWorkService.calculatePurchaseTotals(purchases2210).getTotalSpecialFund());

        List<Purchase> purchases2240 = contractWorkService.getPurchaseProjectsByKekv(2240);
        req.setAttribute("purchases2240", purchases2240);
        req.setAttribute("totalQuantity2240", contractWorkService.calculatePurchaseTotals(purchases2240).getTotalQuantity());
        req.setAttribute("totalPriceSum2240", contractWorkService.calculatePurchaseTotals(purchases2240).getTotalPriceSum());
        req.setAttribute("totalRemainingBalance2240", contractWorkService.calculatePurchaseTotals(purchases2240).getTotalRemainingBalance());
        req.setAttribute("totalGeneralFund2240", contractWorkService.calculatePurchaseTotals(purchases2240).getTotalGeneralFund());
        req.setAttribute("totalSpecialFund2240", contractWorkService.calculatePurchaseTotals(purchases2240).getTotalSpecialFund());

        List<Purchase> purchases3110 = contractWorkService.getPurchaseProjectsByKekv(3110);
        req.setAttribute("purchases3110", purchases3110);
        req.setAttribute("totalQuantity3110", contractWorkService.calculatePurchaseTotals(purchases3110).getTotalQuantity());
        req.setAttribute("totalPriceSum3110", contractWorkService.calculatePurchaseTotals(purchases3110).getTotalPriceSum());
        req.setAttribute("totalRemainingBalance3110", contractWorkService.calculatePurchaseTotals(purchases3110).getTotalRemainingBalance());
        req.setAttribute("totalGeneralFund3110", contractWorkService.calculatePurchaseTotals(purchases3110).getTotalGeneralFund());
        req.setAttribute("totalSpecialFund3110", contractWorkService.calculatePurchaseTotals(purchases3110).getTotalSpecialFund());

        req.setAttribute("projectStatuses", contractWorkService.getProjectStatuses());
        req.setAttribute("users", contractWorkService.getAllUsers());

        getServletContext().getRequestDispatcher("/WEB-INF/views/purchases/purchases.jsp").forward(req, resp);
    }
}
