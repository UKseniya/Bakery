package kz.epam.commands;

import kz.epam.dao.UserDAO;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;

import javax.servlet.http.HttpServletRequest;

public class RegistrationForm implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

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
                    MessageManager.getInstance().getProperty("registration.error"));
            page = "/jsp/registration.jsp";
        }
        return page;
    }
}
