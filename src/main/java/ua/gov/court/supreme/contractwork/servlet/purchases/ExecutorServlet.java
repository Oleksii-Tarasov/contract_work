package ua.gov.court.supreme.contractwork.servlet.purchases;

import ua.gov.court.supreme.contractwork.servlet.BaseWorkServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/executor")
public class ExecutorServlet extends BaseWorkServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        try {
            long projectId = Long.parseLong(req.getParameter("projectId"));
            Long executorId = contractWorkService.findExecutorIdByProjectId(projectId);

            resp.getWriter().write("{\"executorId\": " + (executorId == null ? "null" : executorId) + "}");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid project ID\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Server error\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain; charset=UTF-8");

        String projectIdParam = req.getParameter("projectId");
        String executorIdParam = req.getParameter("executorId");

        try {
            long projectId = Long.parseLong(projectIdParam);
            Long executorId = null;
            if (executorIdParam != null && !executorIdParam.isEmpty()) {
                executorId = Long.parseLong(executorIdParam);
            }

            contractWorkService.updateProjectExecutor(projectId, executorId);
            resp.getWriter().write("OK");
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("ERROR: " + e.getMessage());
        }
    }
}
