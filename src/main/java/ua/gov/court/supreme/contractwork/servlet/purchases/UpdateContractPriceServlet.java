package ua.gov.court.supreme.contractwork.servlet.purchases;

import ua.gov.court.supreme.contractwork.servlet.BaseWorkServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update-contract-price")
public class UpdateContractPriceServlet extends BaseWorkServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long projectId = Long.parseLong(req.getParameter("projectId"));
        double contractPrice = Double.parseDouble(req.getParameter("contractPrice"));

        workInspector.updateContractPrice(projectId, contractPrice);
    }
}
