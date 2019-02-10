package kz.epam.commands;

import kz.epam.constants.Constants;
import kz.epam.dao.UserDAO;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class RegistrationForm implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        String language = session.getAttribute(Constants.LOCALE).toString();

        Locale locale = new Locale(language);

        UserDAO userDAO = new UserDAO();
        boolean isLoginFree = userDAO.isLoginFree(login);
        if (isLoginFree) {

            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setLogin(login);
            user.setPassword(password);

            boolean created = userDAO.create(user);
            page = "/jsp/registration_successful.jsp";
        }

        else {
            request.setAttribute("registrationErrorMessage",
                    MessageManager.getInstance(locale).getProperty("error.registration"));
            page = "/jsp/registration.jsp";
        }
        return page;
    }
}
