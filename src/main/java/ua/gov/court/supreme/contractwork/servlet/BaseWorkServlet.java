package ua.gov.court.supreme.contractwork.servlet;

import ua.gov.court.supreme.contractwork.service.ContractWorkService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class BaseWorkServlet extends HttpServlet {
    protected ContractWorkService contractWorkService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        contractWorkService = (ContractWorkService) config.getServletContext().getAttribute("contractWorkService");

        if (contractWorkService == null) {
            throw new ServletException("ContractWorkService is not initialized");
        }
    }
}
