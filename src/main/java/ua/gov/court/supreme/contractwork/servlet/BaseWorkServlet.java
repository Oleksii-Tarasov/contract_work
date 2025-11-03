package ua.gov.court.supreme.contractwork.servlet;

import ua.gov.court.supreme.contractwork.service.WorkInspector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class BaseWorkServlet extends HttpServlet {
    protected WorkInspector workInspector;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        workInspector = (WorkInspector) config.getServletContext().getAttribute("workInspector");

        if (workInspector == null) {
            throw new ServletException("WorkInspector is not initialized");
        }
    }
}
