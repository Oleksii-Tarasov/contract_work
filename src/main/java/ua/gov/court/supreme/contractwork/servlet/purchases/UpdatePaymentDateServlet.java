package ua.gov.court.supreme.contractwork.servlet.purchases;

import ua.gov.court.supreme.contractwork.servlet.BaseWorkServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/update-payment-date")
public class UpdatePaymentDateServlet extends BaseWorkServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long projectId = Long.parseLong(req.getParameter("projectId"));
        String dateStr =  req.getParameter("paymentDueDate");

        LocalDate paymentDate = null;

        if (dateStr != null && !dateStr.isEmpty()) {
            paymentDate = LocalDate.parse(dateStr); // yyyy-MM-dd
        }

        workInspector.updatePaymentDueDate(projectId, paymentDate);

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
