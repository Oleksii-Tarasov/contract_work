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
        long projectId = Long.parseLong(req.getParameter("projectId"));
        long executorId = workInspector.getExecutorIdFromProject(projectId);

        resp.setContentType("application/json");
        resp.getWriter().write("{\"executorId\":" + executorId + "}");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectIdStr = req.getParameter("projectId");
        String userIdStr = req.getParameter("userId");

        resp.setContentType("text/plain; charset=UTF-8");

        try {
            long projectId = Long.parseLong(projectIdStr);
            Long userId = null;

            if (userIdStr != null && !userIdStr.isEmpty()) {
                userId = Long.parseLong(userIdStr);
            }

            workInspector.updateProjectExecutor(projectId, userId);
            resp.getWriter().write("OK");
        } catch (Exception e) {
            resp.setStatus(400);
            resp.getWriter().write("ERROR: " + e.getMessage());
        }
    }
}
