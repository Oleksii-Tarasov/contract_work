package ua.gov.court.supreme.contractwork.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete-project")
public class DeleteProjectFromEstimateServlet extends BaseWorkServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                long projectId = Long.parseLong(idParam);

                workInspector.deleteProjectFromEstimate(projectId);

                resp.sendRedirect(req.getContextPath() + "/estimate");
            } catch (NumberFormatException e) {
                // Обробка помилки, якщо ID не є числом
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid project ID format.");
            } catch (Exception e) {
                // Обробка інших помилок (наприклад, помилка БД)
                throw new ServletException("Error deleting project", e);
            }
        } else {
            // Обробка помилки, якщо параметр 'id' відсутній
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing project ID.");
        }
    }
}
