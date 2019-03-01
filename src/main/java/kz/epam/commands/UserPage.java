package kz.epam.commands;

import kz.epam.constants.Constants;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserPage implements Command {
    private static final String PATH_TO_ADMIN_PAGE = "/controller?command=admin_page";

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.USER);

        if (user.getRole().equals(Constants.USER)) {
            page = Constants.PATH_TO_USER_PAGE;
        }
        else if (user.getRole().equals(Constants.ADMIN)){
            page = PATH_TO_ADMIN_PAGE;
        }

        return page;
    }
}
