package ua.gov.court.supreme.contractwork.servlet;

import ua.gov.court.supreme.contractwork.enums.ProjectStatus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update-status")
public class UpdateProjectStatusServlet extends BaseWorkServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long projectId = Long.parseLong(req.getParameter("projectId"));
            String statusRaw = req.getParameter("status");

            ProjectStatus status = null;

            if (statusRaw != null && !statusRaw.isEmpty()) {
                int intStatus = Integer.parseInt(statusRaw);
                status = ProjectStatus.fromInt(intStatus);
            }

            workInspector.updateProjectStatus(projectId, status);

            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            e.printStackTrace();
        }
    }
}
