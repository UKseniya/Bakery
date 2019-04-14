package kz.epam.servlet;

import kz.epam.command.Command;
import kz.epam.command.CommandFactory;
import kz.epam.config.ConfigManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BakeryController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String PAGE_ERROR = "path.page.error";
    private static CommandFactory commandFactory = CommandFactory.getInstance();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response) throws ServletException, IOException {
        String page;
        Command command = commandFactory.getCommand(request);

        page = command.execute(request);

        if (page != null) {
            RequestDispatcher dispatcher = getServletContext()
                    .getRequestDispatcher(page);

            dispatcher.forward(request, response);
        } else {
            page = ConfigManager.getInstance().getProperty(PAGE_ERROR);
            response.sendRedirect(request.getContextPath() + page);

        }
    }
}
