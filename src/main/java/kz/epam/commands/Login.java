package kz.epam.commands;

import kz.epam.constants.Constants;
import kz.epam.dao.UserDAO;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class Login implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        String language = session.getAttribute(Constants.LOCALE).toString();

        Locale locale = new Locale(language);

        User user = null;
        UserDAO userDAO = new UserDAO();
        boolean isUserRegistered = userDAO.isUserRegistered(login, password);

        if (isUserRegistered == true) {
            user = userDAO.findUserByLoginAndPassword(login, password);
            session.setAttribute("user", user);

            if (user.getRole().equals("user")) {
                page = "/jsp/user/user.jsp";
            }
            else if (user.getRole().equals("admin")){

                page = "/jsp/admin/admin.jsp";
            }
            session.setAttribute("user", user);
        }
        else {
            request.setAttribute("loginErrorMessage",
                    MessageManager.getInstance(locale).getProperty("error.login"));
            page = "/jsp/login.jsp";
        }
        return page;
    }
}
