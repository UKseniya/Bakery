package kz.epam.commands;

import kz.epam.constants.Constants;
import kz.epam.entities.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class Logout implements Command {
    private static final String PATH_TO_WELCOME_PAGE = "/index.jsp";
    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();

        String page = PATH_TO_WELCOME_PAGE;

        session.removeAttribute(Constants.USER);

        return page;
    }
}
