package ua.gov.court.supreme.contractwork.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update-justification")
public class JustificationServlet extends BaseWorkServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String projectIdParam = req.getParameter("projectId");
        String justification = req.getParameter("justification");

        if (projectIdParam == null || projectIdParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Project ID is missing");
            return;
        }

        long projectId = Long.parseLong(projectIdParam);

        workInspector.updateJustification(projectId, justification);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("OK");
    }
}
