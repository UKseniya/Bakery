package kz.epam.commands;

import kz.epam.dao.UserDAO;
import kz.epam.entities.User;
import kz.epam.message.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Login implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = null;
        UserDAO userDAO = new UserDAO();
        boolean isUserRegistered = userDAO.isUserRegistered(login, password);

        if (isUserRegistered == true) {
            HttpSession session = request.getSession(true);
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
                    MessageManager.getInstance().getProperty("login.error"));
            page = "/jsp/login.jsp";
        }
        return page;
    }
}
