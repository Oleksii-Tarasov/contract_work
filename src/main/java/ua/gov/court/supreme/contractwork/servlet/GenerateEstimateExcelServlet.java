package ua.gov.court.supreme.contractwork.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/generate-estimate-excel")
public class GenerateEstimateExcelServlet extends BaseWorkServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        workInspector.generateEstimateExcelFile();
    }
}
