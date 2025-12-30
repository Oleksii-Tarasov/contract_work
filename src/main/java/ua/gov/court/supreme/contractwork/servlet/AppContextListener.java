package ua.gov.court.supreme.contractwork.servlet;

import ua.gov.court.supreme.contractwork.service.ContractWorkService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Server started – initializing...");
        ServletContext servletContext = sce.getServletContext();
        ContractWorkService contractWorkService = new ContractWorkService();
        sce.getServletContext().setAttribute("contractWorkService", contractWorkService);
    }
}
