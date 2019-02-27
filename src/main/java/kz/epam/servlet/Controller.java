package kz.epam.servlet;

import kz.epam.commands.Command;
import kz.epam.commands.CommandFactory;
import kz.epam.config.ConfigManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    CommandFactory commandFactory = CommandFactory.getInstance();

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
        String page = null;

        // get command received from JSP
        Command command = CommandFactory.getCommand(request);

        // call execute() method and provide the parameters to the certain command class

        page = command.execute(request);
        // the method return a page in the resposnse
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext()
                    .getRequestDispatcher(page);
            // вызов страницы ответа на запрос
            dispatcher.forward(request, response);
        } else {
//            page = ConfigManager.getProperty("path.page.error");
            response.sendRedirect(request.getContextPath() + page);

        }
    }
}
