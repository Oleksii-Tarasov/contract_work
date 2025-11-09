package ua.gov.court.supreme.contractwork.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/generate-purchases-table")
public class CreateContrcatsTableServlet extends BaseWorkServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        workInspector.createPurchasesFromEstimate();

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
