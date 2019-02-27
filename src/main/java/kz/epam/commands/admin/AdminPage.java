package kz.epam.commands.admin;

import kz.epam.commands.Command;
import kz.epam.constants.Constants;
import kz.epam.dao.UserDAO;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class AdminPage implements Command {
    private static final String TOTAL_NUMBER_OF_USERS = "numberOfUsers";
    private List<User> registeredUsers = new ArrayList<>();

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        String locale = session.getAttribute(Constants.LOCALE).toString();

        UserDAO userDAO = new UserDAO();
        registeredUsers = userDAO.findAllUsersByRole(Constants.USER);
        int numberOfUsers = registeredUsers.size();

        session.setAttribute(TOTAL_NUMBER_OF_USERS, numberOfUsers);

        page = Constants.PATH_TO_ADMIN_PAGE;
        return page;
    }
}
