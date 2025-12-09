package ua.gov.court.supreme.contractwork.servlet.estimate;

import ua.gov.court.supreme.contractwork.servlet.BaseWorkServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/generate-estimate-excel")
public class GenerateEstimateExcelServlet extends BaseWorkServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        byte[] excelData = workInspector.createEstimateExcelFile();

        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setHeader("Content-Disposition", "attachment; filename=\"koshtorys_2025.xlsx\"");
        resp.setContentLength(excelData.length);

        resp.getOutputStream().write(excelData);
        resp.getOutputStream().flush();
    }
}
